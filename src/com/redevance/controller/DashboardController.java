package com.redevance.controller;

import com.redevance.Main;
import static com.redevance.Main.livres;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Transaction;
import com.redevance.dao.bean.Users;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.services.AppServices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;

public class DashboardController extends ControllerManager {
    @FXML
    private TableView<Livres> projects;

    @FXML
    private TableColumn<Livres, String> title;

    @FXML
    private TableColumn<Livres, LocalDate> received;

    @FXML
    private TableView<Transaction> transactions;

    @FXML
    private TableColumn<Transaction, String> transac;

    @FXML
    private TableColumn<Transaction, LocalDateTime> date;

    @FXML
    private TableColumn<Transaction, Users> user;
    
    private ObservableList<Livres> livrelist=FXCollections.observableArrayList();
    private ObservableList<Transaction> transaction=FXCollections.observableArrayList();

    @FXML
    void seeall(ActionEvent event) {

    }

    @FXML
    void allprojects(ActionEvent event) {

    }


    @Override
    protected void init() {
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
        transac.setCellValueFactory(data->data.getValue().actionProperty());
        date.setCellValueFactory(data->data.getValue().createdProperty());
        user.setCellValueFactory(data->data.getValue().usersProperty());

        title.setCellValueFactory(data->data.getValue().titreProperty());
        projects.getSelectionModel().selectedItemProperty().addListener((o,a,n)->{
            //loadprojetinfo(n);
        });
    }
    
    @FXML
    void reports(ActionEvent event) {

    }
    @Override
    protected void initialized(){
        Main.livres.clear();
        livres.addAll(AppServices.bookList());
//        transaction =!getUser().getType().equalsIgnoreCase("user")?DAOFactory.createModel(DAOName.transac).selectAll():
//        DAOFactory.createModel(DAOName.transac).searchBy("users_id=?", getUser().getId());
            Platform.runLater(()->{
                       projects.itemsProperty().bind(new SimpleObjectProperty<>(livres));
                       transactions.itemsProperty().bind(new SimpleObjectProperty<>(transaction));
        });        
    }
                

}
