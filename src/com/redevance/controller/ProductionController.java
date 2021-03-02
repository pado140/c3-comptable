/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import com.redevance.dao.bean.Clients;
import com.redevance.dao.bean.Contrats;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Redevance;
import com.redevance.dao.bean.Vente_details;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.dao.enumeration.TYPECONTRAT;
import com.redevance.services.AppServices;
import java.time.LocalDate;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class ProductionController extends ControllerManager {
    
    private Livres livreSelect;
    private Contrats contrat;
    private final ObservableList<Vente_details> detailsvente=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Vente_details>> details=new SimpleObjectProperty<>(detailsvente);
    private final ObservableList<Redevance> redevancelist=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Redevance>> redevancedetails=new SimpleObjectProperty<>(redevancelist);
    @FXML
    private Label livre;

    @FXML
    private TabPane tabpane;

    @FXML
    private TableView<Vente_details> tab_rep;

    @FXML
    private TableColumn<Vente_details, LocalDate> date;

    @FXML
    private TableColumn<Vente_details, Clients> Cl;

    @FXML
    private TableColumn<Vente_details, Integer> Qt;

    @FXML
    private TableColumn<Vente_details, Double> Px;

    @FXML
    private TableColumn<Vente_details, Double> Vt;

    @FXML
    private TableColumn<Vente_details, Double> Va;

    @FXML
    private TableColumn<Vente_details, Double> Vrt;

    @FXML
    private Label qtytot;

    @FXML
    private Label ventetot;

    @FXML
    private TableView<Redevance> redevance;

    @FXML
    private TableColumn<Redevance, LocalDate> Date1;

    @FXML
    private TableColumn<Redevance, String> checkno;

    @FXML
    private TableColumn<Redevance, Double> montant;

    @FXML
    private Label contrat_type;

    @FXML
    private Label contrat_redevance;

    @FXML
    private Label depenses;

    @FXML
    private Label due;

    @FXML
    void close(ActionEvent event) {
        ((Stage)due.getScene().getWindow()).close();
    }

    @Override
    protected void init() {
        date.setCellValueFactory(data->data.getValue().getVente().dateProperty());
        Cl.setCellValueFactory(data->data.getValue().getVente().clientProperty());
        Qt.setCellValueFactory(data->data.getValue().qtyProperty());
        tab_rep.itemsProperty().bind(details);
        Date1.setCellValueFactory(data->data.getValue().dateProperty());
        montant.setCellValueFactory(data->data.getValue().montantProperty());
        checkno.setCellValueFactory(data->data.getValue().checkProperty());
        redevance.itemsProperty().bind(redevancedetails);
        tabpane.getTabs().get(1).setDisable(true);
    }
    
    public Livres getLivre() {
        return livreSelect;
    }

    public void setLivre(@NonNull Livres livreSelect) {
        this.livreSelect = livreSelect;
        livre.setText(livreSelect.getTitre());
        contrat=this.livreSelect.getContrat();
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
        if(contrat!=null){    
        contrat_type.setText(contrat.getContrat().toString());
        contrat_redevance.setText(contrat.getAuteur()+"");
        depenses.setText(contrat.depenseTotal()+"");
        tabpane.getTabs().get(1).setDisable(false);
        
        }
    }
    
    @Override
    protected void initialized(){
        detailsvente.clear();
        redevancelist.clear();
        detailsvente.addAll(AppServices.detailventebybook(livreSelect));
        if(contrat!=null){
            contrat=(Contrats)DAOFactory.createModel(DAOName.contrat).select("id=?", true, contrat.getId());
            redevancelist.addAll(DAOFactory.createModel(DAOName.redevance).search("contrat_id=?",false,contrat.getId()));
            due.setText(calculredevanceaverser(contrat)+"");
        }
        
    }
    
    public double calculredevanceaverser(Contrats contrat){
        double ventetotal=0,benefice=0,redevancetotal=0,total=0;
        for(Vente_details vd:detailsvente){
            System.out.println("prix:"+vd.getPrix());
            System.out.println("qty:"+vd.getQty());
            ventetotal+=(vd.getQty()*vd.getPrix())-vd.getDiscount();
        }
        for(Redevance rd:redevancelist){
            redevancetotal+=rd.getMontant();
        }
        if(contrat.getContrat().equals(TYPECONTRAT.E)){
            ventetotal=ventetotal/1.1;
        }
        System.out.println("ventetotal:"+ventetotal);
        benefice=ventetotal-contrat.depenseTotal();
         total=benefice-redevancetotal;
        return total*contrat.getAuteur()/100;
    }
    
    @FXML
    void verse_redevance() {
        Redevance red = new Redevance();
        red.setCheckno("");
        red.setContrat(contrat);
        red.setDateverser(LocalDate.MAX);
    }
    
}
