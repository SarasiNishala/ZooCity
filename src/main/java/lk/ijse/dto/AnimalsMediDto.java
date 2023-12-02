package lk.ijse.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AnimalsMediDto {
    private String AnimalTg;
    private String MediId;
    private java.sql.Date Date;
    private java.sql.Time Time;
    private int Qty;
}
