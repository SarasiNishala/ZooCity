package lk.ijse.dto.Tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkScheduleTm {
    private String ScheduleId;
    private int Hours;
    private LocalDate Date;
    private Button btn;
}
