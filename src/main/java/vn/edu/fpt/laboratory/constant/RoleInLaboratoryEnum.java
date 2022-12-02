package vn.edu.fpt.laboratory.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleInLaboratoryEnum {

    OWNER("OWNER"),
    MANAGER("MANAGER"),
    MEMBER("MEMBER");

    private final String role;
}
