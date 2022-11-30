package vn.edu.fpt.laboratory.dto.response.laboratory;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.laboratory.dto.common.MemberInfoResponse;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 22:27
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"laboratoryId", "laboratoryName", "description", "members", "projects", "major", "ownerBy"})
public class GetLaboratoryResponse implements Serializable {

    private static final long serialVersionUID = 7221469874164451550L;
    private String laboratoryId;
    private String laboratoryName;
    private String description;
    private Integer members;
    private Integer projects;
    private String major;
    private MemberInfoResponse ownerBy;
}
