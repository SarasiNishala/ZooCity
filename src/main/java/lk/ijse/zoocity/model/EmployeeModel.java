package lk.ijse.zoocity.model;

import lk.ijse.zoocity.db.DbConnection;
import lk.ijse.zoocity.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpId());
        pstm.setString(2, dto.getEmpName());
        pstm.setString(3, dto.getEmpPassword());
        pstm.setString(4, dto.getCategory());
        pstm.setString(5,dto.getEmpAddress());
        pstm.setString(6, String.valueOf(dto.getEmpContact()));
        pstm.setString(7,dto.getAdminId());
        pstm.setString(8,dto.getShiftId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Employee SET EmpName = ?, EmpPassword = ? , Category = ?, Address = ?, Contact = ?, AdminId = ? , ScheduleId = ?, WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpName());
        pstm.setString(2, dto.getEmpPassword());
        pstm.setString(3, dto.getCategory());
        pstm.setString(4,dto.getEmpAddress());
        pstm.setString(5, String.valueOf(dto.getEmpContact()));
        pstm.setString(6,dto.getAdminId());
        pstm.setString(7,dto.getShiftId());
        pstm.setString(8, dto.getEmpId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Employee WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
}
