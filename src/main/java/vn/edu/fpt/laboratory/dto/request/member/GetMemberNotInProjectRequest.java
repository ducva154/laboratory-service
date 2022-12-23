package vn.edu.fpt.laboratory.dto.request.member;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.common.PageableRequest;
import vn.edu.fpt.laboratory.utils.RequestDataUtils;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 24/12/2022 - 04:32
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetMemberNotInProjectRequest extends PageableRequest {

    private static final long serialVersionUID = -8881840123017738371L;
    private String labId;
    private String projectId;

    public String getLabId() {
        return labId;
    }

    public String getProjectId() {
        return projectId;
    }
}
