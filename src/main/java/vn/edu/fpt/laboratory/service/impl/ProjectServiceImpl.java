package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.laboratory.constant.LaboratoryRoleEnum;
import vn.edu.fpt.laboratory.constant.ProjectRoleEnum;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._GetProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectDetailResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;
import vn.edu.fpt.laboratory.entity.Laboratory;
import vn.edu.fpt.laboratory.entity.MemberInfo;
import vn.edu.fpt.laboratory.entity.Project;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.LaboratoryRepository;
import vn.edu.fpt.laboratory.repository.MemberInfoRepository;
import vn.edu.fpt.laboratory.repository.ProjectRepository;
import vn.edu.fpt.laboratory.service.ProjectService;
import vn.edu.fpt.laboratory.utils.AuditorUtils;

import java.util.List;
import java.util.Optional;

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
    private final MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public CreateProjectResponse createProject(String labId, _CreateProjectRequest request) {
        AuditorUtils auditorUtils = new AuditorUtils();

        Laboratory laboratory = laboratoryRepository.findById(labId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Laboratory ID not exist"));

        Optional<Project> projectInDb = projectRepository.findByProjectName(request.getProjectName());
        if(projectInDb.isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Project name already exist");
        }

        String accountId = auditorUtils.getAccountId();

        MemberInfo memberInfoInLab = laboratory.getMembers().stream().filter(m -> m.getAccountId().equals(accountId)).findAny()
                .orElseThrow(() -> new BusinessException("Account ID not contain in repository member"));

        if(!memberInfoInLab.getRole().equals(LaboratoryRoleEnum.OWNER.getRole()) && !memberInfoInLab.getRole().equals(LaboratoryRoleEnum.MANAGER.getRole())){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Invalid role for create project");
        }

        MemberInfo memberInfo = MemberInfo.builder()
                .accountId(accountId)
                .role(ProjectRoleEnum.OWNER.getRole())
                .build();

        try {
            memberInfo = memberInfoRepository.save(memberInfo);
            log.info("Create member info success: {}", memberInfo);
        }catch (Exception ex){
            throw new BusinessException("Can't create member info: "+ ex.getMessage());
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
        }catch (Exception ex){
            throw new BusinessException("Can't create project in database: "+ ex.getMessage());
        }

        List<Project> currentProject = laboratory.getProjects();
        currentProject.add(project);
        laboratory.setProjects(currentProject);

        try {
            laboratoryRepository.save(laboratory);
            log.info("Update laboratory success");
        }catch (Exception ex){
            throw new BusinessException("Can't update laboratory in database: "+ ex.getMessage());
        }

        return CreateProjectResponse.builder()
                .projectId(project.getProjectId())
                .build();
    }

    @Override
    public void updateProject(String projectId, _UpdateProjectRequest request) {

    }

    @Override
    public void deleteProject(String projectId) {

    }

    @Override
    public PageableResponse<GetProjectResponse> getProjectByCondition(_GetProjectRequest request) {
        return null;
    }

    @Override
    public GetProjectDetailResponse getProjectDetailByProjectId(String projectId) {
        return null;
    }
}
