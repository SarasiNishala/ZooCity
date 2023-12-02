package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.TicketDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketModel {
    public static String generateNextTicketId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT TicketNo FROM Ticket ORDER BY TicketNo DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentTicketId = null;

        if (resultSet.next()) {
            currentTicketId = resultSet.getString(1);
            return splitTicketId(currentTicketId);
        }
        return splitTicketId(null);
    }

    private static String splitTicketId(String currentTicketId) {
        if (currentTicketId != null) {
            String[] split = currentTicketId.split("T");
            int id = Integer.parseInt(split[1]);
            id++;
            return "T00" + id;
        }
        return "T001";
    }

    public static String generateNextIncomeId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT IncomeId FROM Ticket ORDER BY IncomeId DESC LIMIT 1";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String currentIncomeId = null;

        if (resultSet.next()) {
            currentIncomeId = resultSet.getString(1);
            return splitIncomeId(currentIncomeId);
        }
        return splitIncomeId(null);
    }

    private static String splitIncomeId(String currentIncomeId) {
        if (currentIncomeId != null) {
            String[] split = currentIncomeId.split("I");
            int id = Integer.parseInt(split[1]);
            id++;
            return "I00" + id;
        }
        return "I001";
    }

    public boolean saveTicket(TicketDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Ticket VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getTicketNo());
        pstm.setString(2, dto.getTicketType());
        pstm.setString(3, String.valueOf(dto.getPrice()));
        pstm.setString(4, String.valueOf(dto.getDate()));
        pstm.setString(5,dto.getAdminId());
        pstm.setString(6,dto.getIncomeId());

        pstm.setString(5, dto.getIncomeId());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

       public boolean editTicket(TicketDto dto) throws SQLException {
       Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE Ticket SET  TicketType = ?, Price = ?, Date = ?, AdminId = ?, IncomeId = ? WHERE AnimalTg = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getTicketType());
        pstm.setString(2, String.valueOf(dto.getPrice()));
        pstm.setString(3, String.valueOf(dto.getDate()));
        pstm.setString(4,dto.getAdminId());
        pstm.setString(5, dto.getIncomeId());
        pstm.setString(6,dto.getTicketNo());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<TicketDto> getAllTicket() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Ticket";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<TicketDto> aniList = new ArrayList<>();

        while (resultSet.next()) {

            String TicketNo = resultSet.getString(1);
            String TicketType = resultSet.getString(2);
            Double Price = Double.valueOf(resultSet.getString(3));
            String AdminId = resultSet.getString(4);
            String IncomeId = resultSet.getString(5);
            LocalDate Date= resultSet.getDate(6).toLocalDate();

            TicketDto dto = new TicketDto(TicketNo,TicketType,Price,AdminId,IncomeId,Date);

            aniList.add(dto);
        }

        return aniList;
    }
}
