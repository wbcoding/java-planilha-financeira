package com.wbcoding.main.Home;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TableModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty description;
    private SimpleStringProperty account;
    private SimpleStringProperty value;
    private SimpleStringProperty date;

    TableModel(int id, String description, String account, String value, String date) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.account = new SimpleStringProperty(account);
        this.value = new SimpleStringProperty(value);
        this.date = new SimpleStringProperty(date);
    }


    int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    String getAccount() {
        return account.get();
    }

    public SimpleStringProperty accountProperty() {
        return account;
    }

    public void setAccount(String account) {
        this.account.set(account);
    }

    public String getValue() {
        return value.get();
    }

    public SimpleStringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }


}
