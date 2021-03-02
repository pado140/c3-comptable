package com.redevance.controller;

import com.redevance.Main;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Contrats;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.enumeration.TYPECONTRAT;
import com.redevance.services.AppServices;
import com.redevance.utils.AppUtils;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewContratController extends ControllerManager{

    @FXML
    private Label livre;

    @FXML
    private TextField pauteur;

    @FXML
    private TextField pedition;

    @FXML
    private Spinner<Integer> qty;

    @FXML
    private ComboBox<TYPECONTRAT> contrat;

    @FXML
    private TextField mnt;
    
    @FXML
    private AnchorPane content;
    
    private Livres books;
    private Contrats contract;

    @FXML
    void close() {
        ((Stage)livre.getScene().getWindow()).close();
    }

    @FXML
    void save() {
        if(AppUtils.validation(content)){
            Contrats contract=new Contrats();
            contract.setLivre(books);
            contract.setContrat(contrat.getSelectionModel().getSelectedItem());
            contract.setAuteur(Double.parseDouble(pauteur.getText()));
            contract.setEdition(Double.parseDouble(pedition.getText()));
            contract.setQty(qty.getValue());
            contract.setPrix(Double.parseDouble(this.mnt.getText()));
            
            contract=AppServices.SaveContrat(contract,null);
            if(contract!=null)
            {
                Alert.success(Main.primarystage, "Nouveau contrat cree pou ce livre", null);
            }
        }
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
        mnt.setTextFormatter(new TextFormatter(filter));
        SpinnerValueFactory<Integer> factory=new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE);
        contrat.getItems().addAll(TYPECONTRAT.A,TYPECONTRAT.E);
        qty.setValueFactory(factory);
        pauteur.setTextFormatter(new TextFormatter<>(filter));
        pedition.setTextFormatter(new TextFormatter<>(filter));
    }

    public Livres getBook() {
        return books;
    }

    public void setBook(Livres books) {
        this.books = books;
        if(books==null){
            this.books=new Livres();
            return;
        }
        livre.setText(this.books.getTitre());
    }

    public Contrats getContrat() {
        return contract;
    }

    
    
}
