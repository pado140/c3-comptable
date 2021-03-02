package com.redevance.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.redevance.Main;
import com.redevance.dao.bean.*;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.dao.dao.RolesModel;
import com.redevance.security.Hash;

public class LoginController extends ControllerManager{
    @FXML
    private TextField username;

    @FXML
    private PasswordField pass;

    @FXML
    private Label loading;

    private Users user;

    private Login log;
    @FXML
    void forget() {

    }

    @FXML
    void signin() {
            if(log.getState()!= Worker.State.READY)
                log.reset();
            log.start();
            
    }

    @Override
    protected void init() {
        log=new Login();
        
        log.runningProperty().addListener((o,a,n)->{
            if(n){
                username.setDisable(true);
                pass.setDisable(true);
            }else{
                username.setDisable(false);
                pass.setDisable(false);
            }
        });
    }
    @FXML
    private void close(){
        ((Stage)pass.getScene().getWindow()).close();
    }

    private void authenticate(){

        user=new Users();
        user.setUsername(username.getText().trim());
        user.setPass(Hash.crypt(pass.getText()));
        System.out.println("pass:"+Hash.crypt(pass.getText()));
        user=(Users) DAOFactory.createModel(DAOName.user).exists(user);
        try{
            if(user.getPass().equals(Hash.crypt(pass.getText()))){
                Groups group=user.getGroups();
                ObservableList<Roles> roles=((RolesModel)DAOFactory.createModel(DAOName.role)).load("groups_id=?",group.getId());
                user.getGroups().setRoles(roles);
                setUser(user);
            }
            else{
                System.out.println("dss");
                setUser(null);
            }
        }catch(NullPointerException ex){
            setUser(user);
            ex.printStackTrace();
        }

    }
    private class Login extends Service<Void> {

        @Override
        protected Task<Void> createTask() {
            return new Task(){
                @Override
                protected Void call() throws Exception {

                    loading.setVisible(true);
                    Platform.runLater(()->{
                        loading.setText("Authenticating...");
                    });
                    authenticate();
                    Thread.sleep(1000);
                    Platform.runLater(()->{
                        loading.setText("verifying user");
                    });
                    Thread.sleep(1000);
                    if(getUser()!=null){
                        System.out.println(getUser());
                        if(!getUser().getEnable()){
                            Platform.runLater(()->{
                            loading.setText("user is disabled");
                        });
                            return null;
                        }
                        Platform.runLater(()->{
                            loading.setText("user is connected");
                        });
                        Thread.sleep(1000);
                        Platform.runLater(()->{
                            loading.setText("App is initializing");

                        });
                        Thread.sleep(500);
                        Platform.runLater(()->{
                            loading.setText("Loading Menu...");

                        });
                        Thread.sleep(1000);
                        
                        if(!user.getType().equalsIgnoreCase("user")) {
                            Thread.sleep(1000);
//                            Platform.runLater(() -> {
//                                loading.setText("Loading Genres...");
//                                Main.genres.addAll(DAOFactory.createModel(DAOName.genre).selectAll());
//                            });
//                            Thread.sleep(1000);
//                            Platform.runLater(() -> {
//                                loading.setText("Loading Auteurs...");
//                                Main.auteurs.addAll(DAOFactory.createModel(DAOName.auteur).selectAll());
//                            });
                        }
                        Thread.sleep(1000);
                        Platform.runLater(()->{
                            loading.setText("App is launching");
                            close();
                        });
                        Platform.runLater(()->{
                            loading.setText("App is initializing");
                            close();
                        });

                    }else{
                        Platform.runLater(()->{
                            loading.setText("Wrong username or password, try again");
                        });
                    }

                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded(); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                protected void running() {
                    super.running(); //To change body of generated methods, choose Tools | Templates.
                }


            };

        }
    }
}
