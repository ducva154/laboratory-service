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
 * @created : 07/12/2022 - 10:42
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReviewApplicationRequest implements Serializable {

    private static final long serialVersionUID = -4292122838816645298L;
    private String status;
    private String comment;
}
