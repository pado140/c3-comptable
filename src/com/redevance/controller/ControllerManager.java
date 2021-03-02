/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;


import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.redevance.Main;
import com.redevance.dao.bean.ImplEntity;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Users;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.resources.lang.TraductionUtils;
import com.redevance.resources.menu.vertical.InitMenu;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.utils.ViewUtils;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Parent;

/**
 *
 * @author Padovano
 */
public abstract class ControllerManager implements Initializable{
    protected final Runnable localeChange=this::translate;
    protected final ObservableMap<String ,Labeled> toTranslate=FXCollections.observableHashMap();
    protected final ObjectProperty<TraductionUtils> traduction=new SimpleObjectProperty<>();
    protected AnchorPane main_panel;
    protected DAOFactory<ImplEntity> factory;
    protected Stage loadDialog;
    public static ObjectProperty<Boolean> connected=new SimpleObjectProperty<>();
    protected load loader;
    public Users getUser() {
        return Main.users;
    }
    
    public Stage runloading(){
        if(loadDialog==null){
            loadDialog=ViewUtils.Modal("loading.fxml", Main.primarystage);
        }
        return loadDialog;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connected.addListener((obs,old,ne)->{
            System.out.println("ok disconneted");
        });
        traduction.addListener((observable, oldValue, newValue) -> {
            translate();
        });
        
        loader=new load();
        init();
        
    }

    
    protected abstract void init();
    
    public void setUser(Users user) {
        Main.users = user;
    }

    public AnchorPane getMain_view() {
        return main_panel;
    }

    public void setMain_view(AnchorPane main_view1) {
        this.main_panel = main_view1;
    }

    protected void translate(){
        if(!isI18n()){
            System.err.println(isI18n());
            return;
        }
        System.err.println(traduction.get());
        toTranslate.keySet().stream().forEach((text) -> {
            translateable(toTranslate.get(text), text);
        });
        
    }
    
    public void I18n(TraductionUtils tr){
        if (traduction.get() != null) {
            traduction.get().removeListener(localeChange);
        }
        if(tr==null)
            return;
        traduction.set(tr);
        traduction.get().addListener(localeChange);
        System.out.println("traduction:"+tr);
    }
    
    protected boolean isI18n(){
        return traduction.get()!=null;
    }
    
    protected void translateable(StringProperty lab){
        
    }
    protected void translateable(Labeled lab,String label){
        Platform.runLater(()->{lab.setText(traduction.get().Translate(label));});
    }
    protected void translateable(Pane pane){
        pane.getChildren().parallelStream().forEach(node -> {
            if(node instanceof Labeled) {
                if(((Labeled) node).getId()!=null)
                Platform.runLater(() -> {
                    ((Labeled) node).setText(traduction.get().Translate(((Labeled) node).getId()));
                });
            }
            if(node instanceof TableView){
                ((TableView)node).getColumns().parallelStream().forEach(o -> {
                    if(((TableColumn)o).getId()!=null)
                    Platform.runLater(()->{((TableColumn)o).setText(traduction.get().Translate(((TableColumn)o).getId()));});
                });

            }
            if(node instanceof Pane) {
                translateable((Pane)node);
            }
        });
    }

    protected Stage getContext(Node n){
        return (Stage)n.getScene().getWindow();
    }
    
    protected void initialized(){};
    protected class load extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task(){
                @Override
                protected Void call() throws Exception {
                    initialized();
                    return null;
                }
                

                @Override
                protected void succeeded() {
                    System.out.println("executed");
                    super.succeeded(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
                }

                @Override
                protected void failed() {
                    super.failed(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
                }

                @Override
                protected void cancelled() {
                    super.cancelled(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
                }

                @Override
                protected void scheduled() {
                    super.scheduled(); //To change body of generated methods, choose Tools | Templates.
                    runloading().close();
                    Main.primarystage.setOpacity(1);
                }
                

                @Override
                protected void running() {
                    super.running(); //To change body of generated methods, choose Tools | Templates.
                    runloading().show();
                }
            };

        }
    }
}
