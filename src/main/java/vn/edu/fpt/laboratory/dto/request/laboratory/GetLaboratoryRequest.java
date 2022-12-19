package vn.edu.fpt.laboratory.dto.request.laboratory;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.laboratory.constant.AppConstant;
import vn.edu.fpt.laboratory.dto.common.PageableRequest;
import vn.edu.fpt.laboratory.utils.RequestDataUtils;

import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:42
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetLaboratoryRequest extends PageableRequest {

    private static final long serialVersionUID = -9106669010225207664L;
    private String laboratoryId;
    private String accountId;
    private Boolean isContain;
    private String laboratoryName;
    private String description;
    private String major;

    public ObjectId getLaboratoryId() {
        return RequestDataUtils.convertObjectId(laboratoryId);
    }

    public String getAccountId() {
        return accountId;
    }

    public Boolean getIsContain() {
        return isContain == null || isContain;
    }

    public String getLaboratoryName() {
        return RequestDataUtils.convertSearchableData(laboratoryName);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }

    public String getMajor() {
        return RequestDataUtils.convertSearchableData(major);
    }
}
