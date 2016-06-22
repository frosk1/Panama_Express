package main.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import main.MainApp;
import main.lucene.Searcher;
import main.model.NodeEntity;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchController {

    @FXML
    private Button searchbutton;

    @FXML
    private Button chartbutton;

    @FXML
    private Button offshore_entities;
    @FXML
    private Button intermediaries;
    @FXML
    private Button addresses;
    @FXML
    private Button officers;

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
    private Label abstract_name_label;
    @FXML
    private Label country_label;
    @FXML
    private Label country_codes_labels;
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


    private MainApp mainApp;

    private HashMap<String, Integer> nodecount = new HashMap<>();

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
        searchbutton.setOnAction(this::search);
        queryfield.setOnAction(this::search);
        offshore_entities.setOnAction(this::search_offshore_entities);
        intermediaries.setOnAction(this::search_intermediaries);
        addresses.setOnAction(this::search_addresses);
        officers.setOnAction(this::search_officers);

        chartbutton.setOnAction(this::chart);

        entitytable.setPlaceholder(new Label("No Results found!"));

        abstract_column.setCellValueFactory(cellData -> cellData.getValue().abstract_nameProperty());
        type_column.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        node1_column.setCellValueFactory(cellData -> cellData.getValue().node1Property());
        relation_column.setCellValueFactory(cellData -> cellData.getValue().relationProperty());
        node2_column.setCellValueFactory(cellData -> cellData.getValue().node2Property());

        showNodeCount();

        showNodeDetails(null);
        entitytable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) -> showNodeDetails(newvalue));

        entitytable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldvalue, newvalue) -> showNodeConnections(newvalue));

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
        String index_dir = "/home/jan/Development/panama_search/index_files";
        Searcher searcher = new Searcher();
        try {
            ArrayList<Document> results = searcher.search(index_dir,queryfield.getText());
            entitytable.setItems(this.fill_observable(results));
            this.showNodeCount();

        }
        catch (IOException | ParseException f){
            f.printStackTrace();
        }

    }

   @FXML
    public void search_offshore_entities(ActionEvent e) {
        String index_dir = "/home/jan/Development/panama_search/index_files";
        Searcher searcher = new Searcher();
        try {
            ArrayList<Document> results = searcher.search(index_dir, queryfield.getText()+ " AND type:entities");
            entitytable.setItems(this.fill_observable(results));

        }
        catch (IOException | ParseException f){
            f.printStackTrace();
        }

    }

    @FXML
    public void search_officers(ActionEvent e) {
        String index_dir = "/home/jan/Development/panama_search/index_files";
        Searcher searcher = new Searcher();
        try {
            ArrayList<Document> results = searcher.search(index_dir, queryfield.getText()+ " AND type:officers");
            entitytable.setItems(this.fill_observable(results));

        }
        catch (IOException | ParseException f){
            f.printStackTrace();
        }

    }

    @FXML
    public void search_addresses(ActionEvent e) {
        String index_dir = "/home/jan/Development/panama_search/index_files";
        Searcher searcher = new Searcher();
        try {
            ArrayList<Document> results = searcher.search(index_dir, queryfield.getText()+ " AND type:addresses");
            entitytable.setItems(this.fill_observable(results));
        }
        catch (IOException | ParseException f){
            f.printStackTrace();
        }

    }
@FXML
    public void search_intermediaries(ActionEvent e) {
        String index_dir = "/home/jan/Development/panama_search/index_files";
        Searcher searcher = new Searcher();
        try {
            ArrayList<Document> results = searcher.search(index_dir, queryfield.getText()+ " AND type:intermediaries");
            entitytable.setItems(this.fill_observable(results));

        }
        catch (IOException | ParseException f){
            f.printStackTrace();
        }

    }


    @FXML
    public void chart(ActionEvent e) {
        try {
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Grapefruit", 13),
                            new PieChart.Data("Oranges", 25),
                            new PieChart.Data("Plums", 10),
                            new PieChart.Data("Pears", 22),
                            new PieChart.Data("Apples", 30));
            final PieChart chart = new PieChart(pieChartData);
            chart.setTitle("Imported Fruits");

            MainApp.showChartWindow(chart);
        }
        catch (Exception f){
            f.printStackTrace();
        }
    }

    private ObservableList<NodeEntity> fill_observable (ArrayList<Document> docs) {
        ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
        for (Document doc: docs) {

            if (doc.getField("type").stringValue().equals("entities")) {
                oblist.add(new NodeEntity(doc.getField("name").stringValue(),
                        doc.getField("type").stringValue(),
                        doc.getField("countries").stringValue(),
                        doc.getField("country_codes").stringValue(),
                        doc.getField("jurisdiction").stringValue(),
                        doc.getField("node_id").stringValue()));
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
                        doc.getField("country_codes").stringValue(),
                        doc.getField("node_id").stringValue()));

            }

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
        return oblist;

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


    private void showNodeDetails(NodeEntity node){
        if(node != null){

            abstract_name_label.setText(node.getAbstract_name());
            country_label.setText(node.getCountry());
            country_codes_labels.setText(node.getCountry_codes());
            type_label.setText(node.getType());
            jurisdiction_label.setText(node.getJurisdiction());

        } else {
            abstract_name_label.setText("");
            country_label.setText("");
            country_codes_labels.setText("");
            jurisdiction_label.setText("");
            type_label.setText("");
        }

    }

    private void showNodeConnections(NodeEntity node) {
        if(node != null){
            String index_dir = "/home/jan/Development/panama_search/index_files";
            Searcher searcher = new Searcher();
            try {

                ObservableList<NodeEntity> oblist = FXCollections.observableArrayList();
                ArrayList<Document> relations = searcher.search(index_dir, "type:relation_node AND node1:" + node.getNode_id());
                String node1_search_pattern = "node_id:";
                System.out.println(relations.size());
                if (relations.size() > 0) {
//                    int count = 1;
                    for (Document doc : relations) {
                        ArrayList<Document> results = searcher.search(index_dir, "node_id:" + doc.getField("node2").stringValue());
                        NodeEntity newNode = new NodeEntity(doc.getField("relation").stringValue(), node.getAbstract_name(),
                                results.get(0).getField("name").stringValue());
                        oblist.add(newNode);
                    }

//                        if ( count==relations.size()) {
//                            node1_search_pattern += doc.getField("node2").stringValue();
//                        } else  {
//                            node1_search_pattern += doc.getField("node2").stringValue()+ " OR node_id:";
//                        }
//                        count += 1;
//
//                    }
//                }
//                System.out.println(node1_search_pattern);

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
    }

    private void initnodecount(){
        this.nodecount.put("entities",0);
        this.nodecount.put("officers",0);
        this.nodecount.put("intermediaries",0);
        this.nodecount.put("addresses",0);
    }



}
