/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import com.redevance.dao.bean.Depenses;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public class DepenseModel extends ImplModel<Depenses>{
    private static DepenseModel model=null;

    public synchronized static DepenseModel getManager() {
        if(model==null)
            model=new DepenseModel();
        return model;
    }

    @Override
    public Depenses add(Depenses data) {
        requete="insert into depenses(description,montant,contrat_id,created) values(?,?,?,Now())";
        this.connectionSql.Update(requete,1,data.getDescription(),data.getMontant(),data.getContrat().getId());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Depenses change(Depenses data) {
        requete="update depenses set description=?,montant=?,contrat_id=? where id=?";
        this.connectionSql.Update(requete,1,data.getDescription(),data.getMontant(),data.getContrat().getId(),data.getId());
        return data;
    }

    @Override
    public boolean remove(Depenses ob) {
        return connectionSql.Update("delete from depenses where id=?", 0, ob.getId());
    }

    @Override
    public ObservableList<Depenses> searchBy(String conditions, boolean withcontain, Object... critere) {
        ObservableList<Depenses> depenses= FXCollections.observableArrayList();
        requete="select * from depenses";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println(data);
        data.stream().map((d) -> new Depenses(d)).forEachOrdered((dep) -> {
            depenses.add(dep);
        });
        return depenses;
    }

    @Override
    public Depenses exists(Depenses ob) {
        return select("description=? and contrat_id=?", false,ob.getDescription(),ob.getContrat().getId());
    }
    
    
}
