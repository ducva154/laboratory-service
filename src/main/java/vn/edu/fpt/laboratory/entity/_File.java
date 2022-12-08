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
 * @created : 29/11/2022 - 16:36
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "files")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class _File extends Auditor {

    private static final long serialVersionUID = -1344802533955238175L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String fileId;
    @Field(name = "file_name")
    private String fileName;
    @Field(name = "file_key")
    private String fileKey;
    @Field(name = "description")
    private String description;
    @Field(name = "size")
    private String size;
    @Field(name = "type")
    private String type;
    @Field(name = "length")
    private Long length;
    @Field(name = "mime_type")
    private String mimeType;
}
