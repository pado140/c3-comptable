package com.redevance.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.redevance.Main;
import static com.redevance.Main.auteurs;
import static com.redevance.Main.categories;
import static com.redevance.Main.livres;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Auteurs;
import com.redevance.dao.bean.Categories;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.enumeration.TYPECONTRAT;
import com.redevance.services.AppServices;
import com.redevance.utils.AppUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewBookController extends ControllerManager{

    @FXML
    private AnchorPane content;

    @FXML
    private Pane pane_image;

    @FXML
    private ImageView image;

    @FXML
    private JFXTextField titre_field;

    @FXML
    private JFXTextArea description_field;

    @FXML
    private JFXTextField isbn_field;

    @FXML
    private ChoiceBox<Auteurs> auteurs_select;

    @FXML
    private JFXTextField edition_field;

    @FXML
    private JFXTextField qty_field;

    @FXML
    private JFXComboBox<TYPECONTRAT> contrat_select;

    @FXML
    private JFXDatePicker date_contrat;

    @FXML
    private JFXComboBox<Categories> categorie;

    @FXML
    private JFXTextField prix_field;
    
    private FileChooser  filechooser;
    private final String path="/ressources/no.png";
    private File file=null;
    private final ObjectProperty<ObservableList<Categories>> categorylist=new SimpleObjectProperty<>(categories);
    private final ObjectProperty<ObservableList<Auteurs>> auteurlist=new SimpleObjectProperty<>(auteurs);
    private Livres livre;
    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void close() {
        ((Stage)isbn_field.getScene().getWindow()).close();
    }

    @FXML
    void load() {
        file=filechooser.showOpenDialog(Main.primarystage);
        if(file!=null)
            if(file.exists()){
                try {
                    //image.getViewport().
                    image.setImage(new Image(new FileInputStream(file),image.getFitWidth(),image.getFitHeight(),false,false));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NewBookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }

    @FXML
    void new_cat(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {
        image.setImage(null);
    }

    @FXML
    void save(ActionEvent event) {
        if(AppUtils.validation(content)){
            livre.setQty(Integer.parseInt(qty_field.getText()));
            livre.setIsbn(isbn_field.getText());
            livre.setDescription(description_field.getText());
            livre.setTitre(titre_field.getText());
            livre.setAuteur(auteurs_select.getValue());
            livre.setCategorie(categorie.getValue());
            livre.setPrix(Double.parseDouble(prix_field.getText()));
            livre.setAnneesortie(edition_field.getText());
            livre=AppServices.Savebook(livre);
            if(livre!=null){
                Alert.success(Main.primarystage, "success", null);
                livres.add(livre);
                close();
            }
        }
    }

    @FXML
    void savennew(ActionEvent event) {

    }

    @Override
    protected void init() {
        UnaryOperator<TextFormatter.Change> filter = (TextFormatter.Change t) -> {
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
        };
        if(categories.isEmpty())
            categories.addAll(AppServices.categoryList());
        if(auteurs.isEmpty())
            auteurs.addAll(AppServices.auteurList());
        prix_field.setTextFormatter(new TextFormatter<>(filter));
        Rectangle clip=new Rectangle(image.getFitWidth(), image.getFitHeight());
        clip.setArcWidth(15);
        clip.setArcHeight(15);
        image.setClip(clip);
        filechooser=new FileChooser();
        filechooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("image", "*.png","*.jpg"));
        ObservableList<TYPECONTRAT> types=FXCollections.observableArrayList(TYPECONTRAT.A,TYPECONTRAT.E);
        categorie.itemsProperty().bind(categorylist);;
       
        auteurs_select.itemsProperty().bind(auteurlist); 
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livres) {
        this.livre = new Livres();
        if(livres!=null){
            this.livre=livres;
            try {
            System.out.println("");
            image.setImage(new Image(new FileInputStream(new File(livres.getImagelivre()))));
        } catch (FileNotFoundException ex) {
            //image.setImage(new Image(this.getClass().getResourceAsStream("/ressources/no.png"),image.getFitWidth(),image.getFitHeight(),false,false));
        }
            titre_field.setText(livres.getTitre());
            isbn_field.setText(livres.getIsbn());
            isbn_field.setDisable(true);
            //contrat_select.selectionModelProperty().get().select(livres.getContrat().getType_contrat());
            //contrat_select.setDisable(true);
            auteurs_select.getSelectionModel().select(livres.getAuteur());
            edition_field.setText(livres.getEdition());
            qty_field.setText(String.valueOf(livres.getQty()));
            //date_contrat.setValue(livres.getContrat().getDate());
            //date_contrat.setDisable(true);
            description_field.setText(livres.getDescription());
            System.out.println("categorie:"+livres.getCategorie());
            categorie.setValue(livres.getCategorie());
            prix_field.setText(String.valueOf(livres.getPrix()));
        }
    }
    
    

}
