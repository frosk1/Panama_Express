package main.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;

/**
 * The NoQueryInput class extends the Alert class from the javafx framework.
 * This class opens a dialog window if no text is set in the queryfield from
 * the search_window frontend.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class NoQueryInput extends Alert {


    /**
     * Constructor method sets the Title, Header and Content.
     *
     * @param alertType
     * @since 1.0
     */
    public NoQueryInput(@NamedArg("alertType") AlertType alertType) {
        super(alertType);
        this.setTitle("Warning Dialog");
        this.setHeaderText("NO QUERY INPUT");
        this.setContentText("Please insert a Query before filtering or searching!");
    }
}
