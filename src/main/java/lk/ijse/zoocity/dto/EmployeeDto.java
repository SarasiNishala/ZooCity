package lk.ijse.zoocity.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeDto {
    private String adminId;
    private String empId;
    private String empName;
    private String empAddress;
    private int empContact;
    private String category;
    private String shiftId;
    private  String empPassword;

    public EmployeeDto(String id, String name, String empPassword, String category, String address, String tel, String adminId, String shiftId) {
        this.empId = id;
        this.empName = name;
        this.empPassword = empPassword;
        this.category = category;
        this.empContact = Integer.parseInt(tel);
        this.empAddress = address;
        this.shiftId = shiftId;
        this.adminId = adminId;
    }
}
