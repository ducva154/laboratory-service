package vn.edu.fpt.laboratory.dto.response.app_config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 06:59
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetAppConfigResponse implements Serializable {

    private static final long serialVersionUID = 8659387992099141618L;
    private String configId;
    private String configKey;
    private String configValue;
}
