/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.bean;

import java.time.LocalDate;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class Ventes extends ImplEntity{
    private ObjectProperty<Clients> client;
    private ObjectProperty<Double> discount;
    private ObjectProperty<String> type,occasion,code;
    private ObjectProperty<LocalDate> date;
    private ObservableList<Vente_details> details;

    public Ventes() {
        
    }

    public Ventes(Map<String, Object> data) {
        super(data);
    }

    public ObjectProperty<Clients> clientProperty() {
        return client;
    }

    public void setClient(Clients client) {
        this.client.set(client);
    }

    public ObjectProperty<String> typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public ObjectProperty<String> occasionProperty() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion.set(occasion);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setCreated(LocalDate date) {
        this.date.set(date);
    }

    
    public Clients getClient() {
        return client.get();
    }

    public String getType() {
        return type.get();
    }

    public String getOccasion() {
        return occasion.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObservableList<Vente_details> getDetails() {
        return details;
    }

    public void setDetails(ObservableList<Vente_details> details) {
        this.details = details;
    }

    public ObjectProperty<String> getCodeProperty() {
        return code;
    }

    public void setCodeProperty(ObjectProperty<String> code) {
        this.code = code;
    }
    
    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void init() {
        this.client = new SimpleObjectProperty<>();
        this.type = new SimpleObjectProperty<>();
        this.occasion = new SimpleObjectProperty<>();
        this.code = new SimpleObjectProperty<>();
        this.date = new SimpleObjectProperty<>();
        this.discount = new SimpleObjectProperty<>(0.0);
        details=FXCollections.observableArrayList();
    }

    public ObjectProperty<Double> discountProperty() {
        return discount;
    }
    
    public double getDiscount() {
        return discount.get();
    }

    public void setDiscount(Double discount) {
        this.discount.set(discount);
    }

    @Override
    public String toString() {
        return getCode();
    }
    
}
