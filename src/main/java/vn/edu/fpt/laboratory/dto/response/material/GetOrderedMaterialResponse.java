package vn.edu.fpt.laboratory.dto.response.material;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.laboratory.dto.response.image.GetImageResponse;
import vn.edu.fpt.laboratory.entity.BorrowTime;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class GetOrderedMaterialResponse implements Serializable {

    private static final long serialVersionUID = 8434420456488166738L;
    private String orderId;
    private String materialId;
    private String materialName;
    private GetImageResponse images;
    private LocalDateTime orderFromDate;
    private LocalDateTime orderToDate;
    private String status;
}
