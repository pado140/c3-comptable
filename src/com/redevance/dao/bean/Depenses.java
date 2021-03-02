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
public class Depenses extends ImplEntity{
    private ObjectProperty<Contrats> contrat;
    private ObjectProperty<String> description;
    private ObjectProperty<Double> montant;
    private ObjectProperty<LocalDate> created;

    public Depenses(Map<String, Object> data) {
        super(data);
    }

    public Contrats getContrat() {
        return contrat.get();
    }

    public String getDescription() {
        return description.get();
    }

    public double getMontant() {
        return montant.get();
    }

    public Depenses() {
    }

    public ObjectProperty<Contrats> contratProperty() {
        return contrat;
    }

    public void setContrat(Contrats contrat) {
        this.contrat.set(contrat);
    }

    public ObjectProperty<String> descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public ObjectProperty<Double> montantProperty() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant.set(montant);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public ObjectProperty<LocalDate> createdProperty() {
        return created;
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }
    
    

    @Override
    public void init() {
        this.contrat = new SimpleObjectProperty();
        this.description = new SimpleObjectProperty();
        this.montant = new SimpleObjectProperty();
        created=new SimpleObjectProperty();
    }
}
