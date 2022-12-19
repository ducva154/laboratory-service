package vn.edu.fpt.laboratory.dto.response.material;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.common.UserInfoResponse;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class GetOrderedResponse implements Serializable {

    private static final long serialVersionUID = 8434420456488166738L;
    private String orderId;
    private String materialName;
    private UserInfoResponse borrowBy;
    private Integer amount;
    private String reason;
    private String status;
    private LocalDateTime orderFromDate;
    private LocalDateTime orderToDate;
}
