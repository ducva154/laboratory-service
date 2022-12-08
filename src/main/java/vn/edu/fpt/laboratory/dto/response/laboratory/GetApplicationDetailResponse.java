package vn.edu.fpt.laboratory.dto.response.laboratory;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.constant.ApplicationStatusEnum;
import vn.edu.fpt.laboratory.dto.common.AuditableResponse;

import java.io.Serializable;

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
@SuperBuilder
@JsonPropertyOrder({"applicationId", "status", "reason", "cvKey"})
public class GetApplicationDetailResponse extends AuditableResponse {

    private static final long serialVersionUID = 8208749423480067583L;
    private String applicationId;
    private String accountId;
    private ApplicationStatusEnum status;
    private String reason;
    private String cvKey;
    private String comment;
}
