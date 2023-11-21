package lk.ijse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AnimalsMediDto {
    private String AnimalTg;
    private String MediId;
    private LocalDate Date;
    private LocalDateTime Time;
    private int Qty;
    private String Status;
}
