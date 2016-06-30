package main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class NodeEntity containing StringPropertiy objects. This class is used
 * for initialize observable lists containing information about the search results
 * given by the Searcher class. The acutal Document objects from the Searcher are
 * wrapped into NodeEntity objects, to make their information observable.
 * Linked to the SearchController and InformationController classes.
 *
 * @author IMS_CREW
 * @version 1.1
 * @since 1.0
 */
public class NodeEntity {

    private StringProperty abstract_name = new SimpleStringProperty("");
    private StringProperty type = new SimpleStringProperty("");
    private StringProperty country = new SimpleStringProperty("");
    private StringProperty sourceID = new SimpleStringProperty("");
    private StringProperty jurisdiction = new SimpleStringProperty("");
    private StringProperty node_id = new SimpleStringProperty("");
    private StringProperty status = new SimpleStringProperty("");
    private StringProperty company_type = new SimpleStringProperty("");
    private StringProperty incorporation_date = new SimpleStringProperty("");
    private StringProperty inactivation_date = new SimpleStringProperty("");


    private StringProperty relation = new SimpleStringProperty("");
    private StringProperty node1 = new SimpleStringProperty("");
    private StringProperty node2 = new SimpleStringProperty("");
    private StringProperty node1_name = new SimpleStringProperty("");
    private StringProperty node2_name = new SimpleStringProperty("");


    /**
     * Constructor method for initialize of offshore_entities Document objects.
     *
     * @param abstract_name             String from the abstract_name field of a Document object
     * @param type                      String from the type field of a Document object
     * @param country                   String from the countries field of a Document object
     * @param sourceID                  String from the sourceID field of a Document object
     * @param jurisdiction              String from the jurisdiction_description field of a Document object
     * @param node_id                   String from the node_id field of a Document object
     * @param status                    String from the status field of a Document object
     * @param company_type              String from the company_type field of a Document object
     * @param incorporation_date        String from the incorporation_date field of a Document object
     * @param inactivation_date         String from the inactivation_date field of a Document object
     * @since 1.0
     */
    public NodeEntity(String abstract_name, String type, String country, String sourceID,
                      String jurisdiction, String node_id, String status, String company_type,
                      String incorporation_date, String inactivation_date) {

        this.abstract_name.setValue(abstract_name);
        this.type.setValue(type);
        this.country.setValue(country);
        this.sourceID.setValue(sourceID);
        this.jurisdiction.setValue(jurisdiction);
        this.node_id.setValue(node_id);
        this.status.setValue(status);
        this.company_type.setValue(company_type);
        this.incorporation_date.setValue(incorporation_date);
        this.inactivation_date.setValue(inactivation_date);
    }

    /**
     * Constructor class for initialize addresses, intermediary, officer Document objects.
     *
     * @param abstractname          String from the abstractname field of a Document object
     * @param type                  String from the type field of a Document object
     * @param country               String from the countries field of a Document object
     * @param sourceID              String from the sourceID field of a Document object
     * @param node_id               String from the node_id field of a Document object
     * @since 1.0
     */
    public NodeEntity(String abstractname, String type, String country, String sourceID, String node_id){
        this.abstract_name.setValue(abstractname);
        this.type.setValue(type);
        this.country.setValue(country);
        this.sourceID.setValue(sourceID);
        this.node_id.setValue(node_id);
    }

    /**
     * Constructor class for initialize relation_node Document objects.
     *
     * @param relation          String from the relation field of a Document object
     * @param node1             String from the node1 field of a Document object
     * @param node2             String from the node2 field of a Document object
     * @since 1.0
     */
    public NodeEntity(String relation, String node1, String node2){
        this.relation.setValue(relation);
        this.node1.setValue(node1);
        this.node2.setValue(node2);
    }

    /*
    The Following Methods are Getter and Setter for all the String Property objects.

     */

    public NodeEntity(){

    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getCompany_type() {
        return company_type.get();
    }

    public StringProperty company_typeProperty() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type.set(company_type);
    }

    public String getIncorporation_date() {
        return incorporation_date.get();
    }

    public StringProperty incorporation_dateProperty() {
        return incorporation_date;
    }

    public void setIncorporation_date(String incorporation_date) {
        this.incorporation_date.set(incorporation_date);
    }

    public String getInactivation_date() {
        return inactivation_date.get();
    }

    public StringProperty inactivation_dateProperty() {
        return inactivation_date;
    }

    public void setInactivation_date(String inactivation_date) {
        this.inactivation_date.set(inactivation_date);
    }

    public String getNode2_name() {
        return node2_name.get();
    }

    public String getNode1_name() {
        return node1_name.get();
    }

    public StringProperty node1_nameProperty() {
        return node1_name;
    }

    public void setNode1_name(String node1_name) {
        this.node1_name.set(node1_name);
    }

    public StringProperty node2_nameProperty() {

        return node2_name;
    }

    public void setNode2_name(String node2_name) {
        this.node2_name.set(node2_name);
    }

    public String getRelation() {
        return relation.get();
    }

    public StringProperty relationProperty() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation.set(relation);
    }

    public String getNode1() {
        return node1.get();
    }

    public StringProperty node1Property() {
        return node1;
    }

    public void setNode1(String node1) {
        this.node1.set(node1);
    }

    public String getNode2() {
        return node2.get();
    }

    public StringProperty node2Property() {
        return node2;
    }

    public void setNode2(String node2) {
        this.node2.set(node2);
    }

    public String getNode_id() {
        return node_id.get();
    }

    public StringProperty node_idProperty() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id.set(node_id);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getAbstract_name() {
        return abstract_name.get();
    }

    public StringProperty abstract_nameProperty() {
        return abstract_name;
    }

    public void setAbstract_name(String abstract_name) {
        this.abstract_name.set(abstract_name);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getSourceID() {
        return sourceID.get();
    }

    public StringProperty sourceIDProperty() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID.set(sourceID);
    }

    public String getJurisdiction() {
        return jurisdiction.get();
    }

    public StringProperty jurisdictionProperty() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction.set(jurisdiction);
    }
}
