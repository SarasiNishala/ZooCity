package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.CageDto;
import lk.ijse.dto.WorkScheduleDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkScheduleModel {
    public static String generateNextScheduleId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT ScheduleId FROM Schedule ORDER BY ScheduleId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentScheduleId = null;

        if (resultSet.next()) {
            currentScheduleId = resultSet.getString(1);
            return splitScheduleId(currentScheduleId);
        }
        return splitScheduleId(null);
    }

    private static String splitScheduleId(String currentScheduleId) {
        if (currentScheduleId != null) {
            String[] split = currentScheduleId.split("S");
          int id = Integer.parseInt(split[1]);
            id++;
           return "S00" + id;
        }
        return "S001";
    }

    public static boolean deleteSchedule(String code) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM Schedule WHERE ScheduleId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, code);

        return pstm.executeUpdate() > 0;
    }

    public boolean saveSchedule(WorkScheduleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Schedule VALUES(?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getScheduleId());
        pstm.setString(2, String.valueOf(dto.getHours()));
        pstm.setString(3, String.valueOf(dto.getDate()));

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean editSchedule(WorkScheduleDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Schedule SET Hours = ?, Date = ? WHERE ScheduleId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, String.valueOf(dto.getHours()));
        pstm.setString(2, String.valueOf(dto.getDate()));
        pstm.setString(1, dto.getScheduleId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<WorkScheduleDto> getAll() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Schedule";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<WorkScheduleDto> aniList = new ArrayList<>();

        while (resultSet.next()) {
            aniList.add(new WorkScheduleDto(
                    resultSet.getString("ScheduleId"),
                    Integer.parseInt(resultSet.getString("Hours")),
                    resultSet.getDate("Date").toLocalDate()
            ));
        }
        return aniList;
    }
}
