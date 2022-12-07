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
public class GetApplicationDetailResponse implements Serializable {
    private static final long serialVersionUID = -204847778345354223L;
    private ApplicationStatusEnum status;
    private String reason;
    private String cvKey;
}
