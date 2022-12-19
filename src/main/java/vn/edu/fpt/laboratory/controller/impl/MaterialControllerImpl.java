package vn.edu.fpt.laboratory.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.controller.MaterialController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.SortableRequest;
import vn.edu.fpt.laboratory.dto.request.laboratory.GetOrderRequest;
import vn.edu.fpt.laboratory.dto.request.material.*;
import vn.edu.fpt.laboratory.dto.response.material.*;
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
    public ResponseEntity<GeneralResponse<GetMaterialDetailResponse>> getMaterialId(String materialId) {
        return responseFactory.response(materialService.getMaterialById(materialId));
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> returnMaterial(String orderId) {
        materialService.returnMaterial(orderId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> responseOrder(String orderId, ResponseOrderRequest request) {
        materialService.responseOrder(orderId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }
}
