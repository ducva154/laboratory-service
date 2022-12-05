package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.laboratory.constant.LaboratoryRoleEnum;
import vn.edu.fpt.laboratory.constant.ProjectRoleEnum;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.common.MemberInfoResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.UserInfoResponse;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._GetProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.GetMemberResponse;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectDetailResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;
import vn.edu.fpt.laboratory.entity.Laboratory;
import vn.edu.fpt.laboratory.entity.MemberInfo;
import vn.edu.fpt.laboratory.entity.Project;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.BaseMongoRepository;
import vn.edu.fpt.laboratory.repository.LaboratoryRepository;
import vn.edu.fpt.laboratory.repository.MemberInfoRepository;
import vn.edu.fpt.laboratory.repository.ProjectRepository;
import vn.edu.fpt.laboratory.service.ProjectService;
import vn.edu.fpt.laboratory.service.UserInfoService;
import vn.edu.fpt.laboratory.utils.AuditorUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 00:06
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final LaboratoryRepository laboratoryRepository;
    private final ProjectRepository projectRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final UserInfoService userInfoService;
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public CreateProjectResponse createProject(String labId, _CreateProjectRequest request) {
        AuditorUtils auditorUtils = new AuditorUtils();

        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));

        Optional<Project> projectInDb = projectRepository.findByProjectName(request.getProjectName());
        if (projectInDb.isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project name already exist");
        }

        String accountId = auditorUtils.getAccountId();

        MemberInfo memberInfoInLab = laboratory.getMembers().stream().filter(m -> m.getAccountId().equals(accountId)).findAny()
                .orElseThrow(() -> new BusinessException("Account ID not contain in repository member"));

        if (!memberInfoInLab.getRole().equals(LaboratoryRoleEnum.OWNER.getRole()) && !memberInfoInLab.getRole().equals(LaboratoryRoleEnum.MANAGER.getRole())) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Invalid role for create project");
        }

        MemberInfo memberInfo = MemberInfo.builder()
                .accountId(accountId)
                .role(ProjectRoleEnum.OWNER.getRole())
                .build();

        try {
            memberInfo = memberInfoRepository.save(memberInfo);
            log.info("Create member info success: {}", memberInfo);
        } catch (Exception ex) {
            throw new BusinessException("Can't create member info: " + ex.getMessage());
        }

        Project project = Project.builder()
                .projectName(request.getProjectName())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .toDate(request.getToDate())
                .members(List.of(memberInfoInLab))
                .build();

        try {
            project = projectRepository.save(project);
            log.info("Add project to database success");
        } catch (Exception ex) {
            throw new BusinessException("Can't create project in database: " + ex.getMessage());
        }

        List<Project> currentProject = laboratory.getProjects();
        currentProject.add(project);
        laboratory.setProjects(currentProject);

        try {
            laboratoryRepository.save(laboratory);
            log.info("Update laboratory success");
        } catch (Exception ex) {
            throw new BusinessException("Can't update laboratory in database: " + ex.getMessage());
        }

        return CreateProjectResponse.builder()
                .projectId(project.getProjectId())
                .build();
    }

    @Override
    public void updateProject(String labId, String projectId, _UpdateProjectRequest request) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project ID not exist"));
        List<Project> projects = laboratory.getProjects();

        if (projects.stream().noneMatch(m -> m.getProjectId().equals(projectId))) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory not contain this project");
        }

        if (Objects.nonNull(request.getProjectName())) {
            if (projects.stream().anyMatch(m -> m.getProjectName().equals(request.getProjectName()))) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project name is already exist");
            } else {
                project.setProjectName(request.getProjectName());
            }
        }
        if (Objects.nonNull(request.getDescription())) {
            project.setDescription(request.getDescription());
        }
        if (Objects.nonNull(request.getStartDate())) {
            project.setStartDate(request.getStartDate());
        }
        if (Objects.nonNull(request.getToDate()) && request.getToDate().isAfter(request.getStartDate())) {
            project.setToDate(request.getToDate());
        }
        try {
            projectRepository.save(project);
        } catch (Exception ex) {
            throw new BusinessException("Can't save project to database: " + ex.getMessage());
        }
    }

    @Override
    public void deleteProject(String labId, String projectId) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));
        log.info("Lab is: {}", laboratory);
         log.info("Current project: {}", laboratory.getProjects());
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project ID not exist"));
        List<Project> projects = laboratory.getProjects();
        log.info("Projects get from lab: {}", projects);
        projects.remove(project);
        log.info("Project will remove: {}", project);
        log.info("Projects after remove: {}", projects);

        try {
            projectRepository.deleteById(projectId);
        } catch (Exception ex) {
            throw new BusinessException("Can't delete project in database");
        }
        laboratory.setProjects(projects);
        try {
            laboratoryRepository.save(laboratory);
        } catch (Exception ex) {
            throw new BusinessException("Can't update laboratory in database");
        }
    }

    @Override
    public PageableResponse<GetProjectResponse> getProjectByCondition(_GetProjectRequest request) {
        Query query = new Query();

        if (Objects.nonNull(request.getProjectId())) {
            query.addCriteria(Criteria.where("_id").is(request.getProjectId()));
        }
        if (Objects.nonNull(request.getProjectName())) {
            query.addCriteria(Criteria.where("project_name").regex(request.getProjectName()));
        }

        if (Objects.nonNull(request.getDescription())) {
            query.addCriteria(Criteria.where("description").regex(request.getDescription()));
        }

        query.addCriteria(Criteria.where("start_date").gte(request.getStartDateFrom()).lte(request.getStartDateTo()));

        query.addCriteria(Criteria.where("to_date").gte(request.getToDateFrom()).lte(request.getToDateTo()));

        BaseMongoRepository.addCriteriaWithAuditable(query, request);

        Long totalElements = mongoTemplate.count(query, Project.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        List<Project> projects = mongoTemplate.find(query, Project.class);

        List<GetProjectResponse> getProjectResponses = projects.stream().map(this::convertProjectToGetProjectResponse).collect(Collectors.toList());

        return new PageableResponse<>(request, totalElements, getProjectResponses);
    }

    @Override
    public PageableResponse<GetProjectResponse> getProjectByLaboratoryId(String labId) {
        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(()-> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Lab ID not exist"));
        List<GetProjectResponse> getProjectResponses = laboratory.getProjects().stream().map(this::convertProjectToGetProjectResponse).collect(Collectors.toList());
        return new PageableResponse<>(getProjectResponses);
    }

    @Override
    public PageableResponse<GetMemberResponse> getMemberInProject(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project ID not exist"));
        List<MemberInfo> memberInfos = project.getMembers();
        List<GetMemberResponse> getMemberResponses = memberInfos.stream().map(this::convertMemberToGetMemberInfoResponse).collect(Collectors.toList());
        return new PageableResponse<>(getMemberResponses);
    }

    private GetMemberResponse convertMemberToGetMemberInfoResponse(MemberInfo memberInfo) {
        return GetMemberResponse.builder()
                .memberId(memberInfo.getMemberId())
                .role(memberInfo.getRole())
                .userInfo(UserInfoResponse.builder()
                        .accountId(memberInfo.getAccountId())
                        .userInfo(userInfoService.getUserInfo(memberInfo.getAccountId()))
                        .build())
                .build();
    }

    private GetProjectResponse convertProjectToGetProjectResponse(Project project) {
        return GetProjectResponse.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .members(project.getMembers().size())
                .build();
    }

    @Override
    public GetProjectDetailResponse getProjectDetailByProjectId(String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project ID not exist"));

        return GetProjectDetailResponse.builder()
                .projectId(project.getProjectId())
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .members(project.getMembers().stream()
                        .map(this::convertMemberToMemberInfoResponse)
                        .collect(Collectors.toList()))
                .createdBy(UserInfoResponse.builder()
                        .accountId(project.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(project.getCreatedBy()))
                        .build())
                .createdDate(project.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(project.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(project.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(project.getLastModifiedDate())
                .build();
    }

    @Override
    public void removeMemberFromProject(String projectId, String memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project id not found"));

        List<MemberInfo> memberInfos = project.getMembers();
        Optional<MemberInfo> memberInProject = memberInfos.stream().filter(v -> v.getMemberId().equals(memberId)).findAny();

        if (memberInProject.isEmpty()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Member id not found removeMemberFromProject");
        }

        if (memberInfos.remove(memberInProject.get())) {
            project.setMembers(memberInfos);
            try {
                projectRepository.save(project);
                log.info("Remove member from Project success");
            } catch (Exception ex) {
                throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't update project in database after remove Project");
            }
        } else {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't remove member from Project");
        }
    }


    private MemberInfoResponse convertMemberToMemberInfoResponse(MemberInfo memberInfo) {
        return MemberInfoResponse.builder()
                .memberId(memberInfo.getMemberId())
                .role(memberInfo.getRole())
                .accountId(memberInfo.getAccountId())
                .userInfo(userInfoService.getUserInfo(memberInfo.getAccountId()))
                .build();
    }
}
