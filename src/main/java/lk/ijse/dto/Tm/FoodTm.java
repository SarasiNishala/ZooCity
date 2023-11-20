package lk.ijse.dto.Tm;

import javafx.scene.control.Button;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodTm {
    private String FoodId;
    private String Name;
    private double Price;
    private int Qty;
    private String StockStatus;
    private Button btn;
}
