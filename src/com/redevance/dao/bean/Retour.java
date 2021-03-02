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
public class Retour extends ImplEntity{
    private ObjectProperty<VenteLivres> ventes;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<Integer> qty;

    public Retour(Map<String, Object> data) {
        super(data);
    }

    public ObjectProperty<VenteLivres> getVentesProperty() {
        return ventes;
    }

    public void setVentes(VenteLivres ventes) {
        this.ventes.set(ventes);
    }

    public ObjectProperty<LocalDate> getDateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<Integer> getQtyProperty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty.set(qty);
    }

    public VenteLivres getVentes() {
        return ventes.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public Integer getQty() {
        return qty.get();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void init() {
        this.ventes = new SimpleObjectProperty<>();
        this.date = new SimpleObjectProperty<>();
        this.qty = new SimpleObjectProperty<>();
    }
    
}
