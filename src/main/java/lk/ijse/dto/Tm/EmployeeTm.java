package lk.ijse.dto.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javafx.scene.control.Button;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeTm {
    private String empId;
    private String empName;
    private String empAddress;
    private int empContact;
    private String category;
    private String shiftId;
    private String adminId;
    private Button btn;
}
