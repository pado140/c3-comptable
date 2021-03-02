/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.bean;

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class Categories extends ImplEntity{
    private StringProperty categorie;
    private ObservableList<Livres>livres;

    public Categories(Map<String, Object> data) {
        super(data);
    }

    public Categories() {
    }

    public String getCategorie() {
        return categorie.get();
    }

    public StringProperty categorieProperty() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie.set(categorie);
    }
    
    public ObservableList<Livres> getLivres() {
        return livres;
    }

    public void setLivres(ObservableList<Livres> livres) {
        this.livres = livres;
    }

    @Override
    public String toString() {
        return categorie.get();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void init() {
        this.categorie = new SimpleStringProperty();
        this.livres = FXCollections.observableArrayList();
    }
    
    
}
