package sample;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import UserClient.utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private static String lang_param = "Русский";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button SignUpButton;

    @FXML
    private Button authButton;

    @FXML
    private ComboBox<String> language;

    @FXML
    private Button change;

    @FXML
    void initialize() {
        ObservableList<String> langs= FXCollections.observableArrayList("Русский", "English","Español");

        language.setItems(langs);
        language.setValue(lang_param);

        change.setOnAction(event->{
            switch (language.getValue()) {
                case "Русский":
                    Main.resourceBundle = ResourceBundle.getBundle("resources/locals", new Locale("ru"), new CustomResourceBundleControl("UTF-8"));
                    lang_param = "Русский";
                    break;
                case "English":
                    Main.resourceBundle = ResourceBundle.getBundle("resources/locals", new Locale("en"), new CustomResourceBundleControl("UTF-8"));
                    lang_param = "English";
                    break;
                case "Español":
                    Main.resourceBundle = ResourceBundle.getBundle("resources/locals", new Locale("es"), new CustomResourceBundleControl("UTF-8"));
                    lang_param = "Español";
                    break;
            }
            Scene scene = change.getScene();
            try {
                scene.setRoot(FXMLLoader.load(getClass().getResource("/sample/fxml/sample.fxml"), Main.resourceBundle));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            });
        SignUpButton.setOnAction(event -> {
            if (!login_field.getText().trim().isEmpty() && !password_field.getText().trim().isEmpty()) {

                String loginText = login_field.getText().trim();
                String passwordText = password_field.getText().trim();
                if (loginUser(loginText, passwordText)) {

                    //SignUpButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/fxml/app.fxml"));
                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/sample/fxml/app.fxml"), Main.resourceBundle);
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (language.getValue().equalsIgnoreCase("Русский")) {
                        System.out.println("Логин или пароль неверен");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка входа!");
                        alert.setHeaderText("Ошибка входа!");
                        alert.setContentText("Неверный логин или пароль");
                        alert.showAndWait().ifPresent(rs -> {
                        });
                    } else if (language.getValue().equalsIgnoreCase("English")) {
                        System.out.println("Login or password is wrong");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login failed!");
                        alert.setHeaderText("Login failed!");
                        alert.setContentText("Login or password is wrong");
                        alert.showAndWait().ifPresent(rs -> {
                        });
                    }else if (language.getValue().equalsIgnoreCase("Español")){
                    System.out.println("Nombre de usuario o contraseña incorrecta");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de Inicio de sesión!");
                    alert.setHeaderText("Error de Inicio de sesión!");
                    alert.setContentText("Nombre de usuario o contraseña incorrecta");
                    alert.showAndWait().ifPresent(rs -> {
                    });}
                    else {
                        if (language.getValue().equalsIgnoreCase("Русский"))
                            System.out.println("Вы не ввели логин или пароль");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Вход в систему");
                        alert.setHeaderText("Ошибка входа!");
                        alert.setContentText("Вы не ввели логин или пароль");
                        alert.showAndWait().ifPresent(rs -> {
                        });
                    }
                }}
        });


        authButton.setOnAction(event -> {
            if (!login_field.getText().trim().isEmpty() && !password_field.getText().trim().isEmpty()) {
                String newloginText = login_field.getText().trim();
                String newpasswordText = password_field.getText().trim();
                String result = utils.connection.runCommand(new Server.Container("register", newloginText, newpasswordText));
                // if (language.getValue().equalsIgnoreCase("Русский")){
                if (result.equalsIgnoreCase("Такой пользователь уже существует!")) {
                    System.out.println("Вы не ввели логин или пароль");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Регистрация");
                    alert.setHeaderText("Такой пользователь уже существует!");
                    alert.setContentText("Пожалуйста придумайте другой логин");
                    alert.showAndWait().ifPresent(rs -> {
                    });


                } else {
                    System.out.println("Вы не ввели логин или пароль");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Регистрация");
                    alert.setHeaderText("Регистрация успешна");
                    alert.setContentText("Вы можете использовать новый логин и пароль для входа");
                    alert.showAndWait().ifPresent(rs -> {
                    });
                }

            login_field.setText("");
            password_field.setText("");

        } else {
                System.out.println("Вы не ввели логин или пароль");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Регистрация");
                alert.setHeaderText("Ошибка регистрации!");
                alert.setContentText("Вы не ввели логин или пароль");
                alert.showAndWait().ifPresent(rs -> {
                });
            }
        });
}

    private boolean loginUser(String loginText, String passwordText) {
        String result = utils.connection.runCommand(new Server.Container("login", loginText, passwordText));
        if (result.equalsIgnoreCase("Авторизация успешна")) {
            utils.login = loginText;
            utils.password = passwordText;
            return true;
        }
        return false;
    }

}
