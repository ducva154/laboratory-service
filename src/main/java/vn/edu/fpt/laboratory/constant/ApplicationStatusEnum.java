package vn.edu.fpt.laboratory.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 01/09/2022 - 15:27
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ApplicationStatusEnum {

    WAITING_FOR_APPROVE("WAITING_FOR_APPROVAL"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    ;

    private String statusName;

    ApplicationStatusEnum(String statusName) {
        this.statusName = statusName;
    }
}
