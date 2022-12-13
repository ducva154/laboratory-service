package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.laboratory.ApplyLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.CreateLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.ReviewApplicationRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.UpdateLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.material.CreateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.OrderMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.UpdateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.member.AddMemberToLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.*;
import vn.edu.fpt.laboratory.dto.response.material.*;
import vn.edu.fpt.laboratory.dto.response.project.CreateProjectResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 21:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/laboratories")
public interface LaboratoryController {

    @PostMapping("/laboratory")
    ResponseEntity<GeneralResponse<CreateLaboratoryResponse>> createLaboratory(@RequestBody CreateLaboratoryRequest request);

    @PostMapping("/{lab-id}/project")
    ResponseEntity<GeneralResponse<CreateProjectResponse>> createProject(@PathVariable("lab-id") String labId, @RequestBody _CreateProjectRequest request);

    @PostMapping(value = "/{lab-id}/material")
    ResponseEntity<GeneralResponse<CreateMaterialResponse>> createMaterial(@PathVariable("lab-id") String labId, @RequestBody CreateMaterialRequest request);

    @PutMapping("/{lab-id}")
    ResponseEntity<GeneralResponse<Object>> updateLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody UpdateLaboratoryRequest request);

    @PutMapping("/{lab-id}/materials/{material-id}")
    ResponseEntity<GeneralResponse<Object>> updateMaterial(@PathVariable(name = "lab-id") String labId,
                                                           @PathVariable(name = "material-id") String materialId,
                                                           @RequestBody UpdateMaterialRequest request);

    @PutMapping("/{lab-id}/projects/{project-id}")
    ResponseEntity<GeneralResponse<Object>> updateProject(@PathVariable(name = "lab-id") String labId,
                                                          @PathVariable(name = "project-id") String projectId,
                                                          @RequestBody _UpdateProjectRequest request);

    @DeleteMapping("/{lab-id}/materials/{material-id}")
    ResponseEntity<GeneralResponse<Object>> deleteMaterial(@PathVariable(name = "lab-id") String labId,
                                                           @PathVariable(name = "material-id") String materialId);

    @DeleteMapping("/{lab-id}")
    ResponseEntity<GeneralResponse<Object>> deleteLaboratory(@PathVariable(name = "lab-id") String labId);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetLaboratoryResponse>>> getLaboratory(
            @RequestParam(name = "lab-id", required = false) String labId,
            @RequestParam(name = "account-id", required = false) String accountId,
            @RequestParam(name = "lab-name", required = false) String labName,
            @RequestParam(name = "lab-name-sort-by", required = false) String labNameSortBy,
            @RequestParam(name = "major", required = false) String major,
            @RequestParam(name = "major-sort-by", required = false) String majorSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    );

    @GetMapping("/suggestion")
    ResponseEntity<GeneralResponse<PageableResponse<GetLaboratoryResponse>>> getLaboratorySuggestion(
            @RequestParam(name = "lab-id", required = false) String labId,
            @RequestParam(name = "account-id", required = false) String accountId,
            @RequestParam(name = "lab-name", required = false) String labName,
            @RequestParam(name = "lab-name-sort-by", required = false) String labNameSortBy,
            @RequestParam(name = "major", required = false) String major,
            @RequestParam(name = "major-sort-by", required = false) String majorSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    );

    @GetMapping("/{lab-id}")
    ResponseEntity<GeneralResponse<GetLaboratoryDetailResponse>> getLaboratoryDetail(@PathVariable(name = "lab-id") String labId);

    @GetMapping("/{lab-id}/materials")
    ResponseEntity<GeneralResponse<PageableResponse<GetMaterialResponse>>> getMaterial(
            @RequestParam(name = "material-id", required = false) String materialId,
            @RequestParam(name = "material-name", required = false) String materialName,
            @RequestParam(name = "material-name-sort-by", required = false) String materialNameSortBy,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "status-sort-by", required = false) String statusSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-date-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-date-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @PathVariable(name = "lab-id", required = false) String labId
    );
    @GetMapping("/{lab-id}/members")
    ResponseEntity<GeneralResponse<PageableResponse<GetMemberResponse>>> getMemberInLaboratory(
            @PathVariable(name = "lab-id") String labId,
            @RequestParam(name = "member-id", required = false) String memberId,
            @RequestParam(name = "role", required = false) String role,
            @RequestParam(name = "role-sort-by", required = false) String roleSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @DeleteMapping("/{lab-id}/members/{member-id}")
    ResponseEntity<GeneralResponse<Object>> removeMemberFromLaboratory(@PathVariable(name = "lab-id") String labId, @PathVariable(name = "member-id") String memberId);

    @PostMapping("/{lab-id}/apply")
    ResponseEntity<GeneralResponse<ApplyLaboratoryResponse>> applyToLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody ApplyLaboratoryRequest request);

    @PostMapping("/{lab-id}/{application-id}")
    ResponseEntity<GeneralResponse<Object>> reviewApplication(@PathVariable(name = "lab-id") String labId, @PathVariable(name = "application-id") String applicationId, @RequestBody ReviewApplicationRequest request);

    @GetMapping("/{lab-id}/applications")
    ResponseEntity<GeneralResponse<PageableResponse<GetApplicationResponse>>> getApplicationByLabId(
            @PathVariable(name = "lab-id") String labId,
            @RequestParam(name = "application-id", required = false) String applicationId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "status-sort-by", required = false) String statusSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @GetMapping("/applications/{application-id}")
    ResponseEntity<GeneralResponse<GetApplicationDetailResponse>> getApplicationByApplicationId(@PathVariable(name = "application-id") String applicationId);

    @PostMapping("/{lab-id}/members/member")
    ResponseEntity<GeneralResponse<Object>> addMemberToLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody AddMemberToLaboratoryRequest request);

    @PostMapping("/{lab-id}/{material-id}/order")
    ResponseEntity<GeneralResponse<OrderMaterialResponse>> orderMaterial(@PathVariable(name = "lab-id") String labId,
                                                                         @PathVariable(name = "material-id") String materialId,
                                                                         @RequestBody OrderMaterialRequest request);

    @GetMapping("/{lab-id}/orders")
    ResponseEntity<GeneralResponse<PageableResponse<GetOrderedResponse>>> getOrderByLabId(
            @PathVariable(name = "lab-id") String labId,
            @RequestParam(name = "order-id", required = false) String orderId,
            @RequestParam(name = "material-name", required = false) String materialName,
            @RequestParam(name = "material-name-sort-by", required = false) String materialNameSortBy,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "status-sort-by", required = false) String statusSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-date-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-date-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size);

    @GetMapping("/{lab-id}/{account-id}/materials")
    ResponseEntity<GeneralResponse<PageableResponse<GetOrderedMaterialResponse>>> getOrderedMaterialInLabByAccountId(
            @PathVariable(name = "lab-id") String labId,
            @PathVariable(name = "account-id") String accountId);

}
