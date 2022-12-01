package vn.edu.fpt.laboratory.service;

import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.material.CreateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.OrderMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.ReturnMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.UpdateMaterialRequest;
import vn.edu.fpt.laboratory.dto.response.material.CreateMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialDetailResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.OrderMaterialResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:39
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface MaterialService {

    CreateMaterialResponse createMaterial(String labId, CreateMaterialRequest request);

    void updateMaterial(String materialId, UpdateMaterialRequest  request);

    void deleteMaterial(String laboratoryId, String materialId);

    PageableResponse<GetMaterialResponse> getMaterial(GetMaterialRequest request);

    GetMaterialDetailResponse getMaterialId(String materialId);

    OrderMaterialResponse orderMaterial(String materialId, OrderMaterialRequest request);

    void returnMaterial(String orderId, ReturnMaterialRequest request);

    void removeImage(String materialId, String imageId);

    void addImage(String materialId, AddImageRequest request);
}
