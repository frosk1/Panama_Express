package main;

import com.sun.javafx.application.LauncherImpl;


/**
 * This Main class is the starting point of the MainApplication. It launches the
 * LongAppInitPreloader and the MainApplication.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) throws java.io.IOException{
        LauncherImpl.launchApplication(MainApp.class, LongAppInitPreloader.class, args);
    }
}
