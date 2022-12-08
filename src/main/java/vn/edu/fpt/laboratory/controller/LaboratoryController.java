package vn.edu.fpt.laboratory.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.laboratory.ApplyLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.CreateLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.ReviewApplicationRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.UpdateLaboratoryRequest;
import vn.edu.fpt.laboratory.dto.request.material.CreateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.UpdateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.project._CreateProjectRequest;
import vn.edu.fpt.laboratory.dto.request.project._UpdateProjectRequest;
import vn.edu.fpt.laboratory.dto.response.laboratory.*;
import vn.edu.fpt.laboratory.dto.response.material.CreateMaterialResponse;
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

    @PostMapping(value = "/{lab-id}/material", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<GeneralResponse<CreateMaterialResponse>> createMaterial(@PathVariable("lab-id") String labId, @ModelAttribute CreateMaterialRequest request);

    @PutMapping("/{lab-id}")
    ResponseEntity<GeneralResponse<Object>> updateLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody UpdateLaboratoryRequest request);

    @PutMapping("/{laboratory-id}/materials/{material-id}")
    ResponseEntity<GeneralResponse<Object>> updateMaterial(@PathVariable(name = "laboratory-id") String laboratoryId,
                                                           @PathVariable(name = "material-id") String materialId,
                                                           @RequestBody UpdateMaterialRequest request );
    @PutMapping("/{laboratory-id}/projects/{project-id}")
    ResponseEntity<GeneralResponse<Object>> updateProject(@PathVariable(name = "laboratory-id") String laboratoryId,
                                                          @PathVariable(name = "project-id") String projectId,
                                                          @RequestBody _UpdateProjectRequest request);

    @DeleteMapping("/{laboratory-id}/materials/{material-id}")
    ResponseEntity<GeneralResponse<Object>> deleteMaterial(@PathVariable(name = "laboratory-id") String laboratoryId,
                                                           @PathVariable(name = "material-id") String materialId);

    @DeleteMapping("/{lab-id}")
    ResponseEntity<GeneralResponse<Object>> deleteLaboratory(@PathVariable(name = "lab-id") String labId);

    @GetMapping
    ResponseEntity<GeneralResponse<GetLaboratoryContainerResponse>> getLaboratory(
            @RequestParam(name = "lab-id", required = false) String laboratoryId,
            @RequestParam(name = "account-id", required = false) String accountId,
            @RequestParam(name = "lab-name", required = false) String laboratoryName,
            @RequestParam(name = "lab-name-sort-by", required = false) String laboratoryNameSortBy,
            @RequestParam(name = "major", required = false) String major,
            @RequestParam(name = "major-sort-by", required = false) String majorSortBy,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "suggestion-page", required = false) Integer suggestionPage,
            @RequestParam(name = "suggestion-size", required = false) Integer suggestionSize
            );

    @GetMapping("/{lab-id}")
    ResponseEntity<GeneralResponse<GetLaboratoryDetailResponse>> getLaboratoryDetail(@PathVariable(name = "lab-id") String labId);

    @GetMapping("/{lab-id}/members")
    ResponseEntity<GeneralResponse<PageableResponse<GetMemberResponse>>> getMemberInLaboratory(@PathVariable(name = "lab-id") String labId);

    @DeleteMapping("/{lab-id}/members/{member-id}")
    ResponseEntity<GeneralResponse<Object>> removeMemberFromLaboratory(@PathVariable(name = "lab-id") String labId, @PathVariable(name = "member-id") String memberId);

    @PostMapping("/{lab-id}/apply")
    ResponseEntity<GeneralResponse<ApplyLaboratoryResponse>> applyToLaboratory(@PathVariable(name = "lab-id") String labId, @RequestBody ApplyLaboratoryRequest request);

    @PostMapping("/{lab-id}/{application-id}")
    ResponseEntity<GeneralResponse<Object>> reviewApplication(@PathVariable(name = "lab-id") String labId, @PathVariable(name = "application-id") String applicationId, @RequestBody ReviewApplicationRequest request);

    @GetMapping("/{lab-id}/applications")
    ResponseEntity<GeneralResponse<PageableResponse<GetApplicationResponse>>> getApplicationByLabId(@PathVariable(name = "lab-id") String labId,
        @RequestParam(value = "status", required = false) String status);

    @GetMapping("/applications/{application-id}")
    ResponseEntity<GeneralResponse<GetApplicationDetailResponse>> getApplicationByApplicationId(@PathVariable(name = "application-id") String applicationId);
}
