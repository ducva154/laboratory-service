package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.laboratory.entity.common.Auditor;

import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 21:59
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "borrow_histories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class OrderHistory extends Auditor {

    private static final long serialVersionUID = 4005673246906267960L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String orderId;
    @Field(name = "reason")
    private String reason;
    @Field(name = "status")
    private String status;
    @Field(name = "materialId")
    private String materialId;
    @Field(name = "amount")
    private Integer amount;
    @Field(name = "order_from")
    private LocalDateTime orderFrom;
    @Field(name = "order_to")
    private LocalDateTime orderTo;
    @Field(name = "actually_return")
    @Builder.Default
    private LocalDateTime actuallyReturn = null;
}
