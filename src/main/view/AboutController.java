package main.view;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resource.FileLoader;


/**
 * This Controller class is the interface for the about_window.fxml view.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.1
 */
public class AboutController {

    @FXML
    private ImageView logo;
    /**
     * The default constructor.
     * This constructor method is called before the initialize() method.
     *
     * @since 1.0
     */
    public AboutController(){
    }


    /**
     * This method initializes the controller class and is automatically called right
     * after the about_window.fxml file has been loaded.
     *
     * @since 1.1
     */
    @FXML
    private void initialize() {
        logo.setImage(new Image(
                FileLoader.class.getResourceAsStream("panama_express_logo_big.jpg")));

    }

}
