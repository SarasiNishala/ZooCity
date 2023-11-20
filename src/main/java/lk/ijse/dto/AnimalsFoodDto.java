package lk.ijse.dto;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AnimalsFoodDto {
    private String AnimalTg;
    private String FoodId;
    private LocalDate Date;
    private LocalDateTime Time;
    private int Qty;
    private String Status;
}
