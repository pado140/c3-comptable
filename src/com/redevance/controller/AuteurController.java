package com.redevance.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.redevance.Main;
import com.redevance.alert.Alert;
import com.redevance.alert.AlertButtons;
import com.redevance.dao.bean.Auteurs;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.utils.Boutton;
import com.redevance.utils.ViewUtils;


import static com.redevance.Main.*;
import com.redevance.resources.menu.vertical.InitMenu;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;

public class AuteurController extends ControllerManager {
    
    @FXML
    private TableView<Auteurs> table_auteurs;

    @FXML
    private TableColumn<Auteurs, String> nom;

    @FXML
    private TableColumn<Auteurs, String> prenom;

    @FXML
    private TableColumn<Auteurs, String> tel;

    @FXML
    private TableColumn<Auteurs, String> mail;

    @FXML
    private TableColumn<Auteurs, Auteurs> actions;

    @FXML
    private Label name;

    @FXML
    private Label firstname;

    @FXML
    void home(ActionEvent event) {
        InitMenu.Home();
    }
    
    @FXML
    void search(KeyEvent event) {
        filtreAuteur.clear();
        String critere=((TextField)event.getSource()).getText();
        auteurs.forEach(a->{
            if(a.getNom().toLowerCase().contains(critere.toLowerCase())||a.getPrenom().toLowerCase().contains(critere.toLowerCase()))
                filtreAuteur.add(a);
        });
        table_auteurs.refresh();
    }

    private final ObservableList<Auteurs> filtreAuteur= FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Auteurs>> listdata= new SimpleObjectProperty<>(filtreAuteur);

    @Override
    protected void init() {
        
        nom.setCellValueFactory(data->data.getValue().nomProperty());
        prenom.setCellValueFactory(data->data.getValue().prenomProperty());
//        notice.setCellValueFactory(data->data.getValue().noticeProperty());
        actions.setCellFactory(data->new ActionsCell());
        actions.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        table_auteurs.itemsProperty().bind(listdata);
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }

    @FXML
    void nouveau() {
        add_change(null);
    }

    private void add_change(Auteurs A){
        ViewBehavior newgenre=new ViewBehavior("new_auteur.fxml");
        Parent p=newgenre.getparent();
        ControllerManager cm=(ControllerManager)newgenre.getController();
        ((NewAuteurController)cm).setAuteur(A);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }

    private class ActionsCell extends TableCell<Auteurs ,Auteurs> {
        private Boutton delete,edit,view;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Auteurs auteur, boolean b) {
            super.updateItem(auteur, b);
            if(!b && getItem()!=null){
                render(auteur);
            }
        }
        private void render(Auteurs auteur){

            edit=new Boutton("");
            view=new Boutton("info");
            delete=new Boutton("");
            edit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
            delete.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.REMOVE));

            edit.setOnAction((action)->add_change(auteur));
            delete.setOnAction((action)->delete(auteur));
            HBox box=new HBox();
            box.getChildren().addAll(edit,delete);
            setGraphic(box);

        }

        private void delete(Auteurs auteur){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous supprimer cet Auteur?",rbs)== AlertButtons.YES) {

            }
        }
    }
    
    
    
    @Override
    protected void initialized(){
        Main.auteurs.clear();
        Main.auteurs.addAll(DAOFactory.createModel(DAOName.auteur).selectAll());
        filtreAuteur.clear();
        filtreAuteur.addAll(auteurs);
    }
    
    @FXML
    protected void refresh(){
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }
}
