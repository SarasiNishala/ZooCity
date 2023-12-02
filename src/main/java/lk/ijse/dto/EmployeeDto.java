package lk.ijse.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeDto {
    private String empId;
    private String empName;
    private String empAddress;
    private int empContact;
    private String category;
    private String shiftId;
    private String AdminId;
}
