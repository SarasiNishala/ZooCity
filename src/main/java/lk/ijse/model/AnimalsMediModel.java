package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.AnimalsFoodDto;
import lk.ijse.dto.AnimalsMediDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalsMediModel {

    public static List<AnimalsMediDto> getAllAnimal() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM AnimalMedicine";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AnimalsMediDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new AnimalsMediDto(
                    resultSet.getString("AnimalTg"),
                    resultSet.getString("ANimalMedi"),
                    resultSet.getDate("Date"),
                    resultSet.getTime("Time"),
                    resultSet.getInt("Qty")
            ));
        }
        return aniList;
    }

    public static List<AnimalsMediDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM AnimalMedicine";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AnimalsMediDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new AnimalsMediDto(
                    resultSet.getString("AnimalTg"),
                    resultSet.getString("MediId"),
                    resultSet.getDate("Date"),
                    resultSet.getTime("Time"),
                    resultSet.getInt("Qty")
            ));
        }
        return aniList;
    }

    public boolean saveAnimalsMedi(AnimalsMediDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO AnimalMedicine VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAnimalTg());
        pstm.setString(2, dto.getMediId());
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4, String.valueOf(dto.getTime()));
        pstm.setString(5, String.valueOf(dto.getQty()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editANimalsMedi(AnimalsMediDto dto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE AnimalFood SET MediId = ?, Date = ?, Time = ?, Qty = ?, Status = ? WHERE AnimalTg = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMediId());
        pstm.setString(2, String.valueOf(dto.getDate()));
        pstm.setString(3, String.valueOf(dto.getTime()));
        pstm.setString(4, String.valueOf(dto.getQty()));
        pstm.setString(6, dto.getAnimalTg());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteANimalMedi(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM AnimalFood WHERE AnimalTg = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
}

