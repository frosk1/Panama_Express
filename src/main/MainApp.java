package main;

import javafx.application.Preloader.StateChangeNotification;
import javafx.application.Preloader.ProgressNotification;
import javafx.concurrent.Task;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
import resource.FileLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The MainApp class extends Application from the javafx Framework. This class
 * is the acutal Application class for the Panama Express application.
 * It initialize the primary Stage, setup the root layout and the search layout
 * for the application.
 *
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    BooleanProperty ready = new SimpleBooleanProperty(false);
    private String database_dir = "";
    private String config_dir = "";

    /**
     * The start method is called right after the Preloader class is finished.
     * The BooleanProperty ready has an Changelistener, to recognize the ready status
     * of the Preloader. First it will be checked if there a configdir exist, if not
     * indexing has to be done. After the indexing process finished, the primary stage
     * will be shown and filled with the root and search_window layout. This all
     * runs under a new Thread.
     *
     * @param primaryStage          Stage object representing the main Stage
     * @since 1.0
     */
    @Override
    public void start(Stage primaryStage) {
        longStart();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("The Panama Express");
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
     * The initRootLayout method initializes the root layout, using the
     * Rooutlayout.fxml view.
     *
     * @since 1.0
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
     * The showSearchWindow method shows the Search window inside the root layout,
     * using the search_window.fxml and the controller class SearchController.
     *
     * @since 1.0
     */
    public void showSearchWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/search_window.fxml"));
            AnchorPane search_window = (AnchorPane) loader.load();

            rootLayout.setCenter(search_window);

            SearchController controller = loader.getController();
            controller.initIndexDirectory(this.config_dir);

//            controller.setMainApp(this); // can be used as interface between MainApp and SearchController


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

    /**
     * The checkForIndexFiles method checks if the configdir 'panama_express', which
     * contains the index files and the config file, exist. If this is not the case
     * the IndexerWindow is shown and the acutal indexing can be processed. Therefore the
     * method createIndexFiles is called with the given database dir.
     *
     * @since 1.0
     */
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

    /**
     * The createIndexFiles method initialize a new Indexer and File_Filter
     * to create the index files for the Search Engine. The database dir is
     * given by the user.
     *
     * @param database_dir      String containing the dir path to the ICIJ database
     * @since 1.0
     */
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


    /**
     * The get PrimaryStage method returns the main stage.
     * (Getter Method for the primary stage)
     *
     * @return
     * @since 1.0
     */
    public Stage getPrimaryStage() {

        return primaryStage;
    }

    /**
     * The longStart method starts a new Task to show the Preloader stage
     * with the actual logo. This long Start is actually not needed, it is
     * just to give the user more time so setup and show the logo.
     *
     * The construct of this method is given by Oracle.
     *
     * @since 1.0
     */
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

    /**
     * A Main method for the MainAppliation to launch, it calls the start method
     * of this class.
     *
     * @param args
     * @since 1.0
     */
    public static void main(String[] args) {
        launch(args);
    }

}
