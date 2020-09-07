package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Server.CollectionObjects.Route;
import Server.Container;
import Server.RouteRow;
import UserClient.utils;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController {
    private static String lang_param = "Русский";
    public static ObservableList<RouteRow> table_Observ = FXCollections.observableArrayList();
    private final Comparator<RouteRow> ID_COMPARATOR = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
    private int idtoremove=-1;

    @FXML
    public ComboBox<String> location_param;

    @FXML
    public Button translate;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<RouteRow, Integer> id_col;

    @FXML
    private TableColumn<RouteRow, String> name_col;

    @FXML
    private TableColumn<RouteRow, Double> distance_col;

    @FXML
    private TableColumn<RouteRow, ZonedDateTime> creation_date_col;

    @FXML
    private TableColumn<RouteRow, Integer> x_col;

    @FXML
    private TableColumn<RouteRow, Integer> y_col;

    @FXML
    private TableColumn<RouteRow, String > name_from_col;

    @FXML
    private TableColumn<RouteRow, Double> x_from_col;

    @FXML
    private TableColumn<RouteRow, Long> y_from_col;

    @FXML
    private TableColumn<RouteRow, String> name_to_col;

    @FXML
    private TableColumn<RouteRow, Long> x_to_col;

    @FXML
    private TableColumn<RouteRow, Integer> y_to_col;

    @FXML
    private TableColumn<RouteRow, Integer> z_to;

    @FXML
    private TableColumn<RouteRow, String> owner_col;

    @FXML
    private Button add_button;

    @FXML
    private Button help_button;

    @FXML
    private Button history_button;

    @FXML
    private Button update_button;

    @FXML
    private Button removeAnyByDistance_button;

    @FXML
    private Button remove_button;

    @FXML
    private Button removeGreater_button;

    @FXML
    private Button countByDistance_button;

    @FXML
    private Button clear_button;

    @FXML
    private Button executeScript_button;

    @FXML
    private Button info_button;

    @FXML
    private Button show_button;

    @FXML
    private Button addIfMax_button;

    @FXML
    private TableView<RouteRow> resultTable;

    @FXML
    private TextField script_name;

    @FXML
    private Button animation_button;

    @FXML
    private TextField filter_id;

    @FXML
    private TextField filter_name;

    @FXML
    private TextField filter_date;

    @FXML
    private TextField filter_distance;

    @FXML
    private TextField filter_y;

    @FXML
    private TextField filter_x;

    @FXML
    private TextField filter_name_from;

    @FXML
    private TextField filter_x_from;

    @FXML
    private TextField filter_y_from;

    @FXML
    private TextField filter_name_to;

    @FXML
    private TextField filter_x_to;

    @FXML
    private TextField filter_y_to;

    @FXML
    private TextField filter_z_to;

    @FXML
    private TextField filter_owner;

    @FXML
    void initialize() {


        animation_button.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            //loader.setLocation();
            Parent root;
            try {
                root = loader.load(getClass().getResource("/sample/fxml/animation.fxml"),Main.resourceBundle);
                //root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
        ObservableList<String> langs= FXCollections.observableArrayList("Русский", "English","Español");

        location_param.setItems(langs);
        location_param.setValue(lang_param);

        translate.setOnAction(event->{
            switch (location_param.getValue()){
                case "Русский":
                    Main.resourceBundle = ResourceBundle.getBundle("resources/locals",new Locale("ru"), new CustomResourceBundleControl("UTF-8"));
                    lang_param = "Русский";
                    break;
                case "English":
                    Main.resourceBundle = ResourceBundle.getBundle("resources/locals", new  Locale("en"), new CustomResourceBundleControl("UTF-8"));
                    lang_param = "English";
                    break;
                case "Español":
                    Main.resourceBundle = ResourceBundle.getBundle("resources/locals",new  Locale("es"), new CustomResourceBundleControl("UTF-8"));
                    lang_param = "Español";
                    break;
            }
            Scene scene = remove_button.getScene();
            try {
                scene.setRoot(FXMLLoader.load(getClass().getResource("/sample/fxml/app.fxml"), Main.resourceBundle));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        try {
            updateTableALL();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        add_button.setOnAction(event -> {
            //add_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            //loader.setLocation();
            Parent root;
            try {
                root = loader.load(getClass().getResource("/sample/fxml/add.fxml"),Main.resourceBundle);
                //root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
             */
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        update_button.setOnAction(event -> {

            //update_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/update.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        history_button.setOnAction(event -> {
            String result = utils.connection.runCommand(new Server.Container("history"));
            System.out.println(result);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("История операций");
            alert.setContentText(result);
            alert.showAndWait().ifPresent(rs -> {
            });
        });

        info_button.setOnAction(event -> {
            String result = utils.connection.runCommand(new Server.Container("info"));
            System.out.println(result);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация о коллекции");
            alert.setHeaderText("Информация о коллекции");
            alert.setContentText(result);
            alert.showAndWait().ifPresent(rs -> {
            });
        });

        help_button.setOnAction(event -> {
            String result = utils.connection.runCommand(new Server.Container("help"));
            System.out.println(result);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Справка");
            alert.setHeaderText("Список доступных команд");
            alert.setContentText(result);
            alert.showAndWait().ifPresent(rs -> {
            });
        });

        show_button.setOnAction(event -> {
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        clear_button.setOnAction(event -> {
            String result = utils.connection.runCommand(new Server.Container("clear"));
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println(result);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Очистка коллекции");
            //alert.setHeaderText("Список доступных команд");
            alert.setContentText(result);
            alert.showAndWait().ifPresent(rs -> {
            });
        });

        addIfMax_button.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();

            Parent root;
            try {
                root = loader.load(getClass().getResource("/sample/fxml/addIfMax.fxml"),Main.resourceBundle);
                //root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


        removeGreater_button.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/remove_greater.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        remove_button.setOnAction(event -> {
            if (idtoremove!=-1) {
                String result = utils.connection.runCommand(new Container("remove", new Route(idtoremove)));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Результат выполнения запроса");
                alert.setHeaderText(result);
                if(result.equalsIgnoreCase("Элемент успешно удален")) idtoremove=-1;
                //alert.setContentText(result);
                alert.showAndWait().ifPresent(rs -> {
                });
                try {
                    updateTableALL();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        removeAnyByDistance_button.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/remove_any.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        countByDistance_button.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/fxml/count.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        executeScript_button.setOnAction(event -> {
            if (!script_name.getText().isEmpty()){
                utils.RunScript(script_name.getText());
            }
            try {
                updateTableALL();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

        resultTable.setOnMouseClicked(event -> {
            try {
                idtoremove = resultTable.getSelectionModel().getSelectedItem().getId();
            }catch (Exception e){
            }
            if (event.getClickCount() == 2) {
                System.out.println(resultTable.getSelectionModel().getSelectedItem().getId());
                if (resultTable.getSelectionModel().getSelectedItem().getOwner().equalsIgnoreCase(utils.login)){
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/fxml/update_click.fxml"));
                        Parent root = (Parent)loader.load();
                        UpdateClickController controller = loader.<UpdateClickController>getController();
                        controller.setOld_route(resultTable.getSelectionModel().getSelectedItem());
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                        updateTableALL();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        id_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Integer>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<RouteRow, String>("name"));
        distance_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Double>("distance"));
        creation_date_col.setCellValueFactory(new PropertyValueFactory<RouteRow, ZonedDateTime>("creationDate"));
        x_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Integer>("x"));
        y_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Integer>("y"));
        name_from_col.setCellValueFactory(new PropertyValueFactory<RouteRow, String>("name_from"));
        x_from_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Double>("x_from"));
        y_from_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Long>("y_from"));
        name_to_col.setCellValueFactory(new PropertyValueFactory<RouteRow, String>("name_to"));
        x_to_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Long>("x_to"));
        y_to_col.setCellValueFactory(new PropertyValueFactory<RouteRow, Integer>("y_to"));
        z_to.setCellValueFactory(new PropertyValueFactory<RouteRow, Integer>("z_to"));
        owner_col.setCellValueFactory(new PropertyValueFactory<RouteRow, String>("owner"));
        resultTable.getSortOrder().add(id_col);

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    updateTableALL();
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);


    }

    /*private void update_table(){
        filter_name.setText("");
        resultTable.getItems().clear();
        List<RouteRow> list = utils.connection.runShow();
        for (RouteRow r: list) {
            resultTable.getItems().add(r);
        }
        resultTable.sort();
    }
    */

    public void updateTableALL() throws SQLException {
        //RouteRow selected = resultTable.getSelectionModel().getSelectedItem();
        List<RouteRow> list = utils.connection.runShow();
        table_Observ.clear();
        for (RouteRow r : list) {
            table_Observ.add(r);
        }
        //if(list.contains(selected)) resultTable.getSelectionModel().select(selected);

        FilteredList<RouteRow> filteredData = new FilteredList<>(table_Observ, p -> true);

        filter_id.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getId().toString().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_name.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_date.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getCreationDate().toString().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_distance.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Double.toString(route.getDistance()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_x.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getX().toString().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_y.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getY().toString().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_name_from.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getName_from().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });

        filter_x_from.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Double.toString(route.getX_from()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });

        filter_y_from.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Long.toString(route.getY_from()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });

        filter_name_to.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getName_to().toString().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_x_to.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Long.toString(route.getX_to()).indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_y_to.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getY_to().toString().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_z_to.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getZ_to().toString().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        filter_owner.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(route -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (route.getOwner().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        SortedList<RouteRow> sortedData = new SortedList<>(filteredData);

        ObjectBinding<Comparator<RouteRow>> customBinding = new ObjectBinding<Comparator<RouteRow>>() {
            {
                bind(resultTable.comparatorProperty());
            }

            protected Comparator<RouteRow> computeValue() {
                if (resultTable.getComparator() != null) {
                    return resultTable.getComparator();
                } else {
                    return ID_COMPARATOR;
                }
            }
        };

        sortedData.comparatorProperty().bind(customBinding);
        //sortedData.comparatorProperty().bind(resultTable.comparatorProperty());
        resultTable.setItems(sortedData);
        resultTable.sort();
    }



}