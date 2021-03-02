package com.redevance.controller;

import static com.redevance.Main.primarystage;
import static com.redevance.Main.ventelines;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Vente_details;
import com.redevance.dao.bean.Ventes;
import com.redevance.resources.menu.vertical.InitMenu;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.services.AppServices;
import com.redevance.utils.ViewUtils;
import java.time.LocalDate;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class VenteListController extends ControllerManager{

    @FXML
    private TextField search_field;

    @FXML
    private TableView<Vente_details> ventetable;

    @FXML
    private TableColumn<Vente_details, Ventes> no;

    @FXML
    private TableColumn<Vente_details, Livres> livrecol;

    @FXML
    private TableColumn<Vente_details, Integer> qtycol;

    @FXML
    private TableColumn<Vente_details, Double> prixcol;
    
    @FXML
    private TableColumn<Vente_details, LocalDate> datecol;
    
    @FXML
    private TableColumn<Vente_details, String> typecol;

    @FXML
    private TableColumn<Vente_details, Double> totalcol;
    
    private final ObservableList<Vente_details> vente=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Vente_details>> venteList=new SimpleObjectProperty<>(vente);
    
    private String listType="load";
    

    @FXML
    void home() {
        InitMenu.Home();
    }

    @FXML
    void search() {

    }

    @FXML
    void searchtype() {
        vente.clear();
        String val=search_field.getText().trim().toLowerCase();
        vente.addAll(ventelines.parallelStream().filter(vente->vente.getVente().getCode().toLowerCase().contains(val)).collect(Collectors.toList())); 
    }
    
    @Override
    protected void initialized(){
        if(!listType.equals("load")){
            ventelines.clear();
            ventelines.addAll(AppServices.detailventeList());
        }
        if(ventelines.isEmpty())
            ventelines.addAll(AppServices.detailventeList());
        vente.clear();
        vente.addAll(ventelines);      
    }
    
    @FXML
    void refresh(ActionEvent event) {
        listType="refresh";
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }
    
    private void add_change(){
        ViewBehavior newVente=new ViewBehavior("Vente.fxml");
        Parent p=newVente.getparent();
        VenteController nbc=(VenteController)newVente.getDefaultController();
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }
    

    @FXML
    void newVente() {
        add_change();
    }

    @Override
    protected void init() {
        
        no.setCellValueFactory(data->data.getValue().venteProperty());
        livrecol.setCellValueFactory(data->data.getValue().livreProperty());
        qtycol.setCellValueFactory(data->data.getValue().qtyProperty());
        prixcol.setCellValueFactory(data->data.getValue().prixProperty());
        totalcol.setCellValueFactory(data->data.getValue().tot());
        datecol.setCellValueFactory(data->data.getValue().getVente().dateProperty());
        typecol.setCellValueFactory(data->data.getValue().getVente().typeProperty());
        
        ventetable.itemsProperty().bind(venteList);
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }


}
