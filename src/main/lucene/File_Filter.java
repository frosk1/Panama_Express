package main.lucene;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class File_Filter implements FileFilter {

    List<String> acceptfiles = Arrays.asList("Addresses.csv","all_edges.csv",
            "Entities.csv","Intermediaries.csv","Officers.csv");


    public boolean accept(File file) {
        if (acceptfiles.contains(file.getName())){
            return true;
        } else {
            System.out.println(file.getAbsolutePath()+ "IS  NOT VALID FILE");
        }
        return false;
    }
}
