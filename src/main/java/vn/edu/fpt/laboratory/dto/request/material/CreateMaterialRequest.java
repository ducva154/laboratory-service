package vn.edu.fpt.laboratory.dto.request.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.laboratory.dto.common.CreateFileRequest;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 18:26
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateMaterialRequest implements Serializable {

    private static final long serialVersionUID = 8270344955154159456L;
    private String materialName;
    private String description;
    private Integer amount;
    private String note;
    private CreateFileRequest images;
}
