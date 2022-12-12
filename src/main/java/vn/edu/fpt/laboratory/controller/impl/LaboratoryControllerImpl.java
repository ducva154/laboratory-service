package vn.edu.fpt.laboratory.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.controller.LaboratoryController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.SortableRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.*;
import vn.edu.fpt.laboratory.dto.request.material.CreateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.UpdateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.*;
import vn.edu.fpt.laboratory.dto.response.material.CreateMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.service.LaboratoryService;
import vn.edu.fpt.laboratory.service.MaterialService;
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
public class LaboratoryControllerImpl implements LaboratoryController {

    private final ResponseFactory responseFactory;
    private final LaboratoryService laboratoryService;
    private final ProjectService projectService;
    private final MaterialService materialService;

    @Override
    public ResponseEntity<GeneralResponse<CreateLaboratoryResponse>> createLaboratory(CreateLaboratoryRequest request) {
        return responseFactory.response(laboratoryService.createLaboratory(request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<CreateProjectResponse>> createProject(String labId, _CreateProjectRequest request) {
        return responseFactory.response(projectService.createProject(labId, request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<CreateMaterialResponse>> createMaterial(String labId, CreateMaterialRequest request) {
        return responseFactory.response(materialService.createMaterial(labId, request), ResponseStatusEnum.CREATED);
    }
    @Override
    public ResponseEntity<GeneralResponse<Object>> updateLaboratory(String labId, UpdateLaboratoryRequest request) {
        laboratoryService.updateLaboratory(labId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateMaterial(String laboratoryId, String materialId, UpdateMaterialRequest request) {
        materialService.updateMaterial(laboratoryId, materialId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateProject(String laboratoryId, String projectId, _UpdateProjectRequest request) {
        projectService.updateProject(laboratoryId, projectId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteMaterial(String laboratoryId, String materialId) {
        materialService.deleteMaterial(laboratoryId, materialId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteLaboratory(String labId) {
        laboratoryService.deleteLaboratory(labId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<GetLaboratoryContainerResponse>> getLaboratory(
            String laboratoryId,
            String accountId,
            String laboratoryName,
            String laboratoryNameSortBy,
            String major,
            String majorSortBy,
            Integer page,
            Integer size,
            Integer suggestionPage,
            Integer suggestionSize) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if(Objects.nonNull(laboratoryNameSortBy)){
            sortableRequests.add(new SortableRequest("laboratory_name", laboratoryNameSortBy));
        }
        if(Objects.nonNull(majorSortBy)){
            sortableRequests.add(new SortableRequest("major", majorSortBy));
        }

        GetLaboratoryRequest request = GetLaboratoryRequest.builder()
                .laboratoryId(laboratoryId)
                .accountId(accountId)
                .laboratoryName(laboratoryName)
                .major(major)
                .page(page)
                .size(size)
                .sortBy(sortableRequests)
                .suggestionPage(suggestionPage)
                .suggestionSize(suggestionSize)
                .build();
        return responseFactory.response(laboratoryService.getLaboratory(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<GetLaboratoryDetailResponse>> getLaboratoryDetail(String labId) {
        return responseFactory.response(laboratoryService.getLaboratoryDetail(labId));
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetMemberResponse>>> getMemberInLaboratory(String labId,
                                                                                                      String memberId,
                                                                                                      String role,
                                                                                                      String roleSortBy,
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
        if(Objects.nonNull(roleSortBy)) {
            sortableRequests.add(new SortableRequest("role", roleSortBy));
        }
        if(Objects.nonNull(createdDateSortBy)){
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        if(Objects.nonNull(lastModifiedDateSortBy)){
            sortableRequests.add(new SortableRequest("last_modified_date", lastModifiedDateSortBy));
        }
        GetMemberInLaboratoryRequest request = GetMemberInLaboratoryRequest.builder()
                .memberId(memberId)
                .role(role)
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
        return responseFactory.response(laboratoryService.getMemberInLab(labId, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> removeMemberFromLaboratory(String labId, String memberId) {
        laboratoryService.removeMemberFromLaboratory(labId, memberId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<ApplyLaboratoryResponse>> applyToLaboratory(String labId, ApplyLaboratoryRequest request) {
        return responseFactory.response(laboratoryService.applyToLaboratory(labId, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> reviewApplication(String labId, String applicationId, ReviewApplicationRequest request) {
        laboratoryService.reviewApplication(labId, applicationId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetApplicationResponse>>> getApplicationByLabId(String labId,
                                                                                                           String applicationId,
                                                                                                           String status,
                                                                                                           String statusSortBy,
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
        if(Objects.nonNull(statusSortBy)) {
            sortableRequests.add(new SortableRequest("status", statusSortBy));
        }
        if(Objects.nonNull(createdDateSortBy)){
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        if(Objects.nonNull(lastModifiedDateSortBy)){
            sortableRequests.add(new SortableRequest("last_modified_date", lastModifiedDateSortBy));
        }
        GetApplicationRequest request = GetApplicationRequest.builder()
                .applicationId(applicationId)
                .status(status)
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
        return responseFactory.response(laboratoryService.getApplicationByLabId(labId, request));
    }

    @Override
    public ResponseEntity<GeneralResponse<GetApplicationDetailResponse>> getApplicationByApplicationId(String applicationId) {
        return responseFactory.response(laboratoryService.getApplicationByApplicationId(applicationId));
    }
}
