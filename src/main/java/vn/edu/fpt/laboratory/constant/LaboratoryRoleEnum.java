package vn.edu.fpt.laboratory.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 29/11/2022 - 23:02
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequiredArgsConstructor
@Getter
public enum LaboratoryRoleEnum {

    OWNER("OWNER"),
    MANAGER("MANAGER"),
    MEMBER("MEMBER");

    private final String role;
}
