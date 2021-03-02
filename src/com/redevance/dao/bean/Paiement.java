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

/**
 *
 * @author Padovano
 */
public class Paiement extends ImplEntity{
    private ObjectProperty<Integer> qty;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<Double> montant;
    private ObjectProperty<VenteLivres> vente;

    public Paiement(Map<String, Object> data) {
        super(data);
    }

    @Override
    public void init() {
        this.qty = new SimpleObjectProperty<>();
        this.date = new SimpleObjectProperty<>();
        this.montant = new SimpleObjectProperty<>();
        this.vente = new SimpleObjectProperty<>();
    }

    public ObjectProperty<Integer> getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty.set(qty);
    }

    public ObjectProperty<LocalDate> getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<Double> getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant.set(montant);
    }

    public ObjectProperty<VenteLivres> getVente() {
        return vente;
    }

    public void setVente(VenteLivres vente) {
        this.vente.set(vente);
    }

    @Override
    public boolean isValid() {
        return true;
    }
    
    
}
