/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import com.redevance.dao.bean.Redevance;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public class RedevanceModel extends ImplModel<Redevance>{
    private static RedevanceModel model=null;

    public synchronized static RedevanceModel getManager() {
        if(model==null)
            model=new RedevanceModel();
        return model;
    }
    
    @Override
    public Redevance add(Redevance data) {
        requete="insert into redevance(contrat_id,montant,checkno,dateverser) values(?,?,?,?)";
        this.connectionSql.Update(requete,1,data.getContrat().getId(),data.getMontant(),data.getCheckno(),
                data.getDateverser());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Redevance change(Redevance data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Redevance ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Redevance> searchBy(String conditions, boolean withcontain, Object... critere) {
        ObservableList<Redevance> redevances= FXCollections.observableArrayList();
        requete="select * from Redevance";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println(data);
        data.forEach((d) -> {
            redevances.add(new Redevance(d));
        });
        return redevances;
    }

    @Override
    public Redevance exists(Redevance ob) {
        return select("checkno=?",false,ob.getCheckno());
    }
    
}
