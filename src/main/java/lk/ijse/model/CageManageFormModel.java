package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.AnimalsFoodDto;
import lk.ijse.dto.CageManageFormDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CageManageFormModel {

    public static List<CageManageFormDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM CageControl";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CageManageFormDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new CageManageFormDto(
                    resultSet.getString("EmpId"),
                    resultSet.getString("CageId"),
                    resultSet.getDate("Date").toLocalDate(),
                    resultSet.getTimestamp("Time").toLocalDateTime()
            ));
        }
        return aniList;
    }

    public static List<CageManageFormDto> getAllCages() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM CageControl";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CageManageFormDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new CageManageFormDto(
                    resultSet.getString("EmpId"),
                    resultSet.getString("CageId"),
                    resultSet.getDate("Date").toLocalDate(),
                    resultSet.getTimestamp("Time").toLocalDateTime()
            ));
        }
        return aniList;
    }

    public boolean saveCageForm(CageManageFormDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO CageControl VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getEmpId());
        pstm.setString(2, dto.getCageId());
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4, String.valueOf(dto.getTime()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editCageControlForm(CageManageFormDto dto) throws SQLException {
        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE CageControl SET CageId = ?, Date = ?, Time = ?, Status = ? WHERE EmpId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCageId());
        pstm.setString(2, String.valueOf(dto.getDate()));
        pstm.setString(3, String.valueOf(dto.getTime()));
        pstm.setString(6, dto.getEmpId());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteCageForm(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM CageControl WHERE EmpId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
}
