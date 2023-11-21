package lk.ijse.dto.Tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CageTm {
    private String CageId;
    private String Type;
    private int NoOfANimals;
    private Button btn;
}
