package main;

import com.sun.javafx.application.LauncherImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws java.io.IOException{
        LauncherImpl.launchApplication(MainApp.class, LongAppInitPreloader.class, args);
    }
}
