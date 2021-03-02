package com.redevance.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.redevance.Main;
import com.redevance.alert.Alert;
import com.redevance.dao.ORM.ConnectionSql;
import com.redevance.dao.bean.Auteurs;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.exceptions.DuplicateException;
import com.redevance.utils.AppUtils;
import com.redevance.utils.FileUtils;


public class NewAuteurController extends ControllerManager {

    @FXML
    private AnchorPane content;
    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField tel;

    @FXML
    private TextField mail;


    private Auteurs auteur;

    public Auteurs getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteurs auteur) {
        this.auteur = auteur;
        if(auteur==null) {
            this.auteur = new Auteurs();
            return;
        }
            nom.setText(auteur.getNom());
            prenom.setText(auteur.getPrenom());
            tel.setText(auteur.getTelephone());
            mail.setText(auteur.getEmail());
    }

    private final FileChooser fileChooser=new FileChooser();

    @Override
    protected void init() {

    }
    @FXML
    void cancel(ActionEvent event) {
        closed();
    }

    @FXML
    void closed() {
        ((Stage)nom.getScene().getWindow()).close();
    }

    @FXML
    void save(ActionEvent event) {
        FileUtils fu=null;
        boolean isnew=auteur.isNew();
        if(AppUtils.validation(content)){
            auteur.setNom(nom.getText().trim());
            auteur.setPrenom(prenom.getText().trim());
            auteur.setTelephone(tel.getText().trim());
            auteur.setEmail(mail.getText().trim());
            try {
                auteur = (Auteurs) DAOFactory.createModel(DAOName.auteur).save(auteur);
                if (auteur != null) {
                    if (isnew)
                        Main.auteurs.add(auteur);
                    else
                        Main.auteurs.set(Main.auteurs.indexOf(auteur), auteur);
                    Alert.success((Stage) nom.getScene().getWindow(), "Nouveau auteur ajouter avec succes", null);
                    closed();
                    return;
                }
                
                Alert.error((Stage) nom.getScene().getWindow(), "desolee" + ConnectionSql.instance().getErreur(), null);
                return;
            }catch (DuplicateException ex){
                Alert.error((Stage) nom.getScene().getWindow(), "desolee" + ex.getMessage(), null);
            }
        }
        Alert.error((Stage)nom.getScene().getWindow(),"Svp rempli le champs avant d'essayer d'enregistrer",null);
    }
    
}
