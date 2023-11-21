package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.MedicineDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineModel {
    public static boolean deleteMedcine(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Medicine WHERE MediId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }

    public static String generateNextMediId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT MediId FROM Medicine ORDER BY MediId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentMedicineId = null;

        if (resultSet.next()) {
            currentMedicineId = resultSet.getString(1);
            return splitMediId(currentMedicineId);
        }
        return splitMediId(null);
    }

    private static String splitMediId(String currentMedicineId) {
        if (currentMedicineId != null) {
            String[] split = currentMedicineId.split("M");
            int id = Integer.parseInt(split[1]);
            id++;
            return "M00" + id;
        }
        return "m001";
    }

    public boolean saveMedicine(MedicineDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Medicine VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getMediId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, String.valueOf(dto.getPrice()));
        pstm.setString(4, String.valueOf(dto.getQty()));
        pstm.setString(5,dto.getStockStatus());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editMedicine(MedicineDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Medicine SET Name = ?, Price = ?, QTY = ?, StockStatus = ? WHERE MediId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, String.valueOf(dto.getPrice()));
        pstm.setString(3, String.valueOf(dto.getQty()));
        pstm.setString(4,dto.getStockStatus());
        pstm.setString(5, dto.getMediId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<MedicineDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Medicine";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<MedicineDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new MedicineDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Double.parseDouble(resultSet.getString(3)),
                    Integer.parseInt(resultSet.getString(4)),
                    resultSet.getString(5)
            ));
        }
        return aniList;
    }
}
