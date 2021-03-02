/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import com.redevance.dao.bean.Categories;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class CategoryModel extends ImplModel<Categories>{

    private static CategoryModel model=null;

    public synchronized static CategoryModel getManager() {
        if(model==null)
            model=new CategoryModel();
        return model;
    }

    @Override
    public Categories add(Categories data) {
        requete="insert into categorie (categorie) values(?)";
        connectionSql.Update(requete, 1, data.getCategorie());
        data.setId(connectionSql.getLast());
        return data;
    }

    @Override
    public Categories change(Categories data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Categories ob) {
        requete="delete from categorie where id=?";
        return connectionSql.Update(requete, 0, ob.getId());
    }

    @Override
    public ObservableList<Categories> searchBy(String conditions, boolean withcontain, Object... critere) {
        ObservableList<Categories> categories= FXCollections.observableArrayList();
        requete="select * from categorie";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
                Categories a=new Categories(d);
            categories.add(a);});
        return categories;
    }

    @Override
    public Categories exists(Categories ob) {
        return select("categorie=?",false,ob.getCategorie());
    }
    
}
