package vn.edu.fpt.laboratory.service;

import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.app_config.CreateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.GetAppConfigRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.UpdateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.response.app_config.CreateAppConfigResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigDetailResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 06:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface AppConfigService {

    CreateAppConfigResponse createAppConfig(CreateAppConfigRequest request);

    void updateAppConfig(String configId, UpdateAppConfigRequest request);

    void deleteAppConfig(String configId);

    PageableResponse<GetAppConfigResponse> getAppConfig(GetAppConfigRequest request);

    GetAppConfigDetailResponse getAppConfigDetail(String configId);
}
