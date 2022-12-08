package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.laboratory.constant.ApplicationStatusEnum;
import vn.edu.fpt.laboratory.entity.common.Auditor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 05/12/2022 - 18:20
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Application extends Auditor {

    private static final long serialVersionUID = -73935165214054223L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String applicationId;
    @Field(name = "member_id")
    private String memberId;
    @Field(name = "reason")
    private String reason;
    @Field(name = "comment")
    private String comment;
    @Field(name = "cvKey")
    private String cvKey;
    @Field(name = "status", targetType = FieldType.STRING)
    private ApplicationStatusEnum status;
}
