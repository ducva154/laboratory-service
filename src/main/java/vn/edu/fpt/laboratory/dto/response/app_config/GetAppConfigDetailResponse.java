package vn.edu.fpt.laboratory.dto.response.app_config;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.common.AuditableResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 07:00
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class GetAppConfigDetailResponse extends AuditableResponse {

    private static final long serialVersionUID = 3920281618154634301L;
    private String configId;
    private String configKey;
    private String configValue;
}
