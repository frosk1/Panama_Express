package main.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.model.NodeEntity;
import java.util.ArrayList;


/**
 * Controller class for the information-window frontend provided by the information_window.fxml.
 * All the methods implemented in this class are used to link the information_window view with
 * the model NodeEntity given by the selection from the entitytable from the search_window.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class InformationController {

    @FXML
    private TextArea textarea;

    private NodeEntity currentnode;
    private ArrayList<NodeEntity> active_connections;
    private ArrayList<NodeEntity> passive_connections;
    private String infostring;


    /**
     * The default constructor.
     * This constructor method is called before the initialize() method.
     *
     * @since 1.0
     */
    public InformationController(){
    }


    /**
     * This method initializes the controller class and is automatically called right
     * after the information_window.fxml file has been loaded.
     *
     * @since 1.0
     */
    @FXML
    private void initialize() {
        infostring ="";
    }

    /**
     * The initNode method initializes the current NodeEntity and the active, passive NodeEntity lists.
     * The currentnode and active,passie NodeEntity lists are selected by the entitytable from the search_window
     * Uses the fillinfostring method to set the textarea in the information_window.
     *
     * @param currentnode               selected NodeEntity from the entitytable in the search_window
     * @param active_connections        NodeEntity list containing all active Connecetion Nodes from the currentnode
     * @param passive_connections       NodeEntity list containing all passive Connecetion Nodes from the currentnode
     * @since 1.0
     */
    public void initNode(NodeEntity currentnode, ArrayList<NodeEntity> active_connections, ArrayList<NodeEntity> passive_connections){
        this.currentnode = currentnode;
        this.active_connections = active_connections;
        this.passive_connections = passive_connections;

        this.fillinfostring(this.currentnode);
        textarea.setText(this.infostring);

    }

    /**
     * The method fillinfostring takes the currentnode and the active,passive connection NodeEntity objects
     * and sets the infostring with the available information.
     *
     * @param currentnode               selected NodeEntity from the entitytable in the search_window
     * @since 1.0
     */
    private void fillinfostring(NodeEntity currentnode){

        infostring += "Abstract name: "+this.currentnode.getAbstract_name()+"\n";
        infostring += "\n";
        infostring += "Type: "+this.currentnode.getType()+"\n";
        infostring += "Source: "+this.currentnode.getSourceID()+"\n";
        infostring += "Country: "+this.currentnode.getCountry()+"\n";
        infostring += "Jurisdiction: "+this.currentnode.getJurisdiction()+"\n";
        infostring += "Company type: "+this.currentnode.getCompany_type()+"\n";
        infostring += "Status: "+this.currentnode.getStatus()+"\n";
        infostring += "Incorporation date: "+this.currentnode.getIncorporation_date()+"\n";
        infostring += "Inactivation date: "+this.currentnode.getInactivation_date()+"\n";
        infostring += "\n";
        infostring += "\n";
        infostring += this.insertConnections();

    }

    /**
     * The method insertConnections walks through the active and passive connection list
     * of the currentnode and filters the available information from the relation NodeEntity
     * objects.
     *
     * @return      String containing information about the active and passive connections
     * @since 1.0
     */
    private String insertConnections(){
        String active_connections = "Active Connections:"+"\n";
        String passive_connections = "\n"+"Passive Connections:"+"\n";
        for (NodeEntity node: this.active_connections) {
            active_connections += node.getRelation()+"  " +node.getNode2_name()+"\n";
        }

        for (NodeEntity node: this.passive_connections) {
            passive_connections += node.getNode1_name() + "  " + node.getRelation()+"\n";
        }

        return active_connections+passive_connections;
    }

}
