package main;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import main.view.SearchController;
import resource.FileLoader;


/**
 * The LonaAppInitPreloader extends the Preloader class from the javafx Framework.
 * This Preloader class mainly is therefore to setup a Preloader, can can show
 * the logo and give the user time to set everythin up. The Preloader is not
 * needed, due to the fact, that the main application is fast enough to initialize.
 * The construct of this code if given by Oracle.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class LongAppInitPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;
    boolean noLoadingProgress = true;


    private ImageView example_image = new ImageView(new Image(
            FileLoader.class.getResourceAsStream("panama_express_logo_big.jpg")));

    private Scene createPreloaderScene() {
        bar = new ProgressBar(0);
        bar.setStyle("-fx-accent: #1d1d1d;");
        bar.setPrefSize(1000.0,30.0);
        BorderPane p = new BorderPane();
        p.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #1d1d1d, #363636)");
        p.setBottom(bar);

        example_image.setFitHeight(400);
        example_image.setFitWidth(400);
        example_image.setPreserveRatio(true);
        p.setCenter(example_image);
        Label l = new Label("Version 1.21");
        l.setTextFill(Color.web("#ffffff"));
        p.setTop(l);
        Scene s = new Scene(p, 300, 150);

        return s;
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("The Paname Express");
        this.stage.getIcons().add(new Image(FileLoader.class.getResourceAsStream("panama_express_logo_small.jpg")));
        this.stage.setWidth(800);
        this.stage.setHeight(600);

        stage.setScene(createPreloaderScene());
        stage.show();

    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        //application loading progress is rescaled to be first 50%
        //Even if there is nothing to load 0% and 100% events can be
        // delivered
        if (pn.getProgress() != 1.0 || !noLoadingProgress) {
            bar.setProgress(pn.getProgress()/2);
            if (pn.getProgress() > 0) {
                noLoadingProgress = false;
            }
        }
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        //ignore, hide after application signals it is ready
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof ProgressNotification) {
            //expect application to send us progress notifications
            //with progress ranging from 0 to 1.0
            double v = ((ProgressNotification) pn).getProgress();
            if (!noLoadingProgress) {
                //if we were receiving loading progress notifications
                //then progress is already at 50%.
                //Rescale application progress to start from 50%
                v = 0.5 + v/2;
            }
            bar.setProgress(v);
        } else if (pn instanceof StateChangeNotification) {
            //hide after get any state update from application
            stage.hide();
        }
    }
}
