/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import com.redevance.dao.bean.Ventes;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class VenteModel extends ImplModel<Ventes>{
    private static VenteModel model=null;

    public synchronized static VenteModel getManager() {
        if(model==null)
            model=new VenteModel();
        return model;
    }
    
    @Override
    public Ventes add(Ventes data) {
        requete="insert into vente (code,type,occasion,clients_id,discount,created) values(?,?,?,?,?,?)";
        connectionSql.Update(requete, 1, data.getCode(),data.getType(),data.getOccasion(),data.getClient()!=null?data.getClient().getId():null
                ,data.getDiscount(),data.getDate());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Ventes change(Ventes data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Ventes ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Ventes> searchBy(String conditions, boolean withcontain, Object... critere) {
        ObservableList<Ventes> ventes= FXCollections.observableArrayList();
        requete="select * from vente";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Ventes vente=new Ventes(d);
            //System.out.println(vente.getGenre());
            ventes.add(vente);
        });
        return ventes;
    }

    @Override
    public Ventes exists(Ventes ob) {
        return select("code=?",false,ob.getCode());
    }
    
}
