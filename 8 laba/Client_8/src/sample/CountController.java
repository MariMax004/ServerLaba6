package sample;

import Server.Container;
import UserClient.utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class CountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button count_route;

    @FXML
    private TextField distance;

    @FXML
    private Label dist_error;

    @FXML
    void initialize() {
        count_route.setOnAction(event -> {

            boolean error_detected = false;
            String error = "";


            double dist = 0;
            if (distance.getText().isEmpty()) {
                dist_error.setText("distance не может быть пустым!");
                error_detected = true;
            } else {
                try {
                    dist = Double.parseDouble(distance.getText());
                    dist_error.setText("");
                } catch (Exception e) {
                    dist_error.setText("distance должно быть в формате double!");
                    error_detected = true;
                }
            }


            if (!error_detected) {
                String result = utils.connection.runCommand(new Container("count_by_distance", dist));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат выполнения запроса");
                //alert.setHeaderText("");
                alert.setContentText(result);
                alert.showAndWait().ifPresent(rs -> {
                });
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
            } else {
                System.out.println("Ошибка ввода: Проверьте ввод");
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
