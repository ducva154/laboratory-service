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
 * @created : 29/11/2022 - 21:49
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "member_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class MemberInfo extends Auditor {

    private static final long serialVersionUID = -3617402162338443961L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String memberId;
    @Field(name = "account_id")
    private String accountId;
    @Field(name = "role")
    private String role;
}
