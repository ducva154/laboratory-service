package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
@Document(collection = "laboratories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Laboratory extends Auditor {

    private static final long serialVersionUID = 1350970713183994420L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String laboratoryId;
    @Indexed(unique = true)
    @Field(name = "laboratory_name")
    private String laboratoryName;
    @Field(name = "description")
    private String description;
    @Field(name = "owner_by")
    @DBRef(lazy = true)
    private MemberInfo ownerBy;
    @Field(name = "major")
    private String major;
    @Field(name = "projects")
    @DBRef(lazy = true)
    @Builder.Default
    private List<Project> projects = new ArrayList<>();
    @Field(name = "members")
    @DBRef(lazy = true)
    @Builder.Default
    private List<MemberInfo> members = new ArrayList<>();
    @Field(name = "materials")
    @DBRef(lazy = true)
    @Builder.Default
    private List<Material> materials = new ArrayList<>();
    @Field(name = "application")
    @DBRef(lazy = true)
    @Builder.Default
    private List<Application> applications = new ArrayList<>();
    @Field(name = "order")
    @DBRef(lazy = true)
    @Builder.Default
    private List<OrderHistory> orders = new ArrayList<>();
}
