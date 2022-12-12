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
public class GetOrderRequest extends AuditableRequest {

    private static final long serialVersionUID = 7327041960799083244L;
    private String orderId;
    private String materialName;
    private String status;

    public ObjectId getOrderId() {
        return RequestDataUtils.convertObjectId(orderId);
    }

    public String getMaterialName() {
        return RequestDataUtils.convertSearchableData(materialName);
    }

    public String getStatus() {
        return RequestDataUtils.convertSearchableData(status);
    }
}
