package vn.edu.fpt.laboratory.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatusEnum {

    WAITING_FOR_APPROVAL("WAITING_FOR_APPROVAL"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED"),
    COMPLETED("COMPLETED");

    private final String status;
}
