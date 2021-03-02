package com.redevance.controller;

import com.jfoenix.controls.JFXTextField;
import static com.redevance.Main.clients;
import com.redevance.dao.bean.Clients;
import com.redevance.services.AppServices;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FiltreCustomerController extends ControllerManager{

    @FXML
    private TableView<Clients> tabclient;

    @FXML
    private TableColumn<Clients,String> nom;

    @FXML
    private TableColumn<Clients,String> type;

    @FXML
    private JFXTextField searchField;

    private Clients client;

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients clients) {
        this.client = clients;
    }
    
    private final ObservableList<Clients> liste=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Clients>> customerliste=new SimpleObjectProperty<>(liste);
    
    @FXML
    private void close(){
        ((Stage)tabclient.getScene().getWindow()).close();
    }
    
    @FXML
    private void search(){
        liste.addAll(clients.parallelStream()
                .filter(cl->cl.getNom().toLowerCase().contains(searchField.getText().trim().toLowerCase())).collect(Collectors.toList()));
    }

    @Override
    protected void init() {
        if(clients.isEmpty())
            clients.addAll(AppServices.clientList());
        nom.setCellValueFactory(cellData->cellData.getValue().nomProperty());
        type.setCellValueFactory(cellData->cellData.getValue().typeProperty());
        tabclient.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount()==2){
                setClient(tabclient.getSelectionModel().getSelectedItem());
                close();
            }
        });
        tabclient.itemsProperty().bind(customerliste);
        liste.addAll(clients);
    }
}
