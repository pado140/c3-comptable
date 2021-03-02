/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.controller;

import static com.redevance.Main.primarystage;
import static com.redevance.Main.ventes;
import com.redevance.alert.Alert;
import com.redevance.dao.bean.Clients;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Vente_details;
import com.redevance.dao.bean.Ventes;
import com.redevance.resources.view.ViewBehavior;
import com.redevance.services.AppServices;
import com.redevance.utils.ViewUtils;
import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Padovano
 */
public class VenteController extends ControllerManager {
    @FXML
    private TextField code;
    
    @FXML
    private CheckBox credit;
    
    @FXML
    private CheckBox cash;

    @FXML
    private DatePicker created;

    @FXML
    private Label qt;

    @FXML
    private Label mtot;

    @FXML
    private TableView<Vente_details> table;

    @FXML
    private Text name;

    @FXML
    private Text familly;

    @FXML
    private Text price;
    
    @FXML
    private TextField client;
    
    @FXML
    private TextField reduction;
    
    @FXML
    private TableColumn<Vente_details, Livres> article;

    @FXML
    private TableColumn<Vente_details, String> desc;

    @FXML
    private TableColumn<Vente_details, Double> pu;
    
    @FXML
    private TableColumn<Vente_details, Double> r;

    @FXML
    private TableColumn<Vente_details, Double> mtr;
    
    @FXML
    private TableColumn<Vente_details, Integer> quantity;

    @FXML
    private TableColumn<Vente_details, Double> mt;

    @FXML
    private TableColumn<Vente_details, Vente_details> act;

    private ObjectProperty<Livres> selected;
    private Livres select;
    private SpinnerValueFactory<Integer> valueFactory;
    private int li=0,i=0;
    private double tot;
    private Clients clientSelected;
    
    private final ObservableList<Vente_details> detail=FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<Vente_details>> datalist=new SimpleObjectProperty<>(detail);
//    
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//        
//    }    
    @FXML
    private TextField filter;

    @FXML
    void filtre() {
        ViewBehavior filterL=new ViewBehavior("filtreLivre.fxml");
        Parent p=filterL.getparent();
        FiltreLivreController nbc=(FiltreLivreController)filterL.getDefaultController();
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        selected.set(nbc.getLivre());
        select=nbc.getLivre();
    }
    
    
    @FXML
    void selectcustomer() {
        ViewBehavior filterC=new ViewBehavior("filtreCustomer.fxml");
        Parent p=filterC.getparent();
        FiltreCustomerController nbc=(FiltreCustomerController)filterC.getDefaultController();
        Stage popup= ViewUtils.ModalFrame(p, primarystage);
        popup.showAndWait();
        clientSelected=nbc.getClient();
        if(clientSelected!=null)
            client.setText(clientSelected.toString());
    }
    
    private void fillInfos(Livres p){
        name.setText("");
                    familly.setText("");
                    price.setText("");
        if(p!=null){
            name.setText(p.getTitre());
                    familly.setText(p.getDescription());
                    price.setText(String.valueOf(p.getPrix()));
        }
    }
    
     @FXML
    void addToList(ActionEvent event) {
        
        if(selected.get()!=null){
            Vente_details vd=new Vente_details();
            vd.setLivre(select);
            vd.setPrix(select.getPrix());
            vd.setQty(1);
            System.out.println("hashcode:"+vd.hashCode());
            if(detail.contains(vd)){
                table.getSelectionModel().select(vd);
                System.out.println("duplicata");
            }
            else{
                detail.add(vd);
                save.setDisable(false);
            }
            
            System.out.println("detail:"+detail.size());
        }
        selected.set(null);
        total();
    }
    
    private void removefromList(Vente_details p){
        detail.remove(p);
        total();
        if(detail.isEmpty())
            save.setDisable(true);
    }
    private class ActionFactory extends TableCell<Vente_details, Vente_details>{

        private Button delete;
        @Override
        protected void updateItem(Vente_details item, boolean empty) {
            //super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
            if(!empty && item!=null){
            delete=new Button("X");
            
            delete.setOnAction((action)->remove(item));
            HBox box=new HBox();
            box.getChildren().addAll(delete);
            setGraphic(box);
            }else
                setGraphic(null);
        }
        
