/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import com.redevance.dao.bean.Clients;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public class ClientModel extends ImplModel<Clients>{
    private static ClientModel model=null;

    public synchronized static ClientModel getManager() {
        if(model==null)
            model=new ClientModel();
        return model;
    }
    
    @Override
    public Clients add(Clients data) {
        requete="insert into clients(nom,adresse,tel,type,contact) values(?,?,?,?,?)";
        connectionSql.Update(requete, 1, data.getNom(),data.getAdresse(),data.getTel(),data.getType()
                ,data.getContact());
        
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Clients change(Clients data) {
        requete="update client set nom=?, adresse=?, tel=?, type=?, contact=? where id=?";
        connectionSql.Update(requete, 0, data.getNom(),data.getAdresse(),data.getTel(),data.getType()
                ,data.getContact(),data.getId());
        return data;
    }

    @Override
    public boolean remove(Clients ob) {
        requete="delete from client where id=?";
        return connectionSql.Update(requete,0, ob.getId());
    }

    @Override
    public ObservableList<Clients> searchBy(String conditions,boolean encaps, Object... critere) {
        ObservableList<Clients> clients= FXCollections.observableArrayList();
        requete="select * from clients";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
       
        data.stream().forEach(d->{
            Clients client=new Clients(d);
            //System.out.println(client.getGenre());
            clients.add(client);
        });
        return clients;
    }

    @Override
    public Clients exists( Clients ob) {
        return select("nom=? and type=?",false,ob.getNom(),ob.getType());
    }

}
