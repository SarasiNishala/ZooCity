package lk.ijse.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodDto {
    private String FoodId;
    private String Name;
    private double Price;
    private int Qty;
}
