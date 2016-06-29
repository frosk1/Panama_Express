package main.view;


import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import main.model.NodeEntity;

import java.util.ArrayList;

public class InformationController {

    @FXML
    private TextArea textarea;

    private NodeEntity currentnode;
    private ArrayList<NodeEntity> active_connections;
    private ArrayList<NodeEntity> passive_connections;
    private String infostring;



    public InformationController(){
    }



    @FXML
    private void initialize() {
        infostring ="";


    }

    public void initNode(NodeEntity currentnode, ArrayList<NodeEntity> active_connections, ArrayList<NodeEntity> passive_connections){
        this.currentnode = currentnode;
        this.active_connections = active_connections;
        this.passive_connections = passive_connections;

        this.fillinfostring(this.currentnode);
        textarea.setText(this.infostring);

    }

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
        infostring += this.instertConnections();

    }

    private String instertConnections(){
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
