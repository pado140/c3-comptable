/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.bean;

import com.redevance.dao.enumeration.TYPECONTRAT;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class Contrats extends ImplEntity{
    private ObjectProperty<TYPECONTRAT> contrat;
    private ObjectProperty<Livres> livre;
    private ObjectProperty<Integer> qty;
    private ObjectProperty<Double> prix,edition,auteur;
    private ObjectProperty<LocalDate> date;
    private ObservableList<Depenses> depenses;
    private ObjectProperty<String> description;
    private ObservableList<Redevance> redevance;

    public Contrats(Map<String, Object> data) {
        super(data);
    }

    public Contrats() {
    }

    public ObservableList<Redevance> getRedevance() {
        return redevance;
    }

    public void setRedevance(ObservableList<Redevance> redevance) {
        this.redevance = redevance;
    }
    
    public ObjectProperty<TYPECONTRAT> contratProperty() {
        return contrat;
    }

    public void setContratProperty(ObjectProperty<TYPECONTRAT> type_contrat) {
        this.contrat = type_contrat;
    }

    public ObjectProperty<Livres> livreProperty() {
        return livre;
    }

    public void setLivreProperty(ObjectProperty<Livres> livre) {
        this.livre = livre;
    }

    public ObjectProperty<Integer> qtyProperty() {
        return qty;
    }
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDateProperty(ObjectProperty<LocalDate> date) {
        this.date = date;
    }
    
    public void setPrix(Double prix){
        this.prix.set(prix);
    }

    public Double getPrix() {
        return prix.get();
    }
    
    public ObjectProperty<Double> prixProperty() {
        return prix;
    }
    
    public TYPECONTRAT getContrat() {
        return contrat.get();
    }

    public void setContrat(TYPECONTRAT type_contrat) {
        this.contrat.set(type_contrat);
    }
    public void setContrat(String type_contrat) {
        this.setContrat(TYPECONTRAT.valueOf(type_contrat));
    }

    public Livres getLivre() {
        return livre.get();
    }

    public void setLivre(Livres livre) {
        this.livre.set(livre);
    }

    public int getQty() {
        return qty.get();
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public void setCreated(LocalDate date) {
        this.date.set(date);
    }

    public ObservableList<Depenses> getDepenses() {
        return depenses;
    }

    public void setDepenses(ObservableList<Depenses> depenses) {
        this.depenses=depenses;
    }

    public Double getEdition() {
        return edition.get();
    }

    public ObjectProperty<Double> editionProperty() {
        return edition;
    }

    public void setEdition(double edition) {
        this.edition.set(edition);
    }

    public void setPourcentageediteur(Double edition) {
        this.edition.set(edition);
    }

    public Double getAuteur() {
        return auteur.get();
    }

    public ObjectProperty<Double> auteurProperty() {
        return auteur;
    }

    public void setAuteur(double auteur) {
        this.auteur.set(auteur);
    }
    public void setPourcentageauteur(Double auteur) {
        this.auteur.set(auteur);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void init() {
        this.contrat = new SimpleObjectProperty<>();
        this.livre = new SimpleObjectProperty<>();
        this.qty = new SimpleObjectProperty<>();
        this.date = new SimpleObjectProperty<>();
        this.prix=new SimpleObjectProperty();
        this.edition=new SimpleObjectProperty();
        this.auteur=new SimpleObjectProperty();
        depenses=FXCollections.observableArrayList();
        description=new SimpleObjectProperty<>();
        redevance=FXCollections.observableArrayList();
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
    
    public ObjectProperty<String> descriptionProperty(){
        return description;
    }
    
    public double depenseTotal(){
        double total=0;
        for(Depenses d:depenses){
            total+=d.getMontant();
        }
        return total;
    }
}
