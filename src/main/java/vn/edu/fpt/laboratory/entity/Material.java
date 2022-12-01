package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.laboratory.entity.common.Auditor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 21:49
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "materials")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Material extends Auditor {

    private static final long serialVersionUID = 6984535952069890068L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String materialId;
    @Field(name = "material_name")
    private String materialName;
    @Field(name = "description")
    private String description;
    @Field(name = "status")
    private String status;
    @Field(name = "amount")
    private Integer amount;
    @Field(name = "images")
    @DBRef(lazy = true)
    @Builder.Default
    private List<_Image> images = new ArrayList<>();
}
