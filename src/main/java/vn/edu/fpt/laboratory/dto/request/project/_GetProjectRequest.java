package vn.edu.fpt.laboratory.dto.request.project;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.edu.fpt.laboratory.dto.common.AuditableRequest;
import vn.edu.fpt.laboratory.entity.common.Auditor;
import vn.edu.fpt.laboratory.utils.RequestDataUtils;

import java.time.LocalDate;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 00:05
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class _GetProjectRequest extends AuditableRequest {

    private static final long serialVersionUID = 4328876397176681089L;
    private String projectId;
    private String projectName;
    private String description;
    private String startDateFrom;
    private String startDateTo;
    private String toDateFrom;
    private String toDateTo;

    public ObjectId getProjectId() {
        return RequestDataUtils.convertObjectId(projectId);
    }

    public String getProjectName() {
        return RequestDataUtils.convertSearchableData(projectName);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }

    public LocalDate getStartDateFrom() {
        return RequestDataUtils.convertDateFrom(startDateFrom);
    }

    public LocalDate getStartDateTo() {
        return RequestDataUtils.convertDateTo(startDateTo);
    }

    public LocalDate getToDateFrom() {
        return RequestDataUtils.convertDateFrom(toDateFrom);
    }

    public LocalDate getToDateTo() {
        return RequestDataUtils.convertDateTo(toDateTo);
    }
}
