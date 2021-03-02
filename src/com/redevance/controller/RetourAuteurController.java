/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import com.jfoenix.controls.JFXTextField;
import com.redevance.Main;
import static com.redevance.Main.primarystage;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Vente_details;
import com.redevance.dao.bean.Ventes;
import com.redevance.services.AppServices;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class RetourAuteurController extends ControllerManager {
    @FXML
    private Label livre;

    @FXML
    private Label stock;

    @FXML
    private JFXTextField qty;
    
    private Livres livreSelect;

    @FXML
    void close() {
        ((Stage)stock.getScene().getWindow()).close();
    }

    @FXML
    void save() {
        int stockqty=Integer.parseInt(stock.getText().trim());
        int returnqty=Integer.parseInt(qty.getText().trim());
        if(returnqty>stockqty){
            Alert.error(Main.primarystage, "vous voulez tranferer une quantite que vous n'avez pas", null);
            return;
        }
        Ventes v=new Ventes();
        v.setType("Retour a l'auteur");
        v.setCode(String.valueOf(new Date().getTime()));
        v.setCreated(LocalDate.now());
        Vente_details vd=new Vente_details();
        vd.setLivre(livreSelect);
        vd.setPrix(0.0);
        vd.setQty(returnqty);
        ObservableList<Vente_details> details=FXCollections.observableArrayList(vd);
        v.setDetails(details);
        v=AppServices.SaveVente(v);
        if(v!=null){
            Alert.success(primarystage, "Retour effectuer avec succes", null);
            close();
        }
    }

    @Override
    protected void init() {
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {

            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {

                if (t.isReplaced()) 
                    if(t.getText().matches("[^0-9]"))
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                

                if (t.isAdded()) {
                    if (t.getControlText().contains(".")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9.]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };
        qty.setTextFormatter(new TextFormatter<>(filter));
    }

    public Livres getLivre() {
        return livreSelect;
    }

    public void setLivre(@NonNull Livres livreSelect) {
        this.livreSelect = livreSelect;
        livre.setText(livreSelect.getTitre());
        stock.setText(livreSelect.getQty()+"");
    }
    
    
}
