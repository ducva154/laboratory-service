package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
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
 * @created : 29/11/2022 - 22:17
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/materials")
public interface MaterialController {

    ResponseEntity<GeneralResponse<CreateMaterialResponse>> createMaterial(@RequestBody CreateMaterialRequest request);

    ResponseEntity<GeneralResponse<Object>> updateMaterial(@PathVariable(name = "material-id") String materialId, @RequestBody UpdateMaterialRequest request );

    ResponseEntity<GeneralResponse<Object>> deleteMaterial(@PathVariable(name = "material-id") String materialId);

    ResponseEntity<GeneralResponse<PageableResponse<GetMaterialResponse>>> getMaterial();

    ResponseEntity<GeneralResponse<GetMaterialDetailResponse>> getMaterialId(@PathVariable(name = "material-id") String materialId);

    ResponseEntity<GeneralResponse<OrderMaterialResponse>> orderMaterial(@PathVariable(name = "material-id") String materialId, @RequestBody OrderMaterialRequest request);

    ResponseEntity<GeneralResponse<Object>> returnMaterial(@PathVariable(name = "order-id") String orderId, @RequestBody ReturnMaterialRequest request);
}
