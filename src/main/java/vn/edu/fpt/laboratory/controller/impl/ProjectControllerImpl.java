package vn.edu.fpt.laboratory.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.controller.ProjectController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.GetMemberResponse;
import vn.edu.fpt.laboratory.dto.response.member.RemoveMemberFromProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectDetailResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.service.ProjectService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:18
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectControllerImpl implements ProjectController {

    private final ResponseFactory responseFactory;
    private final ProjectService projectService;



    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetProjectResponse>>> getProject() {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<GetProjectDetailResponse>> getProjectDetail(String projectId) {
        return responseFactory.response(projectService.getProjectDetailByProjectId(projectId));
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetMemberResponse>>> getMemberInProject(String projectId) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<RemoveMemberFromProjectResponse>> removeMemberFromProject(String projectId, String memberId) {
        return null;
    }


}
