package resource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

/**
 * The FileLoader class provides base methods for reading the ressource
 * and config files.
 *
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class FileLoader {

    public final ObservableList<String> countries = FXCollections.observableArrayList();

    /**
     * Contructor method calls the readCountries method right away.
     *
     * @since 1.0
     */
    public FileLoader() {
        this.readCountries();
    }


    /**
     * The readCountries method read from the countries.txt and fills the
     * ObservableList with String containing country names.
     *
     * @since 1.0
     */
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

    /**
     * The readConfigFile method can be used for reading the config file.
     * With version 1.0 there is no need for reading the config File.
     *
     * @param config_file_path          String containing the config-file path
     * @return
     * @since 1.0
     */
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
