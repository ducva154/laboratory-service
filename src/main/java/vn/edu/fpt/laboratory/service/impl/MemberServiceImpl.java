package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.config.kafka.producer.ModifyMembersToWorkspaceProducer;
import vn.edu.fpt.laboratory.constant.LaboratoryRoleEnum;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.event.ModifyMembersToWorkspaceEvent;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToProjectRequest;
import vn.edu.fpt.laboratory.dto.request.member.UpdateMemberInfoRequest;
import vn.edu.fpt.laboratory.entity.*;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.LaboratoryRepository;
import vn.edu.fpt.laboratory.repository.MemberInfoRepository;
import vn.edu.fpt.laboratory.repository.ProjectRepository;
import vn.edu.fpt.laboratory.service.MemberInfoService;
import vn.edu.fpt.laboratory.service.UserInfoService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * vn.edu.fpt.laboratory.service.impl
 *
 * @author : Portgas.D.Ace
 * @created : 02/12/2022
 * @contact : 0339850697- congdung2510@gmail.com
 **/

@Service
@RequiredArgsConstructor
@Slf4j

public class MemberServiceImpl implements MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;
    private final ProjectRepository projectRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final UserInfoService userInfoService;
    private final MongoTemplate mongoTemplate;
    private final ModifyMembersToWorkspaceProducer modifyMembersToWorkspaceProducer;

    @Override
    public void addMemberToProject(String projectId, AddMemberToProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project id not found"));
        Optional<MemberInfo> memberInProject = project.getMembers().stream().filter(v -> v.getMemberId().equals(request.getMemberId()))
                .findAny();
        if (memberInProject.isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member already contain this project");
        }
        MemberInfo memberInfo = memberInfoRepository.findById(request.getMemberId())
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member id not found"));
        List<MemberInfo> memberInfos = project.getMembers();
        memberInfos.add(memberInfo);
        project.setMembers(memberInfos);

        try {
            projectRepository.save(project);
            log.info("Add member to project success");
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can not add member to project in database: " + ex.getMessage());
        }

        modifyMembersToWorkspaceProducer.sendMessage(ModifyMembersToWorkspaceEvent.builder()
                .workspaceId(projectId)
                .accountId(memberInfo.getAccountId())
                .type("ADD")
                .build());
    }

    @Override
    public void addMemberToLaboratory(String labId, AddMemberToLaboratoryRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "laboratory id not found"));
        Optional<MemberInfo> memberInProject = laboratory.getMembers().stream().filter(v -> v.getMemberId().equals(request.getAccountId()))
                .findAny();
        if (memberInProject.isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member already contain this project");
        }
        MemberInfo memberInfo = MemberInfo.builder()
                .accountId(request.getAccountId())
                .role(LaboratoryRoleEnum.MEMBER.getRole())
                .build();
        try {
            memberInfo = memberInfoRepository.save(memberInfo);
        } catch (Exception ex) {
            throw new BusinessException("Can't save member info to database: " + ex.getMessage());
        }
        List<MemberInfo> memberInfos = laboratory.getMembers();
        memberInfos.add(memberInfo);
        laboratory.setMembers(memberInfos);
        try {
            laboratoryRepository.save(laboratory);
            log.info("Add member to laboratory success");
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can not add member to project in database: " + ex.getMessage());
        }
    }

    @Override
    public void updateMember(String memberId, UpdateMemberInfoRequest request) {
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member id not found"));
        if (Objects.nonNull(request.getRole())) {
            memberInfo.setRole(request.getRole());
        }
        try {
            memberInfoRepository.save(memberInfo);
            log.info("Update Member success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save Member in database when update folder: " + ex.getMessage());
        }
    }

    @Override
    public void removeMemberFromProject(String projectId, String memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project id not exist"));
        List<MemberInfo> memberInfos = project.getMembers();
        if (memberInfos.stream().noneMatch(m -> m.getMemberId().equals(memberId))) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member ID not exist in Project");
        }
        memberInfos.removeIf(m -> m.getMemberId().equals(memberId));
        project.setMembers(memberInfos);
        try {
            projectRepository.save(project);
            log.info("Save project success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save project in database  " + ex.getMessage());
        }
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                        .orElseThrow(()->new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member ID not exist"));
        modifyMembersToWorkspaceProducer.sendMessage(ModifyMembersToWorkspaceEvent.builder()
                .workspaceId(projectId)
                .accountId(memberInfo.getAccountId())
                .type("DELETE")
                .build());
    }

    @Override
    public void removeMemberFromLaboratory(String labId, String memberId) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not found"));
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member ID not exist"));
        List<MemberInfo> memberInfos = laboratory.getMembers();
        if (memberInfos.stream().noneMatch(m -> m.getMemberId().equals(memberId))) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member ID is not exist in Laboratory");
        }
        List<Project> projectList = laboratory.getProjects();
        projectList.stream().map(Project::getProjectId).forEach((projectId) -> this.removeMemberFromProject(projectId, memberId));
        memberInfos.removeIf(v -> v.getMemberId().equals(memberId));
        laboratory.setMembers(memberInfos);
        try {
            memberInfoRepository.deleteById(memberId);
            log.info("Delete member success");
        } catch (Exception ex) {
            throw new BusinessException("Can't delete member in database  " + ex.getMessage());
        }
        try {
            laboratoryRepository.save(laboratory);
            log.info("Save laboratory success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save laboratory in database  " + ex.getMessage());
        }
    }
}
