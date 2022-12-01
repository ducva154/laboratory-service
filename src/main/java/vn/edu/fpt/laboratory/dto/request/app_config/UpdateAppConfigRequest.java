package vn.edu.fpt.laboratory.dto.request.app_config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 07:01
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateAppConfigRequest implements Serializable {

    private static final long serialVersionUID = -4025567610641041170L;
    private String configKey;
    private String configValue;
}
