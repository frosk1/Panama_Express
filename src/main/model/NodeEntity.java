package main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class NodeEntity {

    private StringProperty abstract_name = new SimpleStringProperty("");
    private StringProperty type = new SimpleStringProperty("");
    private StringProperty country = new SimpleStringProperty("");
    private StringProperty country_codes = new SimpleStringProperty("");
    private StringProperty jurisdiction = new SimpleStringProperty("");
    private StringProperty node_id = new SimpleStringProperty("");
    private StringProperty relation = new SimpleStringProperty("");
    private StringProperty node1 = new SimpleStringProperty("");
    private StringProperty node2 = new SimpleStringProperty("");
    private StringProperty node1_name = new SimpleStringProperty("");
    private StringProperty node2_name = new SimpleStringProperty("");



    public NodeEntity(String abstract_name, String type, String country, String country_codes, String jurisdiction, String node_id) {
        this.abstract_name.setValue(abstract_name);
        this.type.setValue(type);
        this.country.setValue(country);
        this.country_codes.setValue(country_codes);
        this.jurisdiction.setValue(jurisdiction);
        this.node_id.setValue(node_id);
    }

    public NodeEntity(String abstractname, String type, String country, String country_codes, String node_id){
        this.abstract_name.setValue(abstractname);
        this.type.setValue(type);
        this.country.setValue(country);
        this.country_codes.setValue(country_codes);
        this.node_id.setValue(node_id);
    }

    public NodeEntity(String relation, String node1, String node2){
        this.relation.setValue(relation);
        this.node1.setValue(node1);
        this.node2.setValue(node2);
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

    public NodeEntity(){


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

    public String getCountry_codes() {
        return country_codes.get();
    }

    public StringProperty country_codesProperty() {
        return country_codes;
    }

    public void setCountry_codes(String country_codes) {
        this.country_codes.set(country_codes);
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
