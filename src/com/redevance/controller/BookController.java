/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import com.redevance.Main;
import static com.redevance.Main.livres;
import static com.redevance.Main.primarystage;
import static com.redevance.Main.rbs;
import com.redevance.alert.Alert;
import com.redevance.alert.AlertButtons;
import com.redevance.dao.bean.Auteurs;
import com.redevance.dao.bean.Categories;
import com.redevance.dao.bean.Livres;
import com.redevance.resources.menu.vertical.InitMenu;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.services.AppServices;
import com.redevance.utils.Boutton;
import com.redevance.utils.ViewUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.stream.Collectors;
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

/**
 *
 * @author Padovano
 */
public class BookController extends ControllerManager{
        
    @FXML
    private TextField search_field;

    @FXML
    private TableView<Livres> table_list;

    @FXML
    private TableColumn<Livres, String> titre;

    @FXML
    private TableColumn<Livres, String> isbn;

    @FXML
    private TableColumn<Livres, String> description;

    @FXML
    private TableColumn<Livres, Categories> category;

    @FXML
    private TableColumn<Livres, Auteurs> auteur;

    @FXML
    private TableColumn<Livres, String> edition;

    @FXML
    private TableColumn<Livres, Integer> qty;

    @FXML
    private TableColumn<Livres, Double> prix;
   
    @FXML
    private TableColumn<Livres, Livres> compt;
    
    @FXML
    private TableColumn<Livres, Livres> editcol;
    
    private final ObservableList<Livres> books=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Livres>> bookList=new SimpleObjectProperty<>(books);
    
    private String listType="load";
    
    @Override
    protected void init() {
        table_list.itemsProperty().bind(bookList);
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
        titre.setCellValueFactory((data)->data.getValue().titreProperty());
        isbn.setCellValueFactory((data)->data.getValue().isbnProperty());
        description.setCellValueFactory((data)->data.getValue().descriptionProperty());
        category.setCellValueFactory((data)->data.getValue().categorieProperty());
        edition.setCellValueFactory((data)->data.getValue().anneesortieProperty());
        qty.setCellValueFactory((data)->data.getValue().qtyProperty());
        prix.setCellValueFactory((data)->data.getValue().prixProperty());
        auteur.setCellValueFactory(value->value.getValue().auteurProperty());
        editcol.setCellFactory(data->new EditActionsCell());
        editcol.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        compt.setCellFactory(data->new ComptActionsCell());
        compt.setCellValueFactory(data->{return new SimpleObjectProperty<>(data.getValue());});
        
//        table_list.getSelectionModel().selectedItemProperty().
//        table_list.itemsProperty().bind(bookList);
    }
    
    @FXML
    void home(ActionEvent event) {
        InitMenu.Home();
    }

    @FXML
    void newBook(ActionEvent event) {
        add_change(null);
    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void searchtype(KeyEvent event) {
        books.clear();
        String val=search_field.getText().trim().toLowerCase();
        books.addAll(livres.parallelStream().filter(livre->{return livre.getTitre().toLowerCase().contains(val)||
                livre.getAuteur().getNom().toLowerCase().contains(val)||
                livre.getAuteur().getPrenom().toLowerCase().contains(val);}).collect(Collectors.toList())); 
    }
    
    @Override
    protected void initialized(){
        if(!listType.equals("load")){
            livres.clear();
            Main.livres.addAll(AppServices.bookList());
        }
        books.clear();
        books.addAll(Main.livres);      
    }
    
    @FXML
    void refresh() {
        listType="refresh";
        if(loader.getState()!= Worker.State.READY)
                loader.reset();
            loader.start();
    }
    
    private void add_change(Livres L){
        ViewBehavior newbook=new ViewBehavior("newBook.fxml");
        Parent p=newbook.getparent();
        NewBookController nbc=(NewBookController)newbook.getDefaultController();
        nbc.setLivre(L);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }
    
    private class EditActionsCell extends TableCell<Livres ,Livres> {
        private Boutton delete,edit,view;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Livres livre, boolean b) {
            super.updateItem(livre, b);
            if(!b && getItem()!=null){
                render(livre);
            }
        }
        private void render(Livres livre){

            edit=new Boutton("");
            view=new Boutton("");
            delete=new Boutton("");
            edit.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
            delete.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.REMOVE));
            view.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT_ALT));

            edit.setOnAction((action)->add_change(livre));
            delete.setOnAction((action)->delete(livre));
            HBox box=new HBox();
            box.getChildren().addAll(edit,delete,view);
            setGraphic(box);

        }

        private void delete(Livres livre){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous supprimer cet Auteur?",rbs)== AlertButtons.YES) {

            }
        }
    }
    private class ComptActionsCell extends TableCell<Livres ,Livres> {
        private Boutton promotion,toauteur,report,contrat;

        @Override
        public void updateIndex(int i) {
            super.updateIndex(i);
        }

        @Override
        protected void updateItem(Livres livre, boolean b) {
            super.updateItem(livre, b);
            if(!b && getItem()!=null){
                render(livre);
            }
        }
        private void render(Livres livre){

            promotion=new Boutton("");
            toauteur=new Boutton("");
            report=new Boutton("");
            contrat=new Boutton("");
            promotion.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.SPOTIFY));
            toauteur.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.SHARE_SQUARE_ALT));
            report.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.LINE_CHART));
            contrat.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.FILE));

            promotion.setOnAction((action)->createpromo(livre));
            toauteur.setOnAction((action)->retourAuteur(livre));
            report.setOnAction((action)->production(livre));
            contrat.setOnAction((action)->contrat(livre));
            HBox box=new HBox();
            box.getChildren().addAll(promotion,toauteur,report);
            if(livre.getContrat()==null)
                box.getChildren().add(contrat);
            setGraphic(box);

        }

        private void delete(Livres livre){
            if(Alert.showConfirmMessage(primarystage,"Voulez vous supprimer cet Auteur?",rbs)== AlertButtons.YES) {

            }
        }
    }
    
    public void createpromo(Livres l){
        ViewBehavior newbook=new ViewBehavior("promotion.fxml");
        Parent p=newbook.getparent();
        PromotionController nbc=(PromotionController)newbook.getDefaultController();
        nbc.setLivre(l);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        refresh();
    }
    
    public void retourAuteur(Livres l){
        ViewBehavior newbook=new ViewBehavior("RetourAuteur.fxml");
        Parent p=newbook.getparent();
        RetourAuteurController nbc=(RetourAuteurController)newbook.getDefaultController();
        nbc.setLivre(l);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        refresh();
    }
    public void production(Livres l){
        ViewBehavior newbook=new ViewBehavior("Production.fxml");
        Parent p=newbook.getparent();
        ProductionController nbc=(ProductionController)newbook.getDefaultController();
        nbc.setLivre(l);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }
    public void contrat(Livres l){
        ViewBehavior newContrat=new ViewBehavior("NewContract.fxml");
        Parent p=newContrat.getparent();
        NewContratController nbc=(NewContratController)newContrat.getDefaultController();
        nbc.setBook(l);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        table_list.refresh();
    }
}
