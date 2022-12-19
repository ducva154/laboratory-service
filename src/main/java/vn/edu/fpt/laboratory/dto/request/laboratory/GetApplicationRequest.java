package vn.edu.fpt.laboratory.dto.request.laboratory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.laboratory.dto.common.AuditableRequest;
import vn.edu.fpt.laboratory.utils.RequestDataUtils;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetApplicationRequest extends AuditableRequest {

    private static final long serialVersionUID = -7373344496132454197L;
    private String applicationId;
    private String status;

    public ObjectId getApplicationId() {
        return RequestDataUtils.convertObjectId(applicationId);
    }

    public String getStatus() {
        return RequestDataUtils.convertSearchableData(status);
    }
}
