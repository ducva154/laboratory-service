package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.material.*;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialDetailResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.OrderMaterialResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:17
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/materials")
public interface MaterialController {

    @DeleteMapping("/{material-id}/{image-id}")
    ResponseEntity<GeneralResponse<Object>> removeImage(@PathVariable(name = "material-id") String materialId, @PathVariable(name = "image-id") String imageId);

    @PostMapping("/{material-id}")
    ResponseEntity<GeneralResponse<Object>> addImage(@PathVariable(name = "material-id") String materialId, @RequestBody AddImageRequest request);

    @GetMapping
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
            @PathVariable(name = "laboratory-id", required = false) String laboratoryId
    );

    @GetMapping("/{material-id}")
    ResponseEntity<GeneralResponse<GetMaterialDetailResponse>> getMaterialId(@PathVariable(name = "material-id") String materialId);

    @PostMapping("/{laboratory-id}/{material-id}/order")
    ResponseEntity<GeneralResponse<OrderMaterialResponse>> orderMaterial(@PathVariable(name = "laboratory-id") String laboratoryId,
                                                                         @PathVariable(name = "material-id") String materialId,
                                                                         @RequestBody OrderMaterialRequest request);

    @PostMapping("/orders/{order-id}")
    ResponseEntity<GeneralResponse<Object>> returnMaterial(@PathVariable(name = "order-id") String orderId);

    @GetMapping("/{laboratory-id}/materials")
    ResponseEntity<GeneralResponse<PageableResponse<GetMaterialResponse>>> getMaterialByLabId(
            @PathVariable(name = "laboratory-id") String laboratoryId
    );
}
