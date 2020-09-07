package sample;

import Server.CollectionObjects.Route;
import Server.Container;
import UserClient.utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class RemoveController {

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


            int id = 0;
            if (remove_id.getText().isEmpty()) {
                remove_idError.setText("id не может быть пустым!");
                error_detected = true;
            } else {
                try {
                    id = Integer.parseInt(remove_id.getText());
                    remove_idError.setText("");
                } catch (Exception e) {
                    remove_idError.setText("id должно быть в формате int!");
                    error_detected = true;
                }
            }


            if (!error_detected) {
                String result = utils.connection.runCommand(new Container("remove", new Route(id)));
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
