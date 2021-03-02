/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import com.jfoenix.controls.JFXTextField;
import static com.redevance.Main.livres;
import com.redevance.dao.bean.Livres;
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

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class FiltreLivreController extends ControllerManager {

    private Livres livre;

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livres) {
        this.livre = livres;
    }
    
    @FXML
    private TableView<Livres> tablivre;
    
    @FXML
    private TableColumn<Livres,String> titre,isbn;
    
    
    @FXML
    private JFXTextField searchField;
    
    private final ObservableList<Livres> liste=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Livres>> livresliste=new SimpleObjectProperty<>(liste);
    
    @FXML
    private void close(){
        ((Stage)tablivre.getScene().getWindow()).close();
    }
    
    @FXML
    private void search(){
        liste.addAll(livres.parallelStream()
                .filter(l->l.getTitre().toLowerCase().contains(searchField.getText().trim().toLowerCase())).collect(Collectors.toList()));
    }

    @Override
    protected void init() {
        titre.setCellValueFactory(cellData->cellData.getValue().titreProperty());
        isbn.setCellValueFactory(cellData->cellData.getValue().isbnProperty());
        tablivre.setOnMouseClicked((MouseEvent event) -> {
            if(event.getClickCount()==2){
                setLivre(tablivre.getSelectionModel().getSelectedItem());
                close();
            }
        });
        tablivre.itemsProperty().bind(livresliste);
        liste.addAll(livres);
    }
}
