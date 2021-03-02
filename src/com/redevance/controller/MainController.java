package com.redevance.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import com.redevance.Main;
import com.redevance.resources.menu.MenuElements;
import com.redevance.resources.menu.vertical.InitMenu;
import com.redevance.resources.menu.vertical.MenuBlock;
import com.redevance.resources.menu.vertical.MenuItems;
import com.redevance.resources.view.DashBoard;
import com.redevance.resources.view.ViewBehavior;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;

public class MainController extends ControllerManager {

    private InitMenu menu;
    private ObservableList<MenuElements> listmenu;
    private final Accordion menuStyle=new Accordion();
    private VBox menu_block;

    @FXML
    private TextField search_field;

    @FXML
    private Text username;

    @FXML
    private FontAwesomeIconView dropDownLogin;

    @FXML
    private AnchorPane main_panel;

    @FXML
    private AnchorPane navleft;

    @FXML
    private BorderPane mainFrame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        menu_block=new VBox();
        menu_block.setLayoutY(0);
        menu_block.setPrefWidth(200);
        buildMenu();
        navleft.getStylesheets().add(getClass().getResource("/com/redevance/resources/css/style.css").toExternalForm());
        navleft.getChildren().add(menu_block);
    }

    @Override
    protected void init() {
        ViewBehavior vb=new ViewBehavior("dashboard_view.fxml");
        Parent pane=new DashBoard();
        main_panel.getChildren().clear();
        AnchorPane.setLeftAnchor(pane,10.0);
        AnchorPane.setRightAnchor(pane,10.0);
        AnchorPane.setTopAnchor(pane,10.0);
        AnchorPane.setBottomAnchor(pane,10.0);
        main_panel.getChildren().add(pane);
    }

    private void buildMenu(){
        initList();
        listmenu.forEach(m->{
            if(m instanceof MenuBlock){
                ((MenuBlock)m).setParen(menuStyle);
                if(!menu_block.getChildren().contains(menuStyle))
                    menu_block.getChildren().add(menuStyle);
            }else{
                if(m instanceof MenuItems){
                    if(((MenuItems)m).isPin()){
                        AnchorPane.setBottomAnchor((MenuItems)m, 2.0);
                        AnchorPane.setLeftAnchor((MenuItems)m, 5.0);
                        navleft.getChildren().add((MenuItems)m);
                    }else
                        menu_block.getChildren().add((MenuItems)m);
                }
            }
        });
    }

    private void initList() {
        menu=new InitMenu(Main.users);
        menu.setParent(main_panel);
        listmenu=menu.loadMenu();
    }
}
