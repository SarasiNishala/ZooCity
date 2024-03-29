package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static String checkCategory(String empId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
try {
        String sql = "SELECT Category FROM Employee WHERE EmpId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,empId);

        ResultSet resultSet = pstm.executeQuery();
    String category = "";
        if (resultSet.next()){
             category = resultSet.getString(1);

        }
        return category;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpId());
        pstm.setString(2, dto.getEmpName());
        pstm.setString(3, dto.getEmpAddress());
        pstm.setString(4, String.valueOf(dto.getEmpContact()));
        pstm.setString(5,dto.getCategory());
        pstm.setString(6,dto.getShiftId());
        pstm.setString(7,dto.getAdminId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Employee SET EmpName = ?, Address = ?, Contact = ?, Category = ?, ScheduleId = ?, AdminId = ? WHERE EmpId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpName());
        pstm.setString(2, dto.getEmpAddress());
        pstm.setString(3, String.valueOf(dto.getEmpContact()));
        pstm.setString(4, dto.getCategory());
        pstm.setString(5,dto.getShiftId());
        pstm.setString(6,dto.getAdminId());
        pstm.setString(7, dto.getEmpId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public EmployeeDto searchEmployee(String id) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM Employee WHERE EmpId = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1,id);

            ResultSet resultSet = pstm.executeQuery();
            EmployeeDto dto =null;
            if (resultSet.next()){
                String empId = resultSet.getString(1);
                String empName = resultSet.getString(2);
                String Address = resultSet.getString(3);
                int tel = Integer.parseInt(resultSet.getString(4));
                String category = resultSet.getString(5);
                String shiftId = resultSet.getString(6);
                String adminId = resultSet.getString(7);

                dto = new EmployeeDto( empId,empName, Address,tel,category,shiftId,adminId);
            }
            return dto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
     }
    }

    public static boolean deleteEmployee(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Employee WHERE EmpId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }

    public static List<EmployeeDto> getAllEmp() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> aniList = new ArrayList<>();

        while (resultSet.next()) {

            String empid = resultSet.getString(1);
            String empName = resultSet.getString(2);
            String address = resultSet.getString(3);
            int contact= resultSet.getInt(4);
            String category= resultSet.getString(5);
            String sheduleId= resultSet.getString(6);
            String adminId= resultSet.getString(7);


            EmployeeDto dto = new EmployeeDto(empid,empName,address,contact,category,sheduleId,adminId);

            aniList.add(dto);
        }

        return aniList;
    }
}


