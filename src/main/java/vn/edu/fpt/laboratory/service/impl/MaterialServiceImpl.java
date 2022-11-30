package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.material.CreateMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.OrderMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.ReturnMaterialRequest;
import vn.edu.fpt.laboratory.dto.request.material.UpdateMaterialRequest;
import vn.edu.fpt.laboratory.dto.response.material.CreateMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialDetailResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialResponse;
import vn.edu.fpt.laboratory.dto.response.material.OrderMaterialResponse;
import vn.edu.fpt.laboratory.entity.Material;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.MaterialRepository;
import vn.edu.fpt.laboratory.service.MaterialService;
import vn.edu.fpt.laboratory.service.UserInfoService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 19:24
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MongoTemplate mongoTemplate;
    private final UserInfoService userInfoService;

    @Override
    public CreateMaterialResponse createMaterial(CreateMaterialRequest request) {
        if(materialRepository.findByMaterialName(request.getMaterialName()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Material name already exist");
        }

        Material material = Material.builder()
                .materialName(request.getMaterialName())
                .description(request.getDescription())
                .amount(request.getAmount())
                .build();
        return null;
    }

    @Override
    public void updateMaterial(String materialId, UpdateMaterialRequest request) {

    }

    @Override
    public void deleteMaterial(String materialId) {

    }

    @Override
    public PageableResponse<GetMaterialResponse> getMaterial() {
        return null;
    }

    @Override
    public GetMaterialDetailResponse getMaterialId(String materialId) {
        return null;
    }

    @Override
    public OrderMaterialResponse orderMaterial(String materialId, OrderMaterialRequest request) {
        return null;
    }

    @Override
    public void returnMaterial(String orderId, ReturnMaterialRequest request) {

    }
}
