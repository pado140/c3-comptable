/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.bean;

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Padovano
 */
public class Clients extends ImplEntity{
    private StringProperty nom,adresse,tel,contact,type;

    public Clients(Map<String, Object> data) {
        super(data);
    }

    public Clients() {
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public StringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public StringProperty telProperty() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    
    @Override
    public String toString() {
        return nom.get();
    }

    public String getNom() {
        return nom.get();
    }

    public String getAdresse() {
        return adresse.get();
    }

    public String getTel() {
        return tel.get();
    }

    public String getContact() {
        return contact.get();
    }

    public String getType() {
        return type.get();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void init() {
        this.nom = new SimpleStringProperty();
        this.adresse=new SimpleStringProperty();
        this.tel=new SimpleStringProperty();
        this.contact=new SimpleStringProperty();
        this.type=new SimpleStringProperty();
    }
    
}
