package main.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import main.dialogs.NoSelectionInput;
import main.lucene.Searcher;
import main.model.NodeEntity;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import resource.FileLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Controller class for the search-window frontend provided by the search_window.fxml.
 * This class is a interface for the model class NodeEntity and the view search_window.
 * All the methods implemented in this class are used to link the search_window view with
 * the model NodeEntity. The backend classes Indexer and Searcher are placed in this class.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
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
     * The default constructor.
     * This constructor method is called before the initialize() method.
     * @since 1.0
     */
    public SearchController() {

    }

    /**
     * This method initializes the controller class and is automatically called right
     * after the search_window.fxml file has been loaded.
     *
     * The Eventhandler methods are linked to the corresponding frontend container. Furthermore
     * specific Changelistener are also set to the corresponding frontend containers. The Label
     * container are getting init values.
     *
     * @since 1.0
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
     * Can be seen as interface betweetn the MainApp class and this controller.
     *
     * @param mainApp
     * @since 1.0
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    /**
     * The search method for the acutal search-button container in the frontend.
     * Initalize a Searcher object and search  for the given String (by the queryfield)
     * through the index files using lucene IR-system. The default indexed field is
     * the 'name'-field from the indexed documents. It uses the full functionality of
     * the Queryparser from the Searcher Class.
     *
     * @param e     ActionEvent triggerd by clicking the search-button.
     * @since 1.0
     */
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

    /**
     * The search method for the offshore_entities-button container in the frontend.
     * Initalize a Searcher object and do a Boolean Search with the given String(by the queryfield)
     * and the AND type:entities constraint.The default indexed field is the 'name'-field
     * from the indexed documents. This method is only used for filtering a given Query, given by
     * the String from the queryfield.
     *
     * @param e     ActionEvent triggerd by clicking the offshore_entities-button.
     * @since 1.0
     */
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


    /**
     *  The search method for the officers-button container in the frontend.
     * Initalize a Searcher object and do a Boolean Search with the given String(by the queryfield)
     * and the AND type:officers constraint.The default indexed field is the 'name'-field
     * from the indexed documents. This method is only used for filtering a given Query, given by
     * the String from the queryfield.
     *
     * @param e     ActionEvent triggerd by clicking the officers-button.
     * @since 1.0
     */
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


    /**
     * The search method for the addresses-button container in the frontend.
     * Initalize a Searcher object and do a Boolean Search with the given String(by the queryfield)
     * and the AND type:addresses constraint.The default indexed field is the 'name'-field
     * from the indexed documents. This method is only used for filtering a given Query, given by
     * the String from the queryfield.
     *
     * @param e     ActionEvent triggerd by clicking the addresses-button.
     * @since 1.0
     */
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

    /**
     * The search method for the intermediares-button container in the frontend.
     * Initalize a Searcher object and do a Boolean Search with the given String(by the queryfield)
     * and the AND type:intermediaries constraint.The default indexed field is the 'name'-field
     * from the indexed documents. This method is only used for filtering a given Query, given by
     * the String from the queryfield.
     *
     * @param e     ActionEvent triggerd by clicking the intermediaries-button.
     * @since 1.0
     */
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


    /**
     * The fill_observable method is used for initialize NodeEntity model objects.
     * The return of the search method from the Searcher class gives back a list
     * of ranked Document objects found in the indexed files. The method wraps the
     * documents into NodeEntity objects.
     *
     * @param docs     ArrayList containing resulting Document objects from the search
     * @return oblist  ObservableArrayList containing NodeEntity objects
     * @since 1.0
     */
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


    /**
     * The showNodeDetails method fills the corresponding label container in the frontend.
     * If there is no node selected, the labels are set to null. Used by the entitytable
     * container.
     *
     * @param node      The selected NodeEntity from the entitytable container.
     * @since 1.0
     */
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


    /**
     * The method showNextNode takes the given NodeEntity from the connectiontable
     * and fills a new EntityNode by searching for the node_id field in the indexed
     * files. The new EnitityNode then gets the every information, which is available.
     *
     * (The stored NodeEntity objects from the connectiontable only consists of abstractname,
     * relation and node_ids.)
     *
     * @param node      selected NodeEntity object from the connectiontable container.
     * @since 1.0
     */
    private void showNextNode(NodeEntity node) {
        if (node != null) {
            Searcher searcher = new Searcher();
            try {
                ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
                ArrayList<Document> connectednode = searcher.search(index_dir, "node_id:" + node.getNode2());
                entitytable.setItems(this.fill_observable(connectednode));
                this.showNodeCount();

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * The method showPassivNextNode takes the given NodeEntity from the passive_connectiontable
     * and fills a new EntityNode by searching for the node_id field in the indexed
     * files. The new EnitityNode then gets the every information, which is available.
     *
     * (The stored NodeEntity objects from the passive_connectiontable only consists of abstractname,
     * relation and node_ids.)
     *
     * @param node      selected NodeEntity object from the passive_connectiontable container.
     * @since 1.0
     */
    private void showPassivNextNode(NodeEntity node) {
        if (node != null) {
            Searcher searcher = new Searcher();
            try {
                ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
                ArrayList<Document> connectednode = searcher.search(index_dir, "node_id:" + node.getNode1());
                entitytable.setItems(this.fill_observable(connectednode));
                this.showNodeCount();

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * The showNodeConnections method init a new Search object for searching active Connections
     * of a given Node from the entitytable. Doing a boolean Search for the acutal relation_node
     * field, gives back Document objects with the the type relation_node, the active node1_id and
     * the passive node2_id. After finding all active connection node_id's, it will be search for
     * the information, which is available for the found active connection node_id's.
     *
     * (This method needs to run under another Thread, due to certain Exceptions thrown by the
     * JavaFX Framework.)
     *
     * @param node      selected NodeEntity object from the entitytable
     * @since 1.0
     */
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

    /**
     * The showPassivNodeConnections method init a new Search object for searching passive Connections
     * of a given Node from the entitytable. Doing a boolean Search for the acutal relation_node
     * field, gives back Document objects with the the type relation_node, the active node1_id and
     * the passive node2_id. After finding all passive connection node_id's, it will be search for
     * the information, which is available for the found passive connection node_id's.
     *
     * (This method needs to run under another Thread, due to certain Exceptions thrown by the
     * JavaFX Framework.)
     *
     * @param node      selected NodeEntity object from the entitytable
     * @since 1.0
     */
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

    /**
     * The filterCountries method initialize a new Searcher object for using a Boolean Search
     * with the given country entry from the box(combobox container containing country names).
     * This will filter the origin query given by the queryfield by the countries field indexed
     * the files with adding an AND countries: to the query.
     *
     * @param country       selected String from the combobox for the countries
     * @since 1.0
     */
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
        }

    }

    /**
     * The initIndexDirectory method is an interface for the MainApp class. to link
     * the index directory for the Searcher class given by the config folder.
     *
     * @param indexdir      String containing the path of the index directory.
     * @since 1.0
     */
    public void initIndexDirectory(String indexdir){
        this.index_dir = indexdir;
    }

    /**
     * The resetConnectionCount method sets the active and passice conntecion labels
     * to zero.
     *
     * @since 1.0
     */
    private void resetConnectionCount(){
        this.active_connection_count.setText("0");
        this.passive_connection_count.setText("0");
    }


    /**
     * The method initnodecount initializes the nodecount map.
     *
     * @since 1.0
     */
    private void initnodecount(){
        this.nodecount.put("entities",0);
        this.nodecount.put("officers",0);
        this.nodecount.put("intermediaries",0);
        this.nodecount.put("addresses",0);
    }

    /**
     * The showNodeCount method set the labels of the different counter nodes.
     * After every count ist set, the nodecount map is reset to zero.
     *
     * @since 1.0
     */
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

    /**
     * The fillnodecount method takes a Document object and counts the
     * different type of these document objects in the nodecount map.
     *
     * @param doc       Document Object object from the search results.
     * @since 1.0
     */
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


    /**
     * The resetNodecount method resets the counter in the nodecount map.
     *
     * (This method has become obsolete in version 1.1)
     * @since 1.0
     */
    private void resetNodecount(){
        this.nodecount.put("officers",0);
        this.nodecount.put("entities",0);
        this.nodecount.put("intermediaries",0);
        this.nodecount.put("addresses",0);
    }


    /**
     * The Method showInformationWindow is an Eventhandler for the moreinfo button container
     * in the frontend. A new Stage called Information Window will be opened. The corresponding
     * Frontend is the information_window.fxml, controlled by the Controller class InformationController.
     *
     * @param e     ActionEvent triggerd by klicking the moreinf button container
     */
    public void showInformationWindow(ActionEvent e) {
        if(!queryfield.getText().equals("") && currentnode != null) {
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

            } catch (IOException z) {
                z.printStackTrace();
            }
        }
        else {
            NoSelectionInput alert = new NoSelectionInput(Alert.AlertType.WARNING);
            alert.show();
        }
    }




}
