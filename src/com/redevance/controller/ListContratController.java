/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import static com.redevance.Main.primarystage;
import static com.redevance.Main.rbs;
import com.redevance.alert.Alert;
import com.redevance.alert.AlertButtons;
import com.redevance.dao.bean.Contrats;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.dao.enumeration.TYPECONTRAT;
import com.redevance.resources.menu.vertical.InitMenu;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.utils.Boutton;
import com.redevance.utils.ViewUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ListContratController extends ControllerManager{

    @FXML
    private TableView<Contrats> table_contrats;

    @FXML
    private TableColumn<Contrats, Livres> nom;

    @FXML
    private TableColumn<Contrats, TYPECONTRAT> contrat;

    @FXML
    private TableColumn<Contrats, String> desc;

    @FXML
    private TableColumn<Contrats, Integer> qty;

    @FXML
    private TableColumn<Contrats , Double> mont;

    @FXML
    private TableColumn<Contrats, Double> pauteur;

    @FXML
    private TableColumn<Contrats, Double> pedition;

    @FXML
    private TableColumn<Contrats, Contrats> actions;

    @FXML
    private TextField search_field;
    
    private final ObservableList<Contrats> contrats=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Contrats>> listdata=new SimpleObjectProperty<>(contrats);

    @FXML
    void home() {
        InitMenu.Home();
    }

    @FXML
    void nouveau() {
        add_change(null);
    }

    private void add_change(Contrats c){
        ViewBehavior contract=new ViewBehavior("Depense.fxml");
        Parent p=contract.getparent();
        ControllerManager cm=(ControllerManager)contract.getController();
        ((DepenseController)cm).setContrat(c);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }

    @FXML
    void refresh() {

    }

    @FXML
    void search() {

    }

    @Override
    protected void init() {
        nom.setCellValueFactory(data->data.getValue().livreProperty());
        contrat.setCellValueFactory(data->data.getValue().contratProperty());
        desc.setCellValueFactory(data->data.getValue().descriptionProperty());
        qty.setCellValueFactory(data->data.getValue().qtyProperty());
        mont.setCellValueFactory(data->data.getValue().prixProperty());
        pauteur.setCellValueFactory(data->data.getValue().auteurProperty());
        pedition.setCellValueFactory(data->data.getValue().editionProperty());
        actions.setCellFactory(data-> new EditActionsCell());
        actions.setCellValueFactory(data->new SimpleObjectProperty<>(data.getValue()));
        table_contrats.itemsProperty().bind(listdata);
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }

    @Override
    protected void initialized() {
        contrats.addAll(DAOFactory.createModel(DAOName.contrat).selectAll());
    }
    
    private class EditActionsCell extends TableCell<Contrats ,Contrats> {
        private Boutton delete,depense,view;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Contrats contrat, boolean b) {
            super.updateItem(contrat, b);
            if(!b && getItem()!=null){
                render(contrat);
            }
        }
        private void render(Contrats contrat){

            depense=new Boutton("");
            view=new Boutton("");
            delete=new Boutton("");
            depense.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BUYSELLADS));
            delete.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.REMOVE));
            view.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT_ALT));

            depense.setOnAction((action)->add_change(contrat));
            delete.setOnAction((action)->delete(contrat));
            HBox box=new HBox();
            if(contrat.getContrat().equals(TYPECONTRAT.E))
                box.getChildren().add(depense);
            box.getChildren().addAll(delete);
            setGraphic(box);

        }

        private void delete(Contrats contrat){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous supprimer cet Auteur?",rbs)== AlertButtons.YES) {

            }
        }
    }
}
