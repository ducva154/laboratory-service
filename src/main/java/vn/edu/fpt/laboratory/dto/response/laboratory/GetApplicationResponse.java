package vn.edu.fpt.laboratory.dto.response.laboratory;

import lombok.*;
import vn.edu.fpt.laboratory.dto.common.AuditableResponse;
import vn.edu.fpt.laboratory.dto.common.UserInfoResponse;

import java.time.LocalDateTime;


/**
 * vn.edu.fpt.laboratory.dto.response.laboratory
 *
 * @author : Portgas.D.Ace
 * @created : 05/12/2022
 * @contact : 0339850697- congdung2510@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class GetApplicationResponse {

    private static final long serialVersionUID = 9219729181606732385L;
    private String applicationId;
    private String status;
    private UserInfoResponse createdBy;
    private LocalDateTime createdDate;
    private UserInfoResponse lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
