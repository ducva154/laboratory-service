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
public class GetMemberInLaboratoryRequest extends AuditableRequest {

    private static final long serialVersionUID = 4017687097480637271L;
    private String memberId;
    private String role;

    public ObjectId getMemberId() {
        return RequestDataUtils.convertObjectId(memberId);
    }

    public String getRole() {
        return RequestDataUtils.convertSearchableData(role);
    }
}
