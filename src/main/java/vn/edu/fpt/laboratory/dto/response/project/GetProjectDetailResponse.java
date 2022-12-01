package vn.edu.fpt.laboratory.dto.response.project;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.common.AuditableResponse;
import vn.edu.fpt.laboratory.dto.common.MemberInfoResponse;
import vn.edu.fpt.laboratory.dto.response.laboratory.GetMemberResponse;

import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 00:02
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class GetProjectDetailResponse extends AuditableResponse {

    private static final long serialVersionUID = -21914764616961402L;
    private String projectId;
    private String projectName;
    private String description;
    private List<MemberInfoResponse> members;
}
