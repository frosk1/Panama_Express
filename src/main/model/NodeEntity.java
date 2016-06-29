package main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    public NodeEntity(String abstractname, String type, String country, String sourceID, String node_id){
        this.abstract_name.setValue(abstractname);
        this.type.setValue(type);
        this.country.setValue(country);
        this.sourceID.setValue(sourceID);
        this.node_id.setValue(node_id);
    }

    public NodeEntity(String relation, String node1, String node2){
        this.relation.setValue(relation);
        this.node1.setValue(node1);
        this.node2.setValue(node2);
    }

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
