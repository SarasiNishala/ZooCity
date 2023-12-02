package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkScheduleDto {
    private String ScheduleId;
    private int Hours;
    private LocalDate Date;
}
