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

import java.time.LocalDate;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 21:50
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Project extends Auditor {

    private static final long serialVersionUID = -4011416111330368844L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String projectId;
    @Indexed(unique = true)
    @Field(name = "project_name")
    private String projectName;
    @Field(name = "description")
    private String description;
    @Field(name = "start_date")
    private LocalDate startDate;
    @Field(name = "to_date")
    private LocalDate toDate;
    @Field(name = "members")
    @DBRef(lazy = true)
    private List<MemberInfo> members;
}
