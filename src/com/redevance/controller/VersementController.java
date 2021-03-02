package com.redevance.controller;

import static com.redevance.Main.primarystage;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Redevance;
import com.redevance.utils.AppUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class VersementController extends ControllerManager{

    @FXML
    private AnchorPane content;

    @FXML
    private TextField mnt;

    @FXML
    private TextField check;

    @FXML
    private DatePicker created;
    
    private Redevance redevance;

    public Redevance getRedevance() {
        return redevance;
    }

    public void setRedevance(Redevance redevance) {
        this.redevance = redevance;
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {
 
    }

    @FXML
    void save(ActionEvent event) {
        if(AppUtils.validation(content)&&created.getValue()!=null){
            double montant=Double.parseDouble(mnt.getText().trim());
            if(montant>redevance.getMontant()){
                Alert.error(primarystage, "vous pouvez pas verser plus que ce que vous n'avez pas", null);
                return;
            }
            redevance.setCheckno(check.getText());
            redevance.setDateverser(created.getValue());
        }
    }

    @Override
    protected void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
