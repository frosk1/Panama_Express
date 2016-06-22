package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.view.SearchController;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("THE PANAMA EXPRESS");

        initRootLayout();
        showSearchWindow();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showSearchWindow() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/search_window.fxml"));
            AnchorPane search_window = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(search_window);

            SearchController controller = loader.getController();
//            controller.setMainApp(this);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showChartWindow( PieChart chart) throws Exception{
        Stage dialog = new Stage();
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/chart_window.fxml"));
//        Parent page = (Parent) FXMLLoader.load(MainApp.class.getResource("view/chart_window.fxml"));

        BorderPane chartlayout = (BorderPane) loader.load();
        Scene scene = new Scene(chartlayout);



        chartlayout.setCenter(chart);
//        ((BorderPane) scene.getRoot()).getChildren().add(chart);

        dialog.setScene(scene);
        dialog.show();
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
