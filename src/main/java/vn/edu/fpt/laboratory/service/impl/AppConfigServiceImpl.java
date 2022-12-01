package vn.edu.fpt.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.UserInfoResponse;
import vn.edu.fpt.laboratory.dto.request.app_config.CreateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.GetAppConfigRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.UpdateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.response.app_config.CreateAppConfigResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigDetailResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigResponse;
import vn.edu.fpt.laboratory.dto.response.material.GetMaterialResponse;
import vn.edu.fpt.laboratory.entity.AppConfig;
import vn.edu.fpt.laboratory.entity.Material;
import vn.edu.fpt.laboratory.exception.BusinessException;
import vn.edu.fpt.laboratory.repository.AppConfigRepository;
import vn.edu.fpt.laboratory.repository.BaseMongoRepository;
import vn.edu.fpt.laboratory.service.AppConfigService;
import vn.edu.fpt.laboratory.service.UserInfoService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 07:01
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AppConfigServiceImpl implements AppConfigService {

    private final AppConfigRepository appConfigRepository;
    private final MongoTemplate mongoTemplate;
    private final UserInfoService userInfoService;

    @Override
    public CreateAppConfigResponse createAppConfig(CreateAppConfigRequest request) {
        if(appConfigRepository.findByConfigKey(request.getConfigKey()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Config key already exist");
        }
        AppConfig appConfig = AppConfig.builder()
                .configKey(request.getConfigKey())
                .configValue(request.getConfigValue())
                .build();
        try {
            appConfig = appConfigRepository.save(appConfig);
        }catch (Exception ex){
            throw new BusinessException("Can't create app config to database: "+ ex.getMessage());
        }
        return CreateAppConfigResponse.builder()
                .configId(appConfig.getConfigId())
                .build();
    }

    @Override
    public void updateAppConfig(String configId, UpdateAppConfigRequest request) {
        AppConfig appConfig = appConfigRepository.findById(configId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Config ID not exist"));
        if(Objects.nonNull(request.getConfigKey()) && !request.getConfigKey().isEmpty()) {
            if (appConfigRepository.findByConfigKey(request.getConfigKey()).isPresent()) {
                throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Config key  already exist");
            }
            appConfig.setConfigKey(request.getConfigKey());
        }
        if(Objects.nonNull(request.getConfigValue())){
            appConfig.setConfigValue(request.getConfigValue());
        }

        try {
            appConfigRepository.save(appConfig);
        }catch (Exception ex){
            throw new BusinessException("Can't update app config in database: "+ ex.getMessage());
        }

    }

    @Override
    public void deleteAppConfig(String configId) {
        AppConfig appConfig = appConfigRepository.findById(configId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Config ID not exist"));
        try {
            appConfigRepository.delete(appConfig);
        }catch (Exception ex){
            throw new BusinessException("Can't delete app config in database: "+ ex.getMessage());
        }

    }

    @Override
    public PageableResponse<GetAppConfigResponse> getAppConfig(GetAppConfigRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getConfigId())) {
            query.addCriteria(Criteria.where("_id").is(request.getConfigId()));
        }
        if (Objects.nonNull(request.getConfigKey())) {
            query.addCriteria(Criteria.where("config_key").regex(request.getConfigKey()));
        }

        if (Objects.nonNull(request.getConfigValue())) {
            query.addCriteria(Criteria.where("config_value").is(request.getConfigValue()));
        }

        BaseMongoRepository.addCriteriaWithAuditable(query, request);

        Long totalElements = mongoTemplate.count(query, AppConfig.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        List<AppConfig> appConfigs = mongoTemplate.find(query, AppConfig.class);
        List<GetAppConfigResponse> getAppConfigResponses = appConfigs.stream().map(this::convertAppConfigToGetAppConfigResponse).collect(Collectors.toList());

        return new PageableResponse<>(request, totalElements, getAppConfigResponses);
    }

    private GetAppConfigResponse convertAppConfigToGetAppConfigResponse(AppConfig appConfig) {
        return GetAppConfigResponse.builder()
                .configId(appConfig.getConfigId())
                .configKey(appConfig.getConfigKey())
                .configValue(appConfig.getConfigValue())
                .build();
    }

    @Override
    public GetAppConfigDetailResponse getAppConfigDetail(String configId) {
        AppConfig appConfig = appConfigRepository.findById(configId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Config ID not exist"));

        return GetAppConfigDetailResponse.builder()
                .configId(appConfig.getConfigId())
                .configKey(appConfig.getConfigKey())
                .configValue(appConfig.getConfigValue())
                .createdBy(UserInfoResponse.builder()
                        .accountId(appConfig.getCreatedBy())
                        .userInfo(userInfoService.getUserInfo(appConfig.getCreatedBy()))
                        .build())
                .createdDate(appConfig.getCreatedDate())
                .lastModifiedBy(UserInfoResponse.builder()
                        .accountId(appConfig.getLastModifiedBy())
                        .userInfo(userInfoService.getUserInfo(appConfig.getLastModifiedBy()))
                        .build())
                .lastModifiedDate(appConfig.getLastModifiedDate())
                .build();
    }
}
