/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import com.redevance.dao.bean.Contrats;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class ContratModel extends ImplModel<Contrats>{
    private static ContratModel model=null;

    public synchronized static ContratModel getManager() {
        if(model==null)
            model=new ContratModel();
        return model;
    }
    
    @Override
    public Contrats add(Contrats data) {
        requete="insert into contrat(contrat,pourcentageauteur,pourcentageediteur,prix,qty,created,livre_id) values("
                + "?,?,?,?,?,now(),?)";
        this.connectionSql.Update(requete, 1, data.getContrat().name(),data.getAuteur(),data.getEdition(),
                data.getPrix(),data.getQty(),data.getLivre().getId());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Contrats change(Contrats data) {
        return data;
    }

    @Override
    public boolean remove(Contrats ob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Contrats> searchBy(String conditions, boolean withcontain, Object... critere) {
        ObservableList<Contrats> contrats= FXCollections.observableArrayList();
        requete="select * from contrat_master";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println("data");
        data.stream().forEach(d->{
            Contrats contrat=new Contrats(d);
            if(withcontain){
                contrat.setDepenses(DAOFactory.createModel(DAOName.depense).search("contrat_id=?",false,contrat.getId()));
            }
            contrats.add(contrat);
        });
        return contrats;
    }

    @Override
    public Contrats exists(Contrats ob) {
        return select("livre_id=? and contrat=?",false,ob.getLivre().getId(),ob.getContrat().toString());
    }
    
}