        private void remove(Vente_details item){
            removefromList(item);
        }
    }
    
    private void total(){
        double total=0;
        int nbre=0;
        for(Vente_details vd:detail){
            nbre+=vd.getQty();
            total+=vd.tot().get();
        }
        double remise=reduction.getText().trim().isEmpty()?0:Double.parseDouble(reduction.getText().trim())*total/100;
        total-=remise;
        mtot.setText(String.valueOf(total));
        qt.setText(String.valueOf(nbre));
    }

    private String generateCode(){
        String codeV=String.valueOf(new Date().getTime());
        return codeV;
    }
    
    @Override
    protected void init(){
        selected=new SimpleObjectProperty<>();
        selected.addListener((ObservableValue<? extends Livres> observable, Livres oldValue, Livres n) -> {
            fillInfos(n);
        });
        created.setEditable(false);
        created.valueProperty().bind(new SimpleObjectProperty<>(LocalDate.now()));
        created.setDisable(true);
        article.setCellValueFactory((data)->data.getValue().livreProperty());
        desc.setCellValueFactory((data)->data.getValue().getLivre().descriptionProperty());
        pu.setCellValueFactory((data)->data.getValue().getLivre().prixProperty());
        quantity.setCellValueFactory((data)->data.getValue().qtyProperty());
        quantity.cellValueFactoryProperty().addListener((o,a,n)->{
            System.out.println("total:"+n);
        });
        //mt.cellValueFactoryProperty().bind(new ReadOnlyObjectWrapper<>((data)->new SimpleObjectProperty<>(tot)));
        mt.setCellValueFactory((data)->data.getValue().tot());
        act.setCellValueFactory((data)->new ReadOnlyObjectWrapper<>(data.getValue()));
        act.setCellFactory((data)->new ActionFactory());
//        
        r.setCellValueFactory((data)-> {
            double remis=data.getValue().getDiscount()*100;
            remis/=data.getValue().getPrix()*data.getValue().getQty();
                    
            return new ReadOnlyObjectWrapper<>(remis);
                });
        mtr.setCellValueFactory((data)-> data.getValue().discountProperty());
        
        r.setCellFactory(TextFieldTableCell.<Vente_details,Double>forTableColumn(new StringConverter<Double>() {

            @Override
            public String toString(Double object) {
                System.out.println("ffff:"+object);
                return String.valueOf(object);
            }

            @Override
            public Double fromString(String string) {
                System.out.println("ff:"+string);
                return Double.parseDouble(string);
            }
            })
        );
        r.setOnEditCommit(e -> {
            Vente_details c=e.getRowValue();
            tot=c.getQty()*c.getPrix();
            c.setDiscount(tot*e.getNewValue()/100);
            table.refresh();
            total();
        });
       
        quantity.setCellFactory(TextFieldTableCell.<Vente_details,Integer>forTableColumn(new StringConverter<Integer>() {

            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
            })
        );
        quantity.setOnEditCommit(e -> {
            Vente_details c=e.getRowValue();
            c.setQty(e.getNewValue());
            table.refresh();
            total();
        });
        table.itemsProperty().bind(datalist);
        code.setText(generateCode());
        code.setEditable(false);
        //created.setValue(LocalDate.now());
        qt.setText("0");
        mtot.setText("0");
        selected.set(null);
        li=0;i=0;
        tot=0;
        detail.clear();
        save.setDisable(true);
    }
    @FXML
    private Button save;

    @FXML
    void onNew(ActionEvent event) {
        init();
    }

    @FXML
    void onSave(ActionEvent event) {
        Ventes v=new Ventes();
        v.setCode(code.getText());
        v.setCreated(created.getValue());
        v.setClient(clientSelected);
        v.setOccasion("Vente au comptoir");
        v.setDetails(detail);
        v.setDiscount(reduction.getText().trim().isEmpty()?0:Double.parseDouble(reduction.getText().trim()));
        v.setType(credit.isSelected()?"credit":"cash");
        v=AppServices.SaveVente(v);
        if(v!=null){
            Alert.success(primarystage, "vente enregistre avec succes", null);
            ventes.add(v);
        }
    }
    
    @FXML
    void close(ActionEvent event) {
        ((Stage)table.getScene().getWindow()).close();
    }

}
