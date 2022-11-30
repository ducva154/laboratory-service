package vn.edu.fpt.laboratory.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 07:08
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequiredArgsConstructor
@Getter
public enum ProjectRoleEnum {

    OWNER("OWNER"),
    MANAGER("MANAGER"),
    LEADER("LEADER"),
    MEMBER("MEMBER");

    private final String role;
}
