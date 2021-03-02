/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.bean;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Padovano
 */
public class Vente_details extends ImplEntity{
    private ObjectProperty<Livres> livre;
    private ObjectProperty<String> type;
    private ObjectProperty<Integer> qty;
    private ObjectProperty<Double>prix,discount;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<Ventes> vente;
    
    public Vente_details() {
        
    }

    public Vente_details(Map<String, Object> data) {
        super(data);
    }

    public ObjectProperty<Livres> livreProperty() {
        return livre;
    }

    public void setLivre(Livres livres) {
        this.livre.set(livres);
    }
    public ObjectProperty<String> typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
    
    public ObjectProperty<Integer> qtyProperty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public ObjectProperty<Double> prixProperty() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix.set(prix);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setCreated(LocalDate date) {
        this.date.set(date);
    }

    public Livres getLivre() {
        return livre.get();
    }

    public String getType() {
        return type.get();
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
        return new SimpleObjectProperty((qty.get()*prix.get())-getDiscount());
    }

    public ObjectProperty<Ventes> venteProperty() {
        return vente;
    }

    public void setVenteProperty(ObjectProperty<Ventes> vente) {
        this.vente = vente;
    }
    
    public Ventes getVente() {
        return vente.get();
    }

    public void setVente(Ventes vente) {
        this.vente.set(vente);
    }

    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        this.livre =new SimpleObjectProperty<>();
        this.type = new SimpleObjectProperty<>();
        this.qty = new SimpleObjectProperty<>(0);
        this.prix = new SimpleObjectProperty<>(0.0);
        this.discount = new SimpleObjectProperty<>(0.0);
        this.date = new SimpleObjectProperty<>();
        vente=new SimpleObjectProperty<>();
    }

    public ObjectProperty<Double> discountProperty() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount.set(discount);
    }

    public double getDiscount(){
        return discount.get();
    } 
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.livre.get());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vente_details other = (Vente_details) obj;
        return Objects.equals(this.livre.get(), other.livre.get());
    }
    
}
