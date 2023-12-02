package lk.ijse.dto.Tm;

import java.time.LocalDate;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketTm {
    private String TicketNo;
    private String TicketType;
    private Double Price;
    private LocalDate Date;
    private String AdminId;
    private String IncomeId;
}
