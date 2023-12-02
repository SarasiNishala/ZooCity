package lk.ijse.dto.Tm;
import java.sql.Date;
import java.time.LocalDate;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SalaryTm {
    private String SalaryId;
    private String EmpId;
    private Double Payment;
    private LocalDate Date;
    private Button btn;
}