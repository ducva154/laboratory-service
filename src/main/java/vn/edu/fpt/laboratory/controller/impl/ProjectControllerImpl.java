package vn.edu.fpt.laboratory.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.controller.ProjectController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.SortableRequest;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._GetProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.GetMemberResponse;
import vn.edu.fpt.laboratory.dto.response.member.RemoveMemberFromProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectDetailResponse;
import vn.edu.fpt.laboratory.dto.response.project.GetProjectResponse;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.service.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<GeneralResponse<Object>> updateProject(String laboratoryId, String projectId, _UpdateProjectRequest request) {
        projectService.updateProject(laboratoryId, projectId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetProjectResponse>>> getProject(String projectId,
                                                                                            String projectName,
                                                                                            String projectNameSortBy,
                                                                                            String description,
                                                                                            String startDateFrom,
                                                                                            String startDateTo,
                                                                                            String startDateSortBy,
                                                                                            String toDateFrom,
                                                                                            String toDateTo,
                                                                                            String toDateSortBy,
                                                                                            String createdBy,
                                                                                            String createdDateFrom,
                                                                                            String createdDateTo,
                                                                                            String createdDateSortBy,
                                                                                            String lastModifiedBy,
                                                                                            String lastModifiedDateFrom,
                                                                                            String lastModifiedDateTo,
                                                                                            String lastModifiedDateSortBy,
                                                                                            Integer page,
                                                                                            Integer size) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if(Objects.nonNull(projectNameSortBy)) {
            sortableRequests.add(new SortableRequest("project_name", projectNameSortBy));
        }
        if(Objects.nonNull(startDateSortBy)) {
            sortableRequests.add(new SortableRequest("start_date", startDateSortBy));
        }
        if(Objects.nonNull(toDateSortBy)) {
            sortableRequests.add(new SortableRequest("to_date", toDateSortBy));
        }
        if(Objects.nonNull(createdDateSortBy)) {
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        if(Objects.nonNull(lastModifiedDateSortBy)) {
            sortableRequests.add(new SortableRequest("last_modified_date", lastModifiedDateSortBy));
        }
        _GetProjectRequest request = _GetProjectRequest.builder()
                .projectId(projectId)
                .projectName(projectName)
                .description(description)
                .startDateFrom(startDateFrom)
                .startDateTo(startDateTo)
                .toDateFrom(toDateFrom)
                .toDateTo(toDateTo)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDateFrom(lastModifiedDateFrom)
                .lastModifiedDateTo(lastModifiedDateTo)
                .page(page)
                .size(size)
                .sortBy(sortableRequests)
                .build();
        return responseFactory.response(projectService.getProjectByCondition(request));
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
