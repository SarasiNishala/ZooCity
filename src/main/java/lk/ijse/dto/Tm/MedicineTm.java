package lk.ijse.dto.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javafx.scene.control.Button;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicineTm{
    private String MediId;
    private String Name;
    private double Price;
    private int Qty;
    private String StockStatus;
    private Button btn;
}
