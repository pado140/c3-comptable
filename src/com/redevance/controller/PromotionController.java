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
import com.redevance.dao.bean.Clients;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Vente_details;
import com.redevance.dao.bean.Ventes;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.services.AppServices;
import com.redevance.utils.ViewUtils;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class PromotionController extends ControllerManager{

     @FXML
    private Label stock;

    @FXML
    private Label livre;

    @FXML
    private TextField client;

    @FXML
    private JFXTextField qty;
    
    private Clients clientSelected;
    private Livres livreSelect;

    @FXML
    void close(ActionEvent event) {
        ((Stage)stock.getScene().getWindow()).close();
    }

    @FXML
    void save(ActionEvent event) {
        int stockqty=Integer.parseInt(stock.getText().trim());
        int returnqty=Integer.parseInt(qty.getText().trim());
        if(returnqty>stockqty){
            Alert.error(Main.primarystage, "vous voulez tranferer une quantite que vous n'avez pas", null);
            return;
        }
        if(clientSelected==null){
            Alert.error(primarystage, "veuillez selectionner un client", null);
            return;
        }
        Ventes v=new Ventes();
        v.setType("Promotion");
        v.setCode(String.valueOf(new Date().getTime()));
        v.setCreated(LocalDate.now());
        v.setClient(clientSelected);
        Vente_details vd=new Vente_details();
        vd.setLivre(livreSelect);
        vd.setPrix(0.0);
        vd.setQty(returnqty);
        ObservableList<Vente_details> details=FXCollections.observableArrayList(vd);
        v.setDetails(details);
        v=AppServices.SaveVente(v);
        if(v!=null){
            Alert.success(primarystage, "Retour effectuer avec succes", null);
            close(event);
        }
    }

    @FXML
    void selectcustomer() {
        ViewBehavior filterC=new ViewBehavior("filtreCustomer.fxml");
        Parent p=filterC.getparent();
        FiltreCustomerController nbc=(FiltreCustomerController)filterC.getDefaultController();
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        clientSelected=nbc.getClient();
        if(clientSelected!=null)
            client.setText(clientSelected.toString());
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
