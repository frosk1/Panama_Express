package main.dialogs;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;

/**
 * The NoSelectionInput class extends the Alert class from the javafx framework.
 * This class opens a dialog window if no text is set in the queryfield from
 * the search_window frontend and no Selection is made from the entitytable.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.1
 */
public class NoSelectionInput extends  Alert{

    /**
     * Constructor method sets the Title, Header and Content.
     *
     * @param alertType
     * @since 1.1
     */
    public NoSelectionInput(@NamedArg("alertType") Alert.AlertType alertType) {
        super(alertType);
        this.setTitle("Warning Dialog");
        this.setHeaderText("NO QUERY INPUT AND NO SELECTION");
        this.setContentText("Please insert a Query before and select an item before clicking more Information");
    }
}
