/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.services;

import com.redevance.dao.bean.Auteurs;
import com.redevance.dao.bean.Categories;
import com.redevance.dao.bean.Clients;
import com.redevance.dao.bean.Contrats;
import com.redevance.dao.bean.Depenses;
import com.redevance.dao.bean.Livres;
import com.redevance.dao.bean.Vente_details;
import com.redevance.dao.bean.Ventes;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class AppServices {
    public static ObservableList<Livres> bookList(){
        return DAOFactory.createModel(DAOName.livre).selectAll();
    }
    
    public static Livres Savebook(Livres l){
        l=(Livres)DAOFactory.createModel(DAOName.livre).beforeSave(l);
        l=(Livres)DAOFactory.createModel(DAOName.livre).save(l);
        l=(Livres)DAOFactory.createModel(DAOName.livre).afterSave(l);
        return l;
    }
    
    public static Contrats SaveContrat(Contrats ct,ObservableList<Depenses> exclude){
        ct=(Contrats)DAOFactory.createModel(DAOName.contrat).beforeSave(ct);
        ct=(Contrats)DAOFactory.createModel(DAOName.contrat).save(ct);
            if(exclude!=null)
                for(Depenses d:exclude){
                    DAOFactory.createModel(DAOName.depense).delete(d);
                }
            for(Depenses d:ct.getDepenses()){
                DAOFactory.createModel(DAOName.depense).save(d);
            }
        ct=(Contrats)DAOFactory.createModel(DAOName.contrat).afterSave(ct);
        return ct;
    }
    
    public static ObservableList<Categories> categoryList(){
        return DAOFactory.createModel(DAOName.category).selectAll();
    }
    
    public static ObservableList<Auteurs> auteurList(){
        return DAOFactory.createModel(DAOName.auteur).selectAll();
    }
    
    public static ObservableList<Clients> clientList(){
        return DAOFactory.createModel(DAOName.client).selectAll();
    }
    
    public static ObservableList<Ventes> venteList(){
        return DAOFactory.createModel(DAOName.vente).selectAll();
    }
    public static ObservableList<Vente_details> detailventeList(){
        return DAOFactory.createModel(DAOName.detailvente).selectAll();
    }
    
    public static ObservableList<Vente_details> detailventebybook(Livres l){
        return DAOFactory.createModel(DAOName.detailvente).search("livre_id=?",false,l.getId());
    }
    
    public static Ventes SaveVente(Ventes v){
        v=(Ventes)DAOFactory.createModel(DAOName.vente).beforeSave(v);
        v=(Ventes)DAOFactory.createModel(DAOName.vente).save(v);
        for(Vente_details vd:v.getDetails()){
            vd.setVente(v);
            DAOFactory.createModel(DAOName.detailvente).save(vd);
        }
        v=(Ventes)DAOFactory.createModel(DAOName.vente).afterSave(v);
        return v;
    }
    
    public static Depenses SaveDepense(Depenses dep){
        dep=(Depenses)DAOFactory.createModel(DAOName.depense).beforeSave(dep);
        dep=(Depenses)DAOFactory.createModel(DAOName.depense).save(dep);
        dep=(Depenses)DAOFactory.createModel(DAOName.depense).afterSave(dep);
        return dep;
    }
}
