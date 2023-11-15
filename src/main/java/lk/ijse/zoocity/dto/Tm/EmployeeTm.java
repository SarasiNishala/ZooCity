package lk.ijse.zoocity.dto.Tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeTm {
    private String adminId;
    private String empId;
    private String empName;
    private String empAddress;
    private int empContact;
    private String category;
    private String shiftId;
    private  String empPassword;
}
