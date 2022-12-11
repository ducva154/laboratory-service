package vn.edu.fpt.laboratory.dto.request.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseOrderRequest implements Serializable {

    private static final long serialVersionUID = 6628783678750126985L;
    private String status;
}
