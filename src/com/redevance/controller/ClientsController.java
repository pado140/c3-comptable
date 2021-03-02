package com.redevance.controller;

import static com.redevance.Main.clients;
import static com.redevance.Main.primarystage;
import static com.redevance.Main.rbs;
import com.redevance.alert.Alert;
import com.redevance.alert.AlertButtons;
import com.redevance.dao.bean.Auteurs;
import com.redevance.dao.bean.Clients;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClientsController extends ControllerManager{

    @FXML
    private TextField search_field;

    @FXML
    private TableView<Clients> table_clients;

    @FXML
    private TableColumn<Clients, String> nom;

    @FXML
    private TableColumn<Clients, String> addresse;

    @FXML
    private TableColumn<Clients, String> tel;

    @FXML
    private TableColumn<Clients, String> type;

    @FXML
    private TableColumn<Clients, Clients> actions;

    @Override
    protected void initialized(){
        clients.clear();
        clients.addAll(DAOFactory.createModel(DAOName.client).selectAll());
        filtreClients.clear();
        filtreClients.addAll(clients);
    }
    
    @FXML
    protected void refresh(){
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }
    
    @FXML
    void home(ActionEvent event) {
        InitMenu.Home();
    }
    
    @FXML
    void search(KeyEvent event) {
        filtreClients.clear();
        String critere=((TextField)event.getSource()).getText();
        clients.forEach(a->{
            if(a.getNom().toLowerCase().contains(critere.toLowerCase()))
                filtreClients.add(a);
        });
        table_clients.refresh();
    }

    private final ObservableList<Clients> filtreClients= FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Clients>> listdata= new SimpleObjectProperty<>(filtreClients);

    @Override
    protected void init() {
        nom.setCellValueFactory(data->data.getValue().nomProperty());
        addresse.setCellValueFactory(data->data.getValue().adresseProperty());
        actions.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        table_clients.itemsProperty().bind(listdata);
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
    

}
