package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.laboratory.entity.common.Auditor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 06:50
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class AppConfig extends Auditor {

    private static final long serialVersionUID = -9140898901710233217L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String configId;
    @Field(name = "config_key")
    private String configKey;
    @Field(name = "config_value")
    private String configValue;
}
