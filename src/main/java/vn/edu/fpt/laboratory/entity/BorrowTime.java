package vn.edu.fpt.laboratory.entity;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BorrowTime {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer amount;
}
