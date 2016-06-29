package main;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import resource.FileLoader;

import java.net.URL;


public class LongAppInitPreloader extends Preloader {
    ProgressBar bar;
    Stage stage;
    boolean noLoadingProgress = true;
//    URL url = getClass().getResource("panama_express_logo_big.jpg");


    private ImageView example_image = new ImageView(new Image(
            FileLoader.class.getResourceAsStream("panama_express_logo_big.jpg")));

    private Scene createPreloaderScene() {
        bar = new ProgressBar(0);
        BorderPane p = new BorderPane();
        p.setBottom(bar);
        example_image.setFitHeight(400);
        example_image.setFitWidth(400);
        example_image.setPreserveRatio(true);

        p.setCenter(example_image);
        return new Scene(p, 300, 150);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
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
