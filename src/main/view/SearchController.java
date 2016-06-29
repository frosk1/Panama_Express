package main.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainApp;
import main.dialogs.NoQueryInput;
import main.lucene.Searcher;
import main.model.NodeEntity;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import resource.FileLoader;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchController {

    @FXML
    private Button searchbutton;

    @FXML
    private Button offshore_entities;
    @FXML
    private Button intermediaries;
    @FXML
    private Button addresses;
    @FXML
    private Button officers;

    @FXML
    private Button moreinfo;

    @FXML
    private TextField queryfield;

    @FXML
    private TableView<NodeEntity> entitytable;

    @FXML
    private TableColumn<NodeEntity, String> abstract_column;

    @FXML
    private TableColumn<NodeEntity, String> type_column;

     @FXML
    private TableView<NodeEntity> connectiontable;

    @FXML
    private TableColumn<NodeEntity, String> node1_column;

    @FXML
    private TableColumn<NodeEntity, String> relation_column;

    @FXML
    private TableColumn<NodeEntity, String> node2_column;

    @FXML
    private TableView<NodeEntity> passiv_connectiontable;

    @FXML
    private TableColumn<NodeEntity, String> passiv_node1_column;

    @FXML
    private TableColumn<NodeEntity, String> passiv_relation_column;

    @FXML
    private TableColumn<NodeEntity, String> passiv_node2_column;


    @FXML
    private Label abstract_name_label;
    @FXML
    private Label country_label;
    @FXML
    private Label sourceID_label;
    @FXML
    private Label jurisdiction_label;
    @FXML
    private Label type_label;
    @FXML
    private Label entities_count_label;
    @FXML
    private Label officers_count_label;
    @FXML
    private Label intermediaries_count_label;
    @FXML
    private Label addresses_count_label;

    @FXML
    private Label active_connection_count;

    @FXML
    private Label passive_connection_count;

    @FXML
    private ComboBox<String> box;


    private MainApp mainApp;

    private String index_dir;

    private FileLoader fileLoader = new FileLoader();

    private HashMap<String, Integer> nodecount = new HashMap<>();

    private BorderPane infolayout;

    private NodeEntity currentnode;

    private ArrayList<NodeEntity> currentnode_active_connections = new ArrayList<>();

    private ArrayList<NodeEntity> currentnode_passive_connections = new ArrayList<>();

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SearchController() {


    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        this.initnodecount();

        box.setItems(fileLoader.countries);
        box.setValue("Filter by Country");

        searchbutton.setOnAction(this::search);
        queryfield.setOnAction(this::search);
        offshore_entities.setOnAction(this::search_offshore_entities);
        intermediaries.setOnAction(this::search_intermediaries);
        addresses.setOnAction(this::search_addresses);
        officers.setOnAction(this::search_officers);
        moreinfo.setOnAction(this::showInformationWindow);


        entitytable.setPlaceholder(new Label("No Results found!"));
        connectiontable.setPlaceholder(new Label("No Connections found!"));
        active_connection_count.setText("0");
        passiv_connectiontable.setPlaceholder(new Label("No Connections found!"));
        passive_connection_count.setText("0");

        abstract_column.setCellValueFactory(cellData -> cellData.getValue().abstract_nameProperty());
        type_column.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        node1_column.setCellValueFactory(cellData -> cellData.getValue().node1_nameProperty());
        relation_column.setCellValueFactory(cellData -> cellData.getValue().relationProperty());
        node2_column.setCellValueFactory(cellData -> cellData.getValue().node2_nameProperty());

        passiv_node1_column.setCellValueFactory(cellData -> cellData.getValue().node1_nameProperty());
        passiv_relation_column.setCellValueFactory(cellData -> cellData.getValue().relationProperty());
        passiv_node2_column.setCellValueFactory(cellData -> cellData.getValue().node2_nameProperty());


        showNodeCount();

        showNodeDetails(null);

        entitytable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) -> showNodeDetails(newvalue));

        entitytable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) -> showNodeConnections(newvalue));

        entitytable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) -> showPassivNodeConnections(newvalue));


        connectiontable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) -> showNextNode(newvalue));


        passiv_connectiontable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) -> showPassivNextNode(newvalue));

        box.setOnAction((event) -> {
            String country = box.getSelectionModel().getSelectedItem();
            filterCountries(country);

        });


    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


    }


    @FXML
    public void search(ActionEvent e) {
        Searcher searcher = new Searcher();
        box.setValue("Filter by Country");
        this.resetConnectionCount();
        if (!queryfield.getText().equals("")) {
            try {
                ArrayList<Document> results = searcher.search(index_dir, queryfield.getText());
                this.initnodecount();
                entitytable.setItems(this.fill_observable(results));
                this.showNodeCount();


            } catch (IOException | ParseException f) {
                f.printStackTrace();
            }
        }
        else {
            NoQueryInput alert = new NoQueryInput(Alert.AlertType.WARNING);
            alert.show();
        }

    }

   @FXML
    public void search_offshore_entities(ActionEvent e) {
        Searcher searcher = new Searcher();
        box.setValue("Filter by Country");
        this.resetConnectionCount();
        if (!queryfield.getText().equals("")) {
           try {
               ArrayList<Document> results = searcher.search(index_dir, queryfield.getText() + " AND type:entities");
               entitytable.setItems(this.fill_observable(results));
               this.showNodeCount();

           } catch (IOException | ParseException f) {
               f.printStackTrace();
           }
       }
        else {
           NoQueryInput alert = new NoQueryInput(Alert.AlertType.WARNING);
           alert.show();
       }

    }

    @FXML
    public void search_officers(ActionEvent e) {
        Searcher searcher = new Searcher();
        box.setValue("Filter by Country");
        this.resetConnectionCount();
        if (!queryfield.getText().equals("")) {
            try {
                ArrayList<Document> results = searcher.search(index_dir, queryfield.getText() + " AND type:officers");
                entitytable.setItems(this.fill_observable(results));
                this.showNodeCount();

            } catch (IOException | ParseException f) {
                f.printStackTrace();
            }
        }
        else {
            NoQueryInput alert = new NoQueryInput(Alert.AlertType.WARNING);
            alert.show();
        }

    }

    @FXML
    public void search_addresses(ActionEvent e) {
        Searcher searcher = new Searcher();
        box.setValue("Filter by Country");
        this.resetConnectionCount();
        if (!queryfield.getText().equals("")) {
            try {
                ArrayList<Document> results = searcher.search(index_dir, queryfield.getText() + " AND type:addresses");
                entitytable.setItems(this.fill_observable(results));
                this.showNodeCount();


            } catch (IOException | ParseException f) {
                f.printStackTrace();
            }
        }
        else {
            NoQueryInput alert = new NoQueryInput(Alert.AlertType.WARNING);
            alert.show();
        }

    }

    @FXML
    public void search_intermediaries(ActionEvent e) {
        Searcher searcher = new Searcher();
        box.setValue("Filter by Country");
        this.resetConnectionCount();
        if (!queryfield.getText().equals("")) {
            try {
                ArrayList<Document> results = searcher.search(index_dir, queryfield.getText() + " AND type:intermediaries");
                entitytable.setItems(this.fill_observable(results));
                this.showNodeCount();

            } catch (IOException | ParseException f) {
                f.printStackTrace();
            }
        }
        else {
            NoQueryInput alert = new NoQueryInput(Alert.AlertType.WARNING);
            alert.show();
        }

    }



    private ObservableList<NodeEntity> fill_observable (ArrayList<Document> docs) {
        ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
        for (Document doc: docs) {

            if (doc.getField("type").stringValue().equals("entities")) {
                oblist.add(new NodeEntity(doc.getField("name").stringValue(),
                        doc.getField("type").stringValue(),
                        doc.getField("countries").stringValue(),
                        doc.getField("sourceID").stringValue(),
                        doc.getField("jurisdiction_description").stringValue(),
                        doc.getField("node_id").stringValue(),
                        doc.getField("status").stringValue(),
                        doc.getField("company_type").stringValue(),
                        doc.getField("incorporation_date").stringValue(),
                        doc.getField("inactivation_date").stringValue())
                );
            }

            else if (doc.getField("type").stringValue().equals("relation_node")){
                oblist.add(new NodeEntity(doc.getField("relation").stringValue(),
                        doc.getField("node1").stringValue(),
                        doc.getField("node2").stringValue()));
            }

            else {
                oblist.add(new NodeEntity(doc.getField("name").stringValue(),
                        doc.getField("type").stringValue(),
                        doc.getField("countries").stringValue(),
                        doc.getField("sourceID").stringValue(),
                        doc.getField("node_id").stringValue()));

            }

            this.fillnodecount(doc);
        }

        return oblist;

    }





    private void showNodeDetails(NodeEntity node){
        if(node != null){
            currentnode = node;
            abstract_name_label.setText(node.getAbstract_name());
            country_label.setText(node.getCountry());
            sourceID_label.setText(node.getSourceID());
            type_label.setText(node.getType());
            jurisdiction_label.setText(node.getJurisdiction());

        } else {
            currentnode = null;
            abstract_name_label.setText("");
            country_label.setText("");
            sourceID_label.setText("");
            jurisdiction_label.setText("");
            type_label.setText("");
        }

    }


    private void showNextNode(NodeEntity node) {
        if (node != null) {
            Searcher searcher = new Searcher();
            try {
                ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
                ArrayList<Document> connectednode = searcher.search(index_dir, "node_id:" + node.getNode2());
                entitytable.setItems(this.fill_observable(connectednode));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private void showPassivNextNode(NodeEntity node) {
        if (node != null) {
            Searcher searcher = new Searcher();
            try {
                ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
                ArrayList<Document> connectednode = searcher.search(index_dir, "node_id:" + node.getNode1());
                entitytable.setItems(this.fill_observable(connectednode));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }
    }


    private void showNodeConnections(NodeEntity node) {
        this.resetConnectionCount();
        this.currentnode_active_connections.clear();
        Platform.runLater(new Runnable() {
            @Override public void run() {
        if(node != null){
            Searcher searcher = new Searcher();
            try {

                ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
                ArrayList<Document> relations = searcher.search(index_dir, "type:relation_node AND node1:" + node.getNode_id());
                active_connection_count.setText(String.valueOf(relations.size()));

                if (relations.size() > 0) {

                    for (Document doc : relations) {
                        ArrayList<Document> results = searcher.search(index_dir, "node_id:" + doc.getField("node2").stringValue());
                        NodeEntity newNode = new NodeEntity();
                        newNode.setRelation(doc.getField("relation").stringValue());
                        newNode.setNode1_name(node.getAbstract_name());
                        newNode.setNode2_name(results.get(0).getField("name").stringValue());
                        newNode.setNode2(doc.getField("node2").stringValue());
                        oblist.add(newNode);

                        currentnode_active_connections.add(newNode);
                    }

                    connectiontable.setItems(oblist);
                }
                else if (relations.size()==0){
                    ObservableList<NodeEntity> oblist_empty = FXCollections.observableArrayList();
                    connectiontable.setItems(oblist_empty);

                }
            }

            catch(IOException | ParseException e){
                e.printStackTrace();
            }
        }
        else    {

            ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
            connectiontable.setItems(oblist);

        }

            }});
    }

    private void showPassivNodeConnections(NodeEntity node) {
        this.resetConnectionCount();
        this.currentnode_passive_connections.clear();
        Platform.runLater(new Runnable() {
            @Override public void run() {
        if(node != null){
            Searcher searcher = new Searcher();
            try {

                ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
                ArrayList<Document> relations = searcher.search(index_dir, "type:relation_node AND node2:" + node.getNode_id());

                passive_connection_count.setText(String.valueOf(relations.size()));

                if (relations.size() > 0) {

                    for (Document doc : relations) {
                        ArrayList<Document> results = searcher.search(index_dir, "node_id:" + doc.getField("node1").stringValue());
                        NodeEntity newNode = new NodeEntity();
                        newNode.setRelation(doc.getField("relation").stringValue());
                        newNode.setNode2_name(node.getAbstract_name());
                        newNode.setNode1_name(results.get(0).getField("name").stringValue());
                        newNode.setNode1(doc.getField("node1").stringValue());
                        oblist.add(newNode);

                        currentnode_passive_connections.add(newNode);
                    }

                    passiv_connectiontable.setItems(oblist);
                }

                else if (relations.size()==0){
                    ObservableList<NodeEntity> oblist_empty = FXCollections.observableArrayList();
                    passiv_connectiontable.setItems(oblist_empty);

                }
            }

            catch(IOException | ParseException e){
                e.printStackTrace();
            }
        }
        else    {

            ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
            passiv_connectiontable.setItems(oblist);

        }

            }});
    }

    private void filterCountries(String country){

        Searcher searcher = new Searcher();
        this.resetConnectionCount();
        if (!queryfield.getText().equals("")) {
            try {
                ArrayList<Document> results = searcher.search(index_dir, queryfield.getText() + " AND countries:" + country);
                entitytable.setItems(this.fill_observable(results));
                this.showNodeCount();

            } catch (IOException | ParseException f) {
                f.printStackTrace();
            }
        }
        else {
            NoQueryInput alert = new NoQueryInput(Alert.AlertType.WARNING);
            alert.show();

//            alert.setTitle("Warning Dialog");
//            alert.setHeaderText("NO QUERY INPUT");
//            alert.setContentText("Please insert a Query before filtering!");
//            alert.showAndWait();
        }

    }

    public void initIndexDirectory(String indexdir){
        this.index_dir = indexdir;
    }

    private void resetConnectionCount(){
        this.active_connection_count.setText("0");
        this.passive_connection_count.setText("0");
    }

    private void initnodecount(){
        this.nodecount.put("entities",0);
        this.nodecount.put("officers",0);
        this.nodecount.put("intermediaries",0);
        this.nodecount.put("addresses",0);
    }

    private void showNodeCount(){
        officers_count_label.setText(this.nodecount.get("officers").toString());
        this.nodecount.put("officers",0);
        entities_count_label.setText(this.nodecount.get("entities").toString());
        this.nodecount.put("entities",0);
        intermediaries_count_label.setText(this.nodecount.get("intermediaries").toString());
        this.nodecount.put("intermediaries",0);
        addresses_count_label.setText(this.nodecount.get("addresses").toString());
        this.nodecount.put("addresses",0);
    }

    private void fillnodecount(Document doc){

         if (doc.getField("type").stringValue().equals("entities")) {
                Integer value = this.nodecount.get("entities");
                this.nodecount.put("entities", value +1);
            }
            else if (doc.getField("type").stringValue().equals("officers")) {
                Integer value = this.nodecount.get("officers");
                this.nodecount.put("officers", value +1);
            }
            else if (doc.getField("type").stringValue().equals("intermediaries")) {
                Integer value = this.nodecount.get("intermediaries");
                this.nodecount.put("intermediaries", value +1);
            }
            else if (doc.getField("type").stringValue().equals("addresses")) {
                Integer value = this.nodecount.get("addresses");
                this.nodecount.put("addresses", value +1);
            }

    }

    private void resetNodecount(){
        this.nodecount.put("officers",0);
        this.nodecount.put("entities",0);
        this.nodecount.put("intermediaries",0);
        this.nodecount.put("addresses",0);
    }


    public void showInformationWindow(ActionEvent e) {
                try {

                    Stage stage = new Stage();
                    stage.setTitle("Information Window");
                    stage.getIcons().add(new Image(FileLoader.class.getResourceAsStream("panama_express_logo_small.jpg")));

                    //Fill stage with content
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MainApp.class.getResource("view/information_window.fxml"));
                    infolayout = (BorderPane) loader.load();
                    InformationController controller = loader.getController();
                    controller.initNode(currentnode, currentnode_active_connections, currentnode_passive_connections);

                    // Show the scene containing the root layout.
                    Scene scene = new Scene(infolayout);
                    stage.setScene(scene);
                    stage.show();

                }
                catch (IOException z){
                    z.printStackTrace();
                }
            }



}
