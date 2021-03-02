/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.resources.view;

import com.redevance.controller.CardController;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Padovano
 */
public class Card extends AnchorPane{
    private ViewBehavior behavior;
    private Object controller;
    private String libelle,titre,infos;
    private Image img;
    private final String url="card.fxml";

    public Card(String libelle, String titre, String infos, Image img) {
        this.libelle = libelle;
        this.titre = titre;
        this.infos = infos;
        this.img = img;
        initComponents();
    }

    public Card() {
        this(null,null,null,null);
    }

    private void initComponents(){
        behavior =new ViewBehavior(url);
        CardController cc=(CardController)behavior.getController();
        System.out.println("cc:"+cc);
        cc.setName(titre);
        cc.setValue(infos);
        Parent p=behavior.getparent();
        this.getChildren().add(p);
    }
    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    
}
