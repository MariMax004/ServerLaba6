package sample;

import Server.CollectionObjects.Coordinates;
import Server.CollectionObjects.Location_from;
import Server.CollectionObjects.Location_to;
import Server.CollectionObjects.Route;
import Server.Container;
import UserClient.utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class RemoveGreaterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button remove_route;

    @FXML
    private TextField remove_id;

    @FXML
    private Label remove_idError;

    @FXML
    void initialize() {
        remove_route.setOnAction(event -> {

            boolean error_detected = false;
            String error = "";


            double dist = 0;
            if (remove_id.getText().isEmpty()) {
                remove_idError.setText("distance не может быть пустым!");
                error_detected = true;
            } else {
                try {
                    dist = Double.parseDouble(remove_id.getText());
                    remove_idError.setText("");
                } catch (Exception e) {
                    remove_idError.setText("distance должно быть в формате double!");
                    error_detected = true;
                }
            }


            if (!error_detected) {
                String result = utils.connection.runCommand(new Container("remove_greater", dist));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат выполнения запроса");
                //alert.setHeaderText("");
                alert.setContentText(result);
                alert.showAndWait().ifPresent(rs -> {
                });
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            } else {
                System.out.println("Маршрут не удален. Проверьте ввод");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка ввода");
                alert.setHeaderText("Не все данные введены верно");
                alert.setContentText("Проверьте правильность ввода данных и попробуйте снова");
                alert.showAndWait().ifPresent(rs -> {
                });
            }
        });

    }


    public Double getDistance() {


        return null;
    }

}
