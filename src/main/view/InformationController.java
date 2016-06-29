package main.view;


import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import main.model.NodeEntity;

public class InformationController {

    @FXML
    private TextArea textarea;

    private NodeEntity currentnode;
    private String infostring;



    public InformationController(){
    }



    @FXML
    private void initialize() {
        infostring =("Information Window"+"\n");


    }

    public void initNode(NodeEntity currentnode){
        this.currentnode = currentnode;
        infostring += this.currentnode.getAbstract_name()+"\n";
        infostring += this.currentnode.getType()+"\n";
        infostring += this.currentnode.getCountry()+"\n";
        infostring += this.currentnode.getJurisdiction()+"\n";
        textarea.setText(infostring);

    }

}
