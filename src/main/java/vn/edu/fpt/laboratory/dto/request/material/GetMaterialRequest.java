package vn.edu.fpt.laboratory.dto.request.material;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.laboratory.constant.MaterialStatusEnum;
import vn.edu.fpt.laboratory.dto.common.AuditableRequest;
import vn.edu.fpt.laboratory.utils.RequestDataUtils;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 22:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetMaterialRequest extends AuditableRequest implements Serializable {

    private static final long serialVersionUID = -2865662420994808313L;
    private String materialId;
    private String materialName;
    private String description;
    private String status;

    public ObjectId getMaterialId() {
        return RequestDataUtils.convertObjectId(materialId);
    }

    public String getMaterialName() {
        return RequestDataUtils.convertSearchableData(materialName);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }


    public String getStatus() {
        try {
            return MaterialStatusEnum.valueOf(status).getStatus();
        }catch (Exception ex){
            return null;
        }
    }
}
