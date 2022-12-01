package vn.edu.fpt.laboratory.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.laboratory.constant.ResponseStatusEnum;
import vn.edu.fpt.laboratory.controller.AppConfigController;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.common.SortableRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.CreateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.GetAppConfigRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.UpdateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.response.app_config.CreateAppConfigResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigDetailResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigResponse;
import vn.edu.fpt.laboratory.factory.ResponseFactory;
import vn.edu.fpt.laboratory.service.AppConfigService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 07:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class AppConfigControllerImpl implements AppConfigController {

    private final AppConfigService appConfigService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<CreateAppConfigResponse>> createAppConfig(CreateAppConfigRequest request) {
        return responseFactory.response(appConfigService.createAppConfig(request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateAppConfig(String configId, UpdateAppConfigRequest request) {
        appConfigService.updateAppConfig(configId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteAppConfig(String configId) {
        appConfigService.deleteAppConfig(configId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<GetAppConfigDetailResponse>> getAppConfigDetail(String configId) {
        return responseFactory.response(appConfigService.getAppConfigDetail(configId));
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetAppConfigResponse>>> getAppConfig(String configId,
                                                                                                String configKey,
                                                                                                String configKeySortBy,
                                                                                                String configValue,
                                                                                                String configValueSortBy,
                                                                                                String createdBy,
                                                                                                String createdDateFrom,
                                                                                                String createdDateTo,
                                                                                                String lastModifiedBy,
                                                                                                String lastModifiedDateFrom,
                                                                                                String lastModifiedDateTo,
                                                                                                Integer page,
                                                                                                Integer size) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if(Objects.nonNull(configKeySortBy)) {
            sortableRequests.add(new SortableRequest("config_key", configKeySortBy));
        }
        if(Objects.nonNull(configValueSortBy)){
            sortableRequests.add(new SortableRequest("config_value", configValueSortBy));
        }
        GetAppConfigRequest request = GetAppConfigRequest.builder()
                .configId(configId)
                .configKey(configKey)
                .configValue(configValue)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDateTo(lastModifiedDateTo)
                .lastModifiedDateFrom(lastModifiedDateFrom)
                .sortBy(sortableRequests)
                .page(page)
                .size(size)
                .build();

        return responseFactory.response(appConfigService.getAppConfig(request));
    }
}
