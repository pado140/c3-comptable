/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class CardController extends ControllerManager {
    
    @FXML
    private Label name,value;

    @FXML
    private FontAwesomeIconView icon;

    public Label getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public Label getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value.setText(value);
    }   
    
    private void principal(){
        icon.setIcon(FontAwesomeIcon.MINUS);
        icon.setFill(Paint.valueOf("#656565"));
    }
    public void up(){
        icon.setIcon(FontAwesomeIcon.ARROW_UP);
        icon.setFill(Paint.valueOf("#02d028"));
    }
    public void down(){
        icon.setIcon(FontAwesomeIcon.ARROW_DOWN);
        icon.setFill(Paint.valueOf("#ce0202"));
    }

    @Override
    protected void init() {
        principal();
    }
}
