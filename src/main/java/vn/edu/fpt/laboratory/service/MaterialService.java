package vn.edu.fpt.laboratory.service;

import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.material.*;
import vn.edu.fpt.laboratory.dto.response.material.*;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:39
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface MaterialService {

    CreateMaterialResponse createMaterial(String labId, CreateMaterialRequest request);

    void updateMaterial(String laboratoryId, String materialId, UpdateMaterialRequest  request);

    void deleteMaterial(String laboratoryId, String materialId);

    PageableResponse<GetMaterialResponse> getMaterial(GetMaterialRequest request);

    GetMaterialDetailResponse getMaterialById(String materialId);

    OrderMaterialResponse orderMaterial(String laboratoryId, String materialId, OrderMaterialRequest request);

    void returnMaterial(String orderId);

    PageableResponse<GetOrderedResponse> getOrderByLabId(String laboratoryId, String status);

    PageableResponse<GetOrderedMaterialResponse> getOrderedMaterialInLabByAccountId(String laboratoryId, String memberId);

    void responseOrder(String orderId, ResponseOrderRequest request);

    void removeImage(String materialId, String imageId);

    void addImage(String materialId, AddImageRequest request);

    PageableResponse<GetMaterialResponse> getMaterialByLabId(String labId);
}
