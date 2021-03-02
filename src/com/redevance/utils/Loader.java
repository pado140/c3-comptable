/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.utils;

import com.redevance.Main;
import com.redevance.dao.bean.IEntity;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

/**
 *
 * @author Padovano
 */
public class Loader extends Service<IEntity> {
    protected Stage loadDialog;
    public Stage runloading(){
        if(loadDialog==null){
            loadDialog=ViewUtils.Modal("loading.fxml", Main.primarystage);
        }
        return loadDialog;
    }
    private Runnable func;
    public Loader(Runnable r) {
        this.func=r;
        
    }
    
        @Override
        protected Task<IEntity> createTask() {
            return new Task(){
                @Override
                protected Void call() throws Exception {
                    Platform.runLater(func);
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