package vn.edu.fpt.laboratory.dto.request.app_config;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.common.AuditableRequest;
import vn.edu.fpt.laboratory.utils.RequestDataUtils;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 07:01
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetAppConfigRequest extends AuditableRequest {

    private static final long serialVersionUID = -2402850828146561326L;
    private String configId;
    private String configKey;
    private String configValue;

    public String getConfigId() {
        return configId;
    }

    public String getConfigKey() {
        return RequestDataUtils.convertSearchableData(configKey);
    }

    public String getConfigValue() {
        return RequestDataUtils.convertSearchableData(configValue);
    }
}
