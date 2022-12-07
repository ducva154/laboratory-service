package vn.edu.fpt.laboratory.controller.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.controller.MaterialController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.SortableRequest;
import vn.edu.fpt.laboratory.dto.request.material.*;
import vn.edu.fpt.laboratory.dto.response.material.CreateMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialDetailResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.OrderMaterialResponse;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.service.MaterialService;

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
@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialControllerImpl implements MaterialController {

    private final ResponseFactory responseFactory;

    private final MaterialService materialService;


    @Override
    public ResponseEntity<GeneralResponse<Object>> removeImage(String materialId, String imageId) {
        materialService.removeImage(materialId, imageId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> addImage(String materialId, AddImageRequest request) {
        materialService.addImage(materialId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetMaterialResponse>>> getMaterial(String materialId,
                                                                                              String materialName,
                                                                                              String materialNameSortBy,
                                                                                              String description,
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
                                                                                              Integer size, String laboratoryId) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if(Objects.nonNull(materialNameSortBy)){
            sortableRequests.add(new SortableRequest("material_name", materialNameSortBy));
        }
        if(Objects.nonNull(statusSortBy)){
            sortableRequests.add(new SortableRequest("status", statusSortBy));
        }
        if(Objects.nonNull(createdDateSortBy)){
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        if(Objects.nonNull(lastModifiedDateSortBy)){
            sortableRequests.add(new SortableRequest("last_modified_date", lastModifiedDateSortBy));
        }
        GetMaterialRequest request = GetMaterialRequest.builder()
                .materialId(materialId)
                .materialName(materialName)
                .description(description)
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
        return responseFactory.response(materialService.getMaterial(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<GetMaterialDetailResponse>> getMaterialId(String materialId) {
        return responseFactory.response(materialService.getMaterialId(materialId));
    }

    @Override
    public ResponseEntity<GeneralResponse<OrderMaterialResponse>> orderMaterial(String laboratoryId, String materialId, OrderMaterialRequest request) {
        return responseFactory.response(materialService.orderMaterial(laboratoryId, materialId, request), ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> returnMaterial(String orderId) {
        materialService.returnMaterial(orderId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetMaterialResponse>>> getMaterialByLabId(String laboratoryId) {

        return responseFactory.response(materialService.getMaterialByLabId(laboratoryId));
    }
}
