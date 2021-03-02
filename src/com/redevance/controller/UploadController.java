package com.redevance.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.redevance.Main;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.utils.AppUtils;
import com.redevance.utils.DocUtils;
import com.redevance.utils.FileUtils;

import java.io.File;
import java.nio.file.Path;

import static com.redevance.Main.users;
import java.nio.file.Paths;

public class UploadController extends ControllerManager {
    @Override
    protected void init() {
        fileChooser=new FileChooser();
        fileChooser.setTitle("Choisissez les fichiers ajouter dans le projet");
        listFiles.itemsProperty().bind(new SimpleObjectProperty<>(files));
        listFiles.getSelectionModel().selectedItemProperty().addListener((o,a,n)->{

        });
    }


    private final ObservableList<File> files= FXCollections.observableArrayList();
    private Livres livre;

    private FileChooser fileChooser;

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
        if(livre==null)
            this.livre=new Livres();
        System.out.println(this.livre);
    }
    @FXML
    private AnchorPane loader;

    @FXML
    private ListView<File> listFiles;

    @FXML
    void close(ActionEvent event) {
            ((Stage)loader.getScene().getWindow()).close();
    }

    @FXML
    void load(DragEvent event) {
        files.addAll(event.getDragboard().getFiles());
    }

    @FXML
    void open(ActionEvent event) {
        files.addAll(fileChooser.showOpenMultipleDialog(loader.getScene().getWindow()));
    }

    @FXML
    void upload(ActionEvent event) {
        Alert.success((Stage)loader.getScene().getWindow(),"success", Main.rbs);
    }

   

    private void DocFile(File f){
        DocUtils du=new DocUtils(f);
        System.out.println(du.docProperties().getPages()+" pages");
        System.out.println(du.docProperties().getWords()+" mots");
        System.out.println(du.docProperties().getParagraphs()+" paragraphs");
    }

    private String getExt(String path){
        System.out.println(path.substring(path.lastIndexOf(".")));
        return path.substring(path.lastIndexOf("."));
    }

    private String getNameWithoutExtension(String path){
        return path.substring(0,path.lastIndexOf("."));
    }

}
