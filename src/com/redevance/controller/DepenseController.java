package com.redevance.controller;

import static com.redevance.Main.primarystage;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Contrats;
import com.redevance.dao.bean.Depenses;
import com.redevance.services.AppServices;
import com.redevance.utils.AppUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepenseController extends ControllerManager{

    @FXML
    private TextField ddep;

    @FXML
    private TableView<Depenses> table;

    @FXML
    private TableColumn<ObservableList , Integer> article;

    @FXML
    private TableColumn<Depenses, String> desc;

    @FXML
    private TableColumn<Depenses, Double> mt;

    @FXML
    private TableColumn<Depenses, Depenses> act;

    @FXML
    private Button save;

    @FXML
    private TextField mntdep;

    @FXML
    private Button load;
    
    private Contrats contrat;
    
    private final ObservableList<Depenses> depenseList=FXCollections.observableArrayList();
    private final ObservableList<Depenses> removeList=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Depenses>>listData=new SimpleObjectProperty<>(depenseList);

    @FXML
    void close(ActionEvent event) {
        ((Stage)load.getScene().getWindow()).close();
    }

    @FXML
    void load(ActionEvent event) {
        if(AppUtils.valid(ddep)&&AppUtils.valid(mntdep)){
            Depenses dep=new Depenses();
            dep.setContrat(contrat);
            dep.setDescription(ddep.getText().trim());
            dep.setMontant(Double.valueOf(mntdep.getText().trim()));
            depenseList.add(dep);
        }
    }

    @FXML
    void onSave(ActionEvent event) {
        contrat.setDepenses(depenseList);
        contrat=AppServices.SaveContrat(contrat, removeList);
        if(contrat!=null){
            Alert.success(primarystage, "Contrat modifie avec succes", null);
        }
    }

    @Override
    protected void init() {
        desc.setCellValueFactory(data->data.getValue().descriptionProperty());
        mt.setCellValueFactory(data->data.getValue().montantProperty());
        table.itemsProperty().bind(listData);
    }

    public Contrats getContrat() {
        return contrat;
    }

    public void setContrat(Contrats contrat) {
        this.contrat = contrat;
        depenseList.addAll(contrat.getDepenses());
    }

}
