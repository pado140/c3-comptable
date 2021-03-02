/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import com.redevance.dao.bean.Vente_details;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class VenteDetailModel extends ImplModel<Vente_details>{
    private static VenteDetailModel model=null;

    public synchronized static VenteDetailModel getManager() {
        if(model==null)
            model=new VenteDetailModel();
        return model;
    }
    
    @Override
    public Vente_details add(Vente_details data) {
        requete="insert into vente_details (livre_id,vente_id,qty,prix,discount) values(?,?,?,?,?)";
        connectionSql.Update(requete, 1, data.getLivre().getId(),data.getVente().getId(),
                data.getQty(),data.getPrix(),data.getDiscount());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Vente_details change(Vente_details data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Vente_details ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Vente_details> searchBy(String conditions, boolean withcontain, Object... critere) {
        ObservableList<Vente_details> details= FXCollections.observableArrayList();
        requete="select * from vente_master";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Vente_details detail=new Vente_details(d);
            //System.out.println(detail.getGenre());
            details.add(detail);
        });
        return details;
    }

    @Override
    public Vente_details exists(Vente_details ob) {
        return select("vente_id=? and livre_id=?",false,ob.getVente().getId(),ob.getLivre().getId());
    }

}
