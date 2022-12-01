package vn.edu.fpt.laboratory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.laboratory.dto.common.GeneralResponse;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;
import vn.edu.fpt.laboratory.dto.request.app_config.CreateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.request.app_config.UpdateAppConfigRequest;
import vn.edu.fpt.laboratory.dto.response.app_config.CreateAppConfigResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigDetailResponse;
import vn.edu.fpt.laboratory.dto.response.app_config.GetAppConfigResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 07:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/configs")
public interface AppConfigController {

    @PostMapping("/config")
    ResponseEntity<GeneralResponse<CreateAppConfigResponse>> createAppConfig(@RequestBody CreateAppConfigRequest request);

    @PutMapping("/{config-id}")
    ResponseEntity<GeneralResponse<Object>> updateAppConfig(@PathVariable(name = "config-id") String configId, @RequestBody UpdateAppConfigRequest request);

    @DeleteMapping("/{config-id}")
    ResponseEntity<GeneralResponse<Object>> deleteAppConfig(@PathVariable(name = "config-id") String configId);

    @GetMapping("/{config-id}")
    ResponseEntity<GeneralResponse<GetAppConfigDetailResponse>> getAppConfigDetail(@PathVariable(name = "config-id") String configId);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetAppConfigResponse>>> getAppConfig(
            @RequestParam(name = "config-id", required = false) String configId,
            @RequestParam(name = "config-key", required = false) String configKey,
            @RequestParam(name = "config-key-sort-by", required = false) String configKeySortBy,
            @RequestParam(name = "config-value", required = false) String configValue,
            @RequestParam(name = "config-value-sort-by", required = false) String configValueSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-date-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-date-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
            );
}
