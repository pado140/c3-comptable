package com.redevance.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.utils.AppUtils;

import static com.redevance.Main.users;
import com.redevance.dao.bean.Users;
import com.redevance.security.Hash;

public class ChangePassController extends ControllerManager {

    boolean closed=false;
    @FXML
    private Label user;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField passc;

    @FXML
    private Label error;

    @FXML
    void close() {
        closed=true;
        getContext(user).close();
    }

    @FXML
    void confirm() {
        error.setVisible(false);
        if(AppUtils.valid(pass,6)){
            if(AppUtils.valid(pass,passc)){
                users.setPass(Hash.crypt(pass.getText()));
                if(DAOFactory.createModel(DAOName.user).save(users)==null){
                    users.setPass(Hash.crypt(users.getDefaultpass()));
                };

                closed=false;
                getContext(user).close();
            }else{
                error.setText("Les Mot de passe doivent etre identiques");
                error.setVisible(true);
            }
        }else {
            error.setText("Le Mot de passe doit avoir 6 caracteres au minimum");
            error.setVisible(true);
        }
    }
    @Override
    protected void init() {
        //user.setText(String.join(" ", users.getPrenom(), users.getNom()));
    }
}
