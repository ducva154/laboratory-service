package vn.edu.fpt.laboratory.dto.request.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 18:29
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@Data
@Builder
public class OrderMaterialRequest implements Serializable {

    private static final long serialVersionUID = -7674328278013001218L;
    private Integer amount;
    private String reason;
    private LocalDateTime orderFrom;
    private LocalDateTime orderTo;

}
