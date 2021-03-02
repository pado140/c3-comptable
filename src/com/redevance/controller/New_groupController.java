/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.redevance.Main;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Groups;
import com.redevance.dao.bean.Roles;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.dao.dao.GroupsModel;
import com.redevance.exceptions.DuplicateException;
import com.redevance.resources.view.ListViewMultiple;
import com.redevance.utils.AppUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class New_groupController extends ControllerManager {

    @FXML
    private Label panLabel1;

    @FXML
    private Label panLabel2;

    @FXML
    private TabPane wizard;

    @FXML
    private Tab about;

    @FXML
    private Label groupNameLabel;

    @FXML
    private TextField gname;

    @FXML
    private Label descLabel;

    @FXML
    private JFXButton next0;

    @FXML
    private TextArea desc;

    @FXML
    private Tab roles;

    @FXML
    private JFXButton done;

    @FXML
    private JFXButton previous1;

    @FXML
    private ListViewMultiple<Roles> selector;

    private Groups group;

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
        if(group!=null){
            gname.setText(group.getName());
            desc.setText(group.getDescription());
            selector.getSources().removeAll(group.getRoles());
            selector.getCible().addAll(group.getRoles());
        }else
            this.group=new Groups();
    }

    private ObservableList<Roles> allRoles;
    @FXML
    void close() {
        ((Stage)wizard.getScene().getWindow()).close();
    }

    private ObjectProperty<ObservableList<Roles>> roleAvailable;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources); //To change body of generated methods, choose Tools | Templates.
        roleAvailable=new SimpleObjectProperty<>(Main.roles);
        roles.setDisable(true);
        next0.setOnAction((event)->{
            if(AppUtils.validation((AnchorPane)about.getContent())){
                if(roles.isDisable())
                    roles.setDisable(false);
                wizard.getSelectionModel().selectNext();
            }
        });
        
        previous1.setOnAction((event)->{
                wizard.getSelectionModel().selectPrevious();
        });
        
        selector.getSources().addAll(Main.roles);
        ((AnchorPane)roles.getContent()).getChildren().add(selector);
        done.setOnAction((actionevent)->{
            if(AppUtils.validation((AnchorPane)roles.getContent())){
            group.setName(gname.getText().trim());
            group.setDescription(desc.getText().trim());
            
            group.setRoles(selector.getCible());
            try{
            group=((GroupsModel) DAOFactory.createModel(DAOName.group)).save(group);
            if(group!=null){
                Alert.success(((Stage)wizard.getScene().getWindow()), "Enregistrement avec succes", traduction.get());
//                Main.groups.add(group);
            }
            }catch(DuplicateException d){
                group=null;
                Alert.error(((Stage)wizard.getScene().getWindow()), d.getMessage(), traduction.get());
            }
            if(group!=null)
                close();
            }
        });
        
    }

    @Override
    protected void init() {
        selector=new ListViewMultiple<>();
        selector.setPrefSize(614,120);
        selector.setLayoutY(39);
        selector.setLayoutX(42);
        selector.render();
    }

    private void finish(){
        
    }
}
