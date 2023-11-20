package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.AnimalsFoodDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalsFoodModel {
    public static List<AnimalsFoodDto> getAllAnimal() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Animals";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AnimalsFoodDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new AnimalsFoodDto(
                    resultSet.getString("AnimalTg"),
                    resultSet.getString("FoodId"),
                    resultSet.getDate("Date").toLocalDate(),
                    resultSet.getTimestamp("Time").toLocalDateTime(),
                    resultSet.getInt("Qty"),
                    resultSet.getString("Status")
            ));
        }
        return aniList;
    }

    public static List<AnimalsFoodDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM AnimalFood";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AnimalsFoodDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new AnimalsFoodDto(
                    resultSet.getString("AnimalTg"),
                    resultSet.getString("FoodId"),
                    resultSet.getDate("Date").toLocalDate(),
                    resultSet.getTimestamp("Time").toLocalDateTime(),
                    resultSet.getInt("Qty"),
                    resultSet.getString("Status")
            ));
        }
        return aniList;
    }

    public boolean saveAnimalFood(AnimalsFoodDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO AnimalFoods VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAnimalTg());
        pstm.setString(2, dto.getFoodId());
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4, String.valueOf(dto.getTime()));
        pstm.setString(5, String.valueOf(dto.getQty()));
        pstm.setString(6,dto.getStatus());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editANimalsFood(AnimalsFoodDto dto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String sql = "UPDATE AnimalFood SET FoodId = ?, Date = ?, Time = ?, Qty = ?, Status = ? WHERE AnimalTg = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getFoodId());
        pstm.setString(2, String.valueOf(dto.getDate()));
        pstm.setString(3, String.valueOf(dto.getTime()));
        pstm.setString(4, String.valueOf(dto.getQty()));
        pstm.setString(5, dto.getStatus());
        pstm.setString(6, dto.getAnimalTg());

        return pstm.executeUpdate() > 0;
    }
}
