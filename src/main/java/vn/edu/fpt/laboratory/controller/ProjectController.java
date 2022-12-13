package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.GetMemberResponse;
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

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetProjectResponse>>> getProject(
            @RequestParam(name = "project-id", required = false) String projectId,
            @RequestParam(name = "project-name", required = false) String projectName,
            @RequestParam(name = "project-name-sort-by", required = false) String projectNameSortBy,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "start-date-from", required = false) String startDateFrom,
            @RequestParam(name = "start-date-to", required = false) String startDateTo,
            @RequestParam(name = "start-date-sort-by", required = false) String startDateSortBy,
            @RequestParam(name = "to-date-from", required = false) String toDateFrom,
            @RequestParam(name = "to-date-to", required = false) String toDateTo,
            @RequestParam(name = "to-date-sort-by", required = false) String toDateSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-date-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-date-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size

    );

    @GetMapping("/{lab-id}/projects")
    ResponseEntity<GeneralResponse<PageableResponse<GetProjectResponse>>> getProjectByLabId(@PathVariable(name = "lab-id") String labId);

    @GetMapping("/{project-id}")
    ResponseEntity<GeneralResponse<GetProjectDetailResponse>> getProjectDetail(@PathVariable(name = "project-id") String projectId);

    @DeleteMapping("/{lab-id}/{project-id}")
    ResponseEntity<GeneralResponse<Object>> deleteProject(@PathVariable(name = "lab-id") String labId, @PathVariable(name = "project-id") String projectId);

    @GetMapping("/{project-id}/members")
    ResponseEntity<GeneralResponse<PageableResponse<GetMemberResponse>>> getMemberInProject(@PathVariable(name = "project-id") String projectId);

    @DeleteMapping("/{project-id}/members/{member-id}")
    ResponseEntity<GeneralResponse<Object>> removeMemberFromProject(@PathVariable(name = "project-id") String projectId, @PathVariable(name = "member-id") String memberId);

    @PostMapping("/{project-id}/members/member")
    ResponseEntity<GeneralResponse<Object>> addMemberToProject(@PathVariable(name = "project-id") String projectId, @RequestBody AddMemberToProjectRequest request);
}
