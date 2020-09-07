package sample;

import Server.CollectionObjects.Coordinates;
import Server.CollectionObjects.Location_from;
import Server.CollectionObjects.Location_to;
import Server.CollectionObjects.Route;
import Server.Container;
import Server.RouteRow;
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

public class UpdateClickController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField add_name;

    @FXML
    private TextField add_y;

    @FXML
    private TextField add_x;

    @FXML
    private TextField add_name_from;

    @FXML
    private TextField add_x_from;

    @FXML
    private TextField add_y_from;

    @FXML
    private TextField add_name_to;

    @FXML
    private TextField add_y_to;

    @FXML
    private TextField add_x_to;

    @FXML
    private TextField add_z_to;

    @FXML
    private Button update_route;

    @FXML
    private Label nameError;

    @FXML
    private Label x_fromError;

    @FXML
    private Label name_fromError;

    @FXML
    private Label yError;

    @FXML
    private Label xError;

    @FXML
    private Label y_fromError;

    @FXML
    private Label name_toError;

    @FXML
    private Label x_toError;

    @FXML
    private Label y_toError;

    @FXML
    private Label z_toError;

    private RouteRow old_route;

    public void setOld_route(RouteRow old_route) {
        this.old_route = old_route;
        add_name.setText(old_route.getName());
        add_x.setText(old_route.getX().toString());
        add_y.setText(old_route.getY().toString());
        add_name_from.setText(old_route.getName_from());
        add_x_from.setText(Double.toString(old_route.getX_from()));
        add_y_from.setText(Long.toString(old_route.getY_from()));
        add_name_to.setText(old_route.getName_to());
        add_x_to.setText(Long.toString(old_route.getX_to()));
        add_y_to.setText(old_route.getY_to().toString());
        add_z_to.setText(old_route.getZ_to().toString());
    }

    @FXML
    void initialize() {

        update_route.setOnAction(event -> {

            Route newRoute = createNewRoute();

            if (newRoute != null) {
                String result = utils.connection.runCommand(new Container("update", newRoute));
                if (result.equalsIgnoreCase("Элемент успешно обновлен")){
                    System.out.println(result);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Результат выполнения запроса");
                    alert.setHeaderText("");
                    alert.setContentText(result);
                    alert.showAndWait().ifPresent(rs -> {
                    });
                    ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Результат выполнения запроса");
                    alert.setHeaderText("");
                    alert.setContentText(result);
                    alert.showAndWait().ifPresent(rs -> {
                    });
                }
            } else {
                System.out.println("Маршрут не обновлен. Проверьте ввод");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Маршрут не обновлен");
                alert.setHeaderText("Не все данные введены верно");
                alert.setContentText("Проверьте правильность ввода данных и попробуйте снова");
                alert.showAndWait().ifPresent(rs -> {
                });
            }

        });

    }


    public Route createNewRoute() {

        boolean error_detected = false;
        String error = "";

        nameError.setText("");
        xError.setText("");
        yError.setText("");
        name_fromError.setText("");
        x_fromError.setText("");
        y_fromError.setText("");
        name_toError.setText("");
        x_toError.setText("");
        y_toError.setText("");
        z_toError.setText("");

        int id=old_route.getId();

        String name = add_name.getText();
        if (name.isEmpty()) {
            nameError.setText("Название маршрута не может быть пустым!");
            error_detected = true;
        }

        int x = 0;
        if (add_x.getText().isEmpty()) {
            xError.setText("X не может быть пустым!");
            error_detected = true;
        } else {
            try {
                x = Integer.parseInt(add_x.getText());
                xError.setText("");
            } catch (Exception e) {
                xError.setText("X должно быть в формате int!");
                error_detected = true;
            }
        }

        int y = 0;
        if (add_y.getText().isEmpty()) {
            yError.setText("Y не может быть пустым!");
            error_detected = true;
        } else {
            try {
                y = Integer.parseInt(add_y.getText());
                yError.setText("");
            } catch (Exception e) {
                yError.setText("Y должно быть в формате int!");
                error_detected = true;
            }
        }

        String name_from = add_name_from.getText();
        if (name_from.isEmpty()) {
            name_fromError.setText("Название маршрута не может быть пустым!");
            error_detected = true;
        }
        double x_from = 0;
        if (add_x_from.getText().isEmpty()) {
            x_fromError.setText("X начала координат не может быть пустым!");
            error_detected = true;
        } else {
            try {
                x_from = Double.parseDouble(add_x_from.getText());
                x_fromError.setText("");
            } catch (Exception e) {
                x_fromError.setText("X начала координат должен быть в формате double!");
                error_detected = true;
            }
        }

        long y_from = 0;
        if (add_y_from.getText().isEmpty()) {
            y_fromError.setText("Y начала координат не может быть пустым!");
            error_detected = true;
        } else {
            try {
                y_from = Long.parseLong(add_y_from.getText());
                y_fromError.setText("");
            } catch (Exception e) {
                y_fromError.setText("Y начала координат должен быть в формате double!");
                error_detected = true;
            }
        }

        String name_to = add_name_to.getText();
        if (name_to.isEmpty()) {
            name_toError.setText("Название конца маршрута не может быть пустым!");
            error_detected = true;
        }

        long x_to = 0;
        if (add_x_to.getText().isEmpty()) {
            x_toError.setText("Х конца координат не может быть пустым!");
            error_detected = true;
        } else {
            try {
                x_to = Long.parseLong(add_y_to.getText());
                x_toError.setText("");
            } catch (Exception e) {
                x_toError.setText("Х конца координат должен быть в формате double!");
                error_detected = true;
            }
        }

        int y_to = 0;
        if (add_y_to.getText().isEmpty()) {
            y_toError.setText("Y не может быть пустым!");
            error_detected = true;
        } else {
            try {
                y_to = Integer.parseInt(add_y_to.getText());
                y_toError.setText("");
            } catch (Exception e) {
                y_toError.setText("Y конца должен быть в формате int!");
                error_detected = true;
            }
        }

        int z_to = 0;
        if (add_z_to.getText().isEmpty()) {
            z_toError.setText("Z не может быть пустым!");
            error_detected = true;
        } else {
            try {
                z_to = Integer.parseInt(add_z_to.getText());
                z_toError.setText("");
            } catch (Exception e) {
                z_toError.setText("Z конца должен быть в формате int!");
                error_detected = true;
            }
        }

        if (!error_detected) {
            Location_from from = new Location_from(x_from, y_from, name_from);
            Location_to to = new Location_to(x_to, y_to, z_to, name_to);
            Coordinates coord = new Coordinates(x, y);

            //Поле не может быть null, Значение этого поля должно генерироваться автоматически
            ZonedDateTime now = ZonedDateTime.now();

            return new Route(id, name, coord, now, from, to, utils.login);
        }
        return null;
    }

}
