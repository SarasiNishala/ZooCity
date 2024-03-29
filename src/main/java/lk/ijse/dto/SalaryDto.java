package lk.ijse.dto;

import java.sql.Date;
import java.time.LocalDate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SalaryDto {
    private String SalaryId;
    private String EmpId;
    private Double Payment;
    private LocalDate Date;
}
