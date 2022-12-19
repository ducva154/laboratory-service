package vn.edu.fpt.laboratory.dto.request.laboratory;

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
public class ApplyLaboratoryRequest implements Serializable {

    private static final long serialVersionUID = -4089524881531825113L;
    private String accountId;
    private String reason;
    private String cvKey;
}
