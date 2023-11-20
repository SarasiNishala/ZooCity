package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.AnimalDto;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalModel {
    public static boolean deleteAnimal(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Animals WHERE AnimalTg = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;

    }

    public boolean saveAnimal(AnimalDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Animals VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAnimalTg());
        pstm.setString(2, dto.getCategory());
        pstm.setString(3, dto.getAnimalType());
        pstm.setString(4,dto.getCageId());
        pstm.setString(5,dto.getAdminId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editAnimal(AnimalDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Animals SET Category = ?, AnimalType = ?, CageId = ?, AdminId = ? WHERE AnimalTg = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getCategory());
        pstm.setString(2, dto.getAnimalType());
        pstm.setString(3,dto.getCageId());
        pstm.setString(4,dto.getAdminId());
        pstm.setString(5, dto.getAnimalTg());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public AnimalDto searchAnimal(String tag) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM Animals WHERE AnimalId = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setObject(1,tag);

            ResultSet resultSet = pstm.executeQuery();
            AnimalDto dto =null;
            if (resultSet.next()){
                String animalTg = resultSet.getString(1);
                String category = resultSet.getString(2);
                String type = resultSet.getString(3);
                String cageId = resultSet.getString(4);
                String adminId = resultSet.getString(5);

                dto = new AnimalDto(animalTg,category,type,cageId,adminId);
            }
            return dto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<AnimalDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Animals";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<AnimalDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new AnimalDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return aniList;
    }
}
