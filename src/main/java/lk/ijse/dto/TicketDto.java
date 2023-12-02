package lk.ijse.dto;

import java.time.LocalDate;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketDto {
        private String TicketNo;
        private String TicketType;
        private Double Price;
        private String IncomeId;
        private String AdminId;
        private LocalDate Date;
}
