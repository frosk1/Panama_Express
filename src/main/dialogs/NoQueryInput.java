package main.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;

public class NoQueryInput extends Alert {


    public NoQueryInput(@NamedArg("alertType") AlertType alertType) {
        super(alertType);
        this.setTitle("Warning Dialog");
        this.setHeaderText("NO QUERY INPUT");
        this.setContentText("Please insert a Query before filtering or searching!");
    }
}
