package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.MedicineDto;
import lk.ijse.dto.SalaryDto;
import lk.ijse.dto.WorkScheduleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {
    public static boolean deleteSalary(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Salary WHERE SalaryId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextSalaryId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT SalaryId FROM Salary ORDER BY SalaryId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentSalaryId = null;

        if (resultSet.next()) {
            currentSalaryId = resultSet.getString(1);
            return splitSalaryId(currentSalaryId);
        }
        return splitSalaryId(null);
    }

    private static String splitSalaryId(String currentCageId) {
        if (currentCageId != null) {
            String[] split = currentCageId.split("SL");
            int id = Integer.parseInt(split[1]);
            id++;
            return "SL00" + id;
        }
        return "SL001";
    }

    public boolean saveSalary(SalaryDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Salary VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getSalaryId());
        pstm.setString(2, dto.getEmpId());
        pstm.setString(3, String.valueOf(dto.getPayment()));
        pstm.setString(4, String.valueOf(dto.getDate()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<SalaryDto> getAllSalary() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Salary";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<SalaryDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new SalaryDto(
                    resultSet.getString("SalaryId"),
                    resultSet.getString("EmpId"),
                    resultSet.getDouble("Payment"),
                    resultSet.getDate("Date").toLocalDate()
            ));
        }
        return aniList;
    }
}
