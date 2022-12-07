package vn.edu.fpt.laboratory.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 30/11/2022 - 20:49
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequiredArgsConstructor
@Getter
public enum MaterialStatusEnum {

    FREE("FREE"),
    ORDERED("IN_USED");

    private final String status;
}
