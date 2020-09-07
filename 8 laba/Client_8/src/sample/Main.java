package sample;

import UserClient.ConnectModule;
import UserClient.History;
import UserClient.utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static ResourceBundle resourceBundle;

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.resourceBundle = ResourceBundle.getBundle("resources/locals", new Locale("ru"), new CustomResourceBundleControl("UTF-8"));
        //  Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("/sample/fxml/sample.fxml"), Main.resourceBundle);
            primaryStage.setTitle("Authorization");
            primaryStage.setScene(new Scene(root, 700, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }}
    public static void main(String[] args) {

        try {

            if (args.length < 2) {
                System.out.println("Введите адрес сервера и порт");
                return;
            }

            String addr = args[0];
            int port = 0;
            try {
                port = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Порт должен быть числом");
                return;
            }
         utils.connection = new ConnectModule(addr, port);

            utils.history = new History();

        } catch (Exception e) {
            e.printStackTrace();
        }


        launch(args);

    }
}