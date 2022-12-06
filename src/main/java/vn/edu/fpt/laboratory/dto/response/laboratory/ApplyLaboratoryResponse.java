package vn.edu.fpt.laboratory.dto.response.laboratory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 05/12/2022 - 10:55
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApplyLaboratoryResponse implements Serializable {
    private static final long serialVersionUID = 1800036598711918917L;
    private String applicationId;
}
