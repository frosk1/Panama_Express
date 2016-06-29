package resource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class FileLoader {

    public final ObservableList<String> countries = FXCollections.observableArrayList();

    public FileLoader() {
        this.readCountries();
    }

    ;


    private void readCountries() {

        String line = "";
        InputStream input = getClass().getResourceAsStream("countries.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            while (null != (line = reader.readLine())) {
                countries.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {

                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String readConfigFile(String config_file_path) {

        String line = "";
        String filepath = "";
        String[] stringarray = null;
        try {
            InputStream input = new FileInputStream(config_file_path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            while (null != (line = reader.readLine())) {
                stringarray = line.split("=");
                System.out.println(stringarray.length);
                System.out.println(stringarray[0]);
                System.out.println(stringarray[1]);
                filepath = stringarray[1];
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filepath;
    }
}
