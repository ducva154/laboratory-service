package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.laboratory.entity.common.Auditor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "statistics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Statistic extends Auditor {

    private static final long serialVersionUID = -8536987811267100477L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String statisticId;
    @Field(name = "num_of_lab")
    private Integer numOfLab;
    @Field(name = "num_of_member")
    private Integer numOfMember;
    @Field(name = "num_of_project")
    private Integer numOfProject;
    @Field(name = "total_time_of_project")
    private Long totalTimeOfProject;
    @Field(name = "num_of_material")
    private Integer numOfMaterial;
    @Field(name = "num_of_material_borrowed")
    private Integer numOfMaterialBorrowed;
    @Field(name = "num_of_member_borrowed")
    private Integer numOfMemberBorrowed;
}
