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
public class VenteLivres extends ImplEntity{
    private ObjectProperty<Clients> client;
    private ObjectProperty<String> type,occasion,code;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<Livres> livres;
    private ObjectProperty<Integer> qty;
    private ObjectProperty<Double>prix;
    private ObservableList<Paiement> paiement;

    public VenteLivres(Map<String, Object> data) {
        super(data);
    }
    
    public ObjectProperty<Livres> getLivresProperty() {
        return livres;
    }

    public void setLivres(Livres livres) {
        this.livres.set(livres);
    }
    public ObjectProperty<String> getTypeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public ObjectProperty<String> getOccasionProperty() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion.set(occasion);
    }

    public ObjectProperty<Integer> getQtyProperty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty.set(qty);
    }

    public ObjectProperty<Double> getPrixProperty() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix.set(prix);
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public Livres getLivres() {
        return livres.get();
    }

    public String getType() {
        return type.get();
    }

    public String getOccasion() {
        return occasion.get();
    }

    public Integer getQty() {
        return qty.get();
    }

    public Double getPrix() {
        return prix.get();
    }

    public LocalDate getDate() {
        return date.get();
    }
    
    public ObjectProperty<Double> tot(){
        return new SimpleObjectProperty(qty.get()*prix.get());
    }
    public ObjectProperty<Clients> getClientProperty() {
        return client;
    }

    public void setClient(Clients client) {
        this.client.set(client);
    }

    public Clients getClient() {
        return client.get();
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

    public ObservableList<Paiement> getPaiement() {
        return paiement;
    }

    public void setPaiement(ObservableList<Paiement> Paiement) {
        this.paiement = Paiement;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void init() {
        this.livres =new SimpleObjectProperty<>();
        this.qty = new SimpleObjectProperty<>();
        this.prix = new SimpleObjectProperty<>();
        this.client = new SimpleObjectProperty<>();
        this.type = new SimpleObjectProperty<>();
        this.occasion = new SimpleObjectProperty<>();
        this.date = new SimpleObjectProperty<>();
        this.code = new SimpleObjectProperty<>();
        paiement=FXCollections.observableArrayList();
    }
    
    
}
