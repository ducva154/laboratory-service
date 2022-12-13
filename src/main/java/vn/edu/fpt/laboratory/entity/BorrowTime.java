package vn.edu.fpt.laboratory.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class BorrowTime {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer amount;
}
