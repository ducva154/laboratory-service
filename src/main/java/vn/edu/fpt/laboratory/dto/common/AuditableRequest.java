package vn.edu.fpt.laboratory.dto.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import vn.edu.fpt.laboratory.utils.RequestDataUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 16:53
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class AuditableRequest extends PageableRequest implements Serializable {

    private static final long serialVersionUID = -7225952892240337194L;
    protected String createdBy;
    protected String createdDateFrom;
    protected String createdDateTo;
    protected String lastModifiedBy;
    protected String lastModifiedDateFrom;
    protected String lastModifiedDateTo;

    public ObjectId getCreatedBy() {
        return RequestDataUtils.convertObjectId(createdBy);
    }

    public LocalDateTime getCreatedDateFrom() {
        return RequestDataUtils.convertDateTimeFrom(createdDateFrom);
    }

    public LocalDateTime getCreatedDateTo() {
        return RequestDataUtils.convertDateTimeTo(createdDateTo);
    }

    public ObjectId getLastModifiedBy() {
        return RequestDataUtils.convertObjectId(lastModifiedBy);
    }

    public LocalDateTime getLastModifiedDateFrom() {
        return RequestDataUtils.convertDateTimeFrom(lastModifiedDateFrom);
    }

    public LocalDateTime getLastModifiedDateTo() {
        return RequestDataUtils.convertDateTimeTo(lastModifiedDateTo);
    }
}
