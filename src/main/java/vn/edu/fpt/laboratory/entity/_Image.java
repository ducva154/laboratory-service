package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.edu.fpt.laboratory.entity.common.Auditor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 19:32
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class _Image extends Auditor {

    private static final long serialVersionUID = -6194685386837853335L;
    private String imageId;
    private String imageName;
    private String fullPath;
}
