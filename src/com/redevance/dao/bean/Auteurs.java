package com.redevance.dao.bean;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Map;

public class Auteurs extends ImplEntity {
    private ObjectProperty<String> nom,prenom,telephone,email;
    
    public Auteurs(Map<String, Object> data) {
        super(data);
    }

    @Override
    public void init() {
        nom=new SimpleObjectProperty<>();
        prenom=new SimpleObjectProperty<>();
        telephone=new SimpleObjectProperty<>();
        email=new SimpleObjectProperty<>();
    }

    public Auteurs() {
        super();
    }

    public Auteurs(ObjectProperty<Integer> id) {
        super(id);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public ObjectProperty<String> telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String notice) {
        this.telephone.set(notice);
    }

    public String getNom() {
        return nom.get();
    }

    public ObjectProperty<String> nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public ObjectProperty<String> prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getEmail() {
        return email.get();
    }

    public ObjectProperty<String> emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    
    @Override
    public String toString() {
        return getNom().concat(" ").concat(getPrenom());
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
