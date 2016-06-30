package main.lucene;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * The File_Filter class implements the FileFilter interface.
 * This class filters the .csv-files from the Databse of the ICIJ.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class File_Filter implements FileFilter {

    List<String> acceptfiles = Arrays.asList("Addresses.csv","all_edges.csv",
            "Entities.csv","Intermediaries.csv","Officers.csv");


    /**
     * The accept method filters the files with the acceptfiles list.
     *
     * @param file
     * @return
     * @since 1.0
     */
    public boolean accept(File file) {
        if (acceptfiles.contains(file.getName())){
            return true;
        } else {
            System.out.println(file.getAbsolutePath()+ "IS  NOT VALID FILE");
        }
        return false;
    }
}
