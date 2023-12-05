package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.FoodDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodModel {
    public static String generateNextFoodId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT FoodId FROM Food ORDER BY FoodId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentOFoodId = null;

        if (resultSet.next()) {
            currentOFoodId = resultSet.getString(1);
            return splitFoodId(currentOFoodId);
        }
        return splitFoodId(null);
    }

    private static String splitFoodId(String currentFoodId) {
        if (currentFoodId != null) {
            String[] split = currentFoodId.split("F");
            int id = Integer.parseInt(split[1]);
            id++;
            return "F00" + id;
        }
        return "F001";
    }

    public static boolean deleteFood(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Food WHERE FoodId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }


    public boolean saveFood(FoodDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Food VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getFoodId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, String.valueOf(dto.getPrice()));
        pstm.setString(4, String.valueOf(dto.getQty()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editFood(FoodDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Food SET Name = ?, Price = ?, QTY = ?, StockStatus = ? WHERE FoodId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, String.valueOf(dto.getPrice()));
        pstm.setString(3, String.valueOf(dto.getQty()));
        pstm.setString(5, dto.getFoodId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static List<FoodDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Food";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<FoodDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new FoodDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Double.parseDouble(resultSet.getString(3)),
                    Integer.parseInt(resultSet.getString(4))
            ));
        }
        return aniList;
    }

    public boolean updateStock(String foodId, int qty) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Food SET Qty = Qty-? WHERE FoodId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, String.valueOf(qty));
        pstm.setString(2, foodId);

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
}
