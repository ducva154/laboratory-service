package vn.edu.fpt.laboratory.dto.response.material;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.common.AuditableResponse;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 18:28
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GetMaterialResponse implements Serializable {

    private static final long serialVersionUID = -2107177195120400774L;
    private String materialId;
    private String materialName;
    private String description;
    private Integer amount;
    private String status;
    private String image;
}
