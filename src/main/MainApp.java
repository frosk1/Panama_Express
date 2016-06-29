package main;

import javafx.application.Preloader.StateChangeNotification;
import javafx.application.Preloader.ProgressNotification;
import javafx.concurrent.Task;
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
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import main.lucene.File_Filter;
import main.lucene.Indexer;
import main.view.SearchController;
import javafx.scene.image.Image;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;
import org.controlsfx.control.CheckComboBox;
import resource.FileLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    BooleanProperty ready = new SimpleBooleanProperty(false);
    private String database_dir = "";
    private String config_dir = "";

    @Override
    public void start(Stage primaryStage) {
        longStart();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("THE PANAMA EXPRESS");
        this.primaryStage.getIcons().add(new Image(FileLoader.class.getResourceAsStream("panama_express_logo_small.jpg")));


        ready.addListener(new ChangeListener<Boolean>(){
            public void changed(
                    ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (Boolean.TRUE.equals(t1)) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            checkForIndexFiles();
                            initRootLayout();
                            showSearchWindow();

                        }
                    });
                }
            }
        });;
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
                    // create the data to show in the CheckComboBox


            // Set person overview into the center of root layout.
            rootLayout.setCenter(search_window);

            SearchController controller = loader.getController();
            controller.initIndexDirectory(this.config_dir);
//            controller.setMainApp(this);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showIndexerWindow(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        this.database_dir = selectedDirectory.getAbsolutePath();

        if(selectedDirectory == null){
            System.out.println("nothing selected");
        }

    }

    public void checkForIndexFiles(){
        if (new File(System.getProperty("user.home"),"panama_express").exists()){
            this.config_dir= new File(System.getProperty("user.home"),"panama_express").getAbsolutePath();
        }
        else{

            final File folder = new File(System.getProperty("user.home"), "panama_express");
            this.config_dir = folder.getAbsolutePath();

            if(!folder.exists() && !folder.mkdirs()) {

                //failed to create the folder, probably exit
                throw new RuntimeException("Failed to create config directory. No indexing possible.");
            }

            final File myFile = new File(folder,"config.txt");
            try {
                showIndexerWindow();
                Stage loadingstage = new Stage();
                loadingstage.show();
                createIndexFiles(this.database_dir);
                final PrintWriter pw = new PrintWriter(myFile);
                pw.write("IndexDir="+this.config_dir);
                pw.close();
            }
            catch (FileNotFoundException z){
                z.printStackTrace();
            }
        }


    }

    public void createIndexFiles(String database_dir){
        try {
            File_Filter file_filter = new File_Filter();
            Indexer indexer = new Indexer(this.config_dir);
            indexer.createIndex(database_dir, file_filter);
            indexer.close();
        }
        catch (IOException i){
            i.printStackTrace();

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


    private void longStart() {
        //simulate long init in background
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max = 10;
                for (int i = 1; i <= max; i++) {
                    Thread.sleep(200);
                    // Send progress to preloader
                    notifyPreloader(new ProgressNotification(((double) i)/max));
                }
                // After init is ready, the app is ready to be shown
                // Do this before hiding the preloader stage to prevent the
                // app from exiting prematurely
                ready.setValue(Boolean.TRUE);

                notifyPreloader(new StateChangeNotification(
                        StateChangeNotification.Type.BEFORE_START));

                return null;
            }
        };
        new Thread(task).start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
