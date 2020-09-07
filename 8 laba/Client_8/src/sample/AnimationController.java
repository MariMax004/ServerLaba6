package sample;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import Server.RouteRow;
import UserClient.utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;

public class AnimationController {
    static Timer timer = new Timer();

    static final int WINDOW_X = 1000;

    static final int WINDOW_Y = 800;


    void draw_dragon(int x, int y, int size, int color) {

        try {

            GraphicsContext gc = canvas.getGraphicsContext2D();

            FileInputStream inputstream = new FileInputStream("" + 0 + ".png");

            Image image = new Image(inputstream);

            gc.drawImage(image, x, y, size, size);
            Color c = Color.AQUA;
            switch (color) {
                case 0:
                    c = Color.BLUE;
                    break;
                case 1:
                    c = Color.GREEN;
                    break;
                case 2:
                    c = Color.RED;
                    break;
                case 3:
                    c = Color.CHOCOLATE;
                    break;
                case 4:
                    c = Color.BLACK;
                    break;
                case 5:
                    c = Color.WHITE;
                    break;
                case 6:
                    c = Color.ORANGE;
                    break;
            }
            gc.setStroke(c);
            gc.setLineWidth(2);
            gc.strokeRect(x, y, size, size);

        } catch (IOException exception) {

            exception.printStackTrace();

        }

    }


    @FXML
    private Canvas canvas;


    @FXML
    void initialize() {
     /*   GraphicsContext gc = canvas.getGraphicsContext2D();




    }

    public void draw() throws SQLException {

        List<RouteRow> toDrawBandsList = null;
            List<RouteRow> list = utils.connection.runShow();
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        int color_owner_id = 2;
        for (RouteRow person : toDrawBandsList) {
            switch (person.getOwner()){
                case "Ura": color_owner_id = 1;
                    break;
                case "Artur": color_owner_id = 2;
                    break;
                default: color_owner_id = 3;
                    break;
            }

            int x = Math.toIntExact(person.getX());
            int y = (int) person.getY();
            int size = Math.toIntExact(person.getId());
//            double offset = System.currentTimeMillis() / 300.;
            drawPerson(x, y, size, color_owner_id);
        }

    }
    public void drawPerson(int x, int y, int size, int color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        List<Color> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.ORANGE);
        colors.add(Color.HOTPINK);
        colors.add(Color.AQUA);
        colors.add(Color.SILVER);
        colors.add(Color.CHOCOLATE);
        gc.setFill(colors.get(color));

        gc.fillOval(x, y, size,  size);


    }*/

        canvas.setWidth(WINDOW_X);
        canvas.setHeight(WINDOW_Y);

        TimerTask task = new TimerTask() {

            public void run() {
                List<RouteRow> list = utils.connection.runShow();

                canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


                for (RouteRow r : list) {

                    int color_owner_id = (int) (r.getId() % 7);

                    int x = r.getX();

                    int y = r.getY();

                    int size = (int) (r.getDistance() / 100);

                    double offset =  System.currentTimeMillis() / 300.;

                    draw_dragon(x, (int) (y + Math.sin(offset) * 20), size, color_owner_id);

                }


            }

        };

        timer.schedule(task, 1000L, 100L);

        canvas.setOnMouseClicked(e -> {

            double mouse_x = e.getX();

            double mouse_y = e.getY();

            List<RouteRow> list = utils.connection.runShow();
            for (RouteRow r : list) {
                int x = r.getX().intValue();
                int y = (int) r.getY();
                int size = (int) (r.getDistance() / 100);
                double offset = System.currentTimeMillis() / 300.;
                if (mouse_x > x && mouse_x < (x + size) && mouse_y > (y + Math.sin(offset) * 20) && mouse_y < (y + Math.sin(offset) * 20 + size)) {
                    if (r.getOwner().equalsIgnoreCase(utils.login)) {
                        /*FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("sample/add.fxml"));
                        try {
                            loader.load();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                         */
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/update_click.fxml"));
                            Parent root = (Parent)loader.load();
                            UpdateClickController controller = loader.<UpdateClickController>getController();
                            controller.setOld_route(r);
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.showAndWait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Auth error");
                        alert.setHeaderText("Auth error");
                        alert.setContentText("Access error");
                        alert.showAndWait().ifPresent(rs -> {
                        });
                    }
                }
            }
        });
    }

}