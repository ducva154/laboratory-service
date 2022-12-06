package vn.edu.fpt.laboratory.dto.response.laboratory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.laboratory.dto.common.PageableResponse;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 19:55
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetLaboratoryContainerResponse implements Serializable {

    private static final long serialVersionUID = -4293335061063944296L;
    private PageableResponse<GetLaboratoryResponse> joinedLaboratories;
    private PageableResponse<GetLaboratoryResponse> suggestLaboratories;
}
