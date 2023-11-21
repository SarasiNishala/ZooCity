package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.AnimalDto;
import lk.ijse.dto.CageDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CageModel {
    public static boolean deleteCage(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Cage WHERE CageId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }

    public boolean saveCage(CageDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Cage VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCageId());
        pstm.setString(2, dto.getType());
        pstm.setString(3, String.valueOf(dto.getNoOfANimals()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editCage(CageDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Cage SET Type = ?, NumOfAnimals = ? WHERE CageId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCageId());
        pstm.setString(2, dto.getType());
        pstm.setString(3, String.valueOf(dto.getNoOfANimals()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<CageDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Cage";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CageDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new CageDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Integer.parseInt(resultSet.getString(3))
            ));
        }
        return aniList;
    }
}
