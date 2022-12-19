package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
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
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String imageId;
    @Field(name = "image_name")
    private String imageName;
    @Field(name = "file_key")
    private String fileKey;
    @Field(name = "size")
    private String size;
    @Field(name = "type")
    private String type;
    @Field(name = "length")
    private Long length;
    @Field(name = "mime_type")
    private String mimeType;
}
