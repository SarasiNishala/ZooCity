package lk.ijse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CageManageFormDto {
    private String EmpId;
    private String CageId;
    private LocalDate Date;
    private LocalDateTime Time;
    private String Status;
}
