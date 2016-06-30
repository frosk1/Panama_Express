package main.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.MainApp;
import resource.FileLoader;

import java.io.IOException;

/**
 * This Controller class is the interface for the RootLayout.fxml view.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.1
 */
public class RootController {

    @FXML
    private MenuBar bar;

    @FXML
    private MenuItem about;

    private Pane aboutlayout;

    /**
     * The default constructor.
     * This constructor method is called before the initialize() method.
     *
     * @since 1.1
     */
    public RootController(){
    }


    /**
     * This method initializes the controller class and is automatically called right
     * after the about_window.fxml file has been loaded.
     *
     * @since 1.1
     */
    @FXML
    private void initialize() {

        about.setOnAction(this::showAboutWindow);
    }

    /**
     * The showAboutWindow method opens a new Stage, showing the Logo
     * and links for references and contribution.
     *
     * @param e
     * @since 1.1
     */
    public void showAboutWindow(ActionEvent e) {
            try {

                Stage stage = new Stage();
                stage.setTitle("The Panma Express");
                stage.getIcons().add(new Image(FileLoader.class.getResourceAsStream("panama_express_logo_small.jpg")));

                //Fill stage with content
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/about_window.fxml"));
                aboutlayout = (Pane) loader.load();
                AboutController controller = loader.getController();

                // Show the scene containing the root layout.
                Scene scene = new Scene(aboutlayout);
                stage.setScene(scene);
                stage.show();

            }
            catch (IOException z){
                z.printStackTrace();
            }
    }

}
