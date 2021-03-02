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
public class Redevance extends ImplEntity{
    private ObjectProperty<String> check;
    private ObjectProperty<Contrats> contrat;
    private ObjectProperty<Double> montant;
    private ObjectProperty<LocalDate> date;

    public Redevance(Map<String, Object> data) {
        super(data);
    }

    public Redevance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ObjectProperty<String> checkProperty() {
        return check;
    }

    public String getCheckno() {
        return check.get();
    }

    public void setCheckno(String check) {
        this.check.set(check);
    }

    public ObjectProperty<Contrats> contratProperty() {
        return contrat;
    }

    public Contrats getContrat() {
        return contrat.get();
    }

    public void setContrat(Contrats contrat) {
        this.contrat.set(contrat);
    }

    public ObjectProperty<Double> montantProperty() {
        return montant;
    }

    public Double getMontant() {
        return montant.get();
    }

    public void setMontant(Double montant) {
        this.montant.set(montant);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public LocalDate getDateverser() {
        return date.get();
    }

    public void setDateverser(LocalDate date) {
        this.date.set(date);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void init() {
        this.check = new SimpleObjectProperty<>();
        this.contrat = new SimpleObjectProperty<>();
        this.montant = new SimpleObjectProperty<>();
        this.date = new SimpleObjectProperty<>();
    }
    
    
    
}
