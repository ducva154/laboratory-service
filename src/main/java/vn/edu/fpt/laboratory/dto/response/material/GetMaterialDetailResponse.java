package vn.edu.fpt.laboratory.dto.response.material;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.common.AuditableResponse;
import vn.edu.fpt.laboratory.dto.response.image.GetImageResponse;

import java.io.Serializable;
import java.util.List;

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
@SuperBuilder
public class GetMaterialDetailResponse extends AuditableResponse implements Serializable {

    private static final long serialVersionUID = 8525192166632758337L;
    private String materialId;
    private String materialName;
    private String description;
    private String note;
    private String status;
    private Integer amount;
    private GetImageResponse images;

}
