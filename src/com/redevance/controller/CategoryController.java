package com.redevance.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import com.redevance.Main;
import com.redevance.dao.bean.Genre;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.utils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static com.redevance.Main.primarystage;
import com.redevance.dao.bean.Categories;

public class CategoryController extends ControllerManager {
    @FXML
    private TableView<Categories> table_genres;

    @FXML
    private TableColumn<Categories, Integer> id;

    @FXML
    private TableColumn<Categories, String> genre;
    private ObjectProperty<ObservableList<Categories>> listdata= new SimpleObjectProperty<>(Main.categories);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        id.setCellValueFactory((data)->data.getValue().idProperty());
        genre.setCellValueFactory(data->data.getValue().categorieProperty());
        table_genres.itemsProperty().bind(listdata);
    }

    @Override
    protected void init() {
        if(Main.categories.isEmpty())
            Main.categories.addAll(DAOFactory.createModel(DAOName.category).selectAll());
        //listdata.get().addAll(Main.genres);
    }

    @FXML
    void newGenre(ActionEvent event) {
        add_change(null);
    }

    private void add_change(Genre g){
        ViewBehavior newgenre=new ViewBehavior("new_genre.fxml");
        Parent p=newgenre.getparent();
        ControllerManager cm=(ControllerManager)newgenre.getController();
        //((NewGenreController)cm).setGenre(g);
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
    }
}
