package lk.ijse.zoocity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/DashBoard.fxml"));
        Scene scene = new Scene(rootNode);

        stage.setTitle("DashBoard");
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.show();
    }
}
