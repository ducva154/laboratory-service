package vn.edu.fpt.laboratory.dto.response.laboratory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.laboratory.constant.ApplicationStatusEnum;

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
@Data
@Builder
public class GetApplicationResponse implements Serializable {
    private static final long serialVersionUID = 9219729181606732385L;
    private String applicationId;
    private ApplicationStatusEnum status;
    private String cvKey;
    private String reason;
}
