package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.GetMemberResponse;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectDetailResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:17
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/projects")
public interface ProjectController {

    @PutMapping("/{project-id}")
    ResponseEntity<GeneralResponse<Object>> updateProject(@PathVariable(name = "project-id") String projectId, @RequestBody _UpdateProjectRequest request);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetProjectResponse>>> getProject();

    @GetMapping("/{project-id}")
    ResponseEntity<GeneralResponse<GetProjectDetailResponse>> getProjectDetail(@PathVariable(name = "project-id") String projectId);

    @GetMapping("/{project-id}/members")
    ResponseEntity<GeneralResponse<PageableResponse<GetMemberResponse>>> getMemberInProject(@PathVariable(name = "project-id") String projectId);
}
