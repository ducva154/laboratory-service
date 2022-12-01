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
import vn.edu.fpt.laboratory.dto.request.material.*;
import vn.edu.fpt.laboratory.dto.response.material.CreateMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialDetailResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.OrderMaterialResponse;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.service.MaterialService;

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
    public ResponseEntity<GeneralResponse<Object>> updateMaterial(String laboratoryId, String materialId, UpdateMaterialRequest request) {
        materialService.updateMaterial(laboratoryId, materialId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteMaterial(String materialId) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> removeImage(String materialId, String imageId) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> addImage(String materialId, AddImageRequest request) {
        materialService.addImage(materialId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetMaterialResponse>>> getMaterial() {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<GetMaterialDetailResponse>> getMaterialId(String materialId) {
        return responseFactory.response(materialService.getMaterialId(materialId));
    }

    @Override
    public ResponseEntity<GeneralResponse<OrderMaterialResponse>> orderMaterial(String laboratoryId, String materialId, OrderMaterialRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> returnMaterial(String orderId, ReturnMaterialRequest request) {
        return null;
    }
}
