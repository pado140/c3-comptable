/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.redevance.dao.bean.Groups;
import java.util.Map;

/**
 *
 * @author Padovano
 */
public class GroupsModel extends ImplModel<Groups>{
    private static GroupsModel model=null;

    public synchronized static GroupsModel getManager() {
        if(model==null)
            model=new GroupsModel();
        return model;
    }
    
    @Override
    public Groups add(Groups objet) {
        requete="insert into `groups`(name,description) values(?,?)";
        
        this.connectionSql.Update(requete, 1, objet.getName(),objet.getDescription());
        objet.setId(this.connectionSql.getLast());
        return objet;
    }

    @Override
    public boolean remove(Groups Objet) {
        return connectionSql.Update("delete from `groups` where id=?", 0, Objet.getId());
    }

    @Override
    public ObservableList<Groups> searchBy(String conditions,boolean encaps, Object... critere)  {
        ObservableList<Groups> group= FXCollections.observableArrayList();
        requete="select * from `groups`";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Groups g=new Groups(d);
            if(encaps)
                g.setRoles(((RolesModel)DAOFactory.createModel(DAOName.role)).load("groups_id=?",g.getId()));
            group.add(g);});
        return group;
    }

    @Override
    public Groups exists(Groups ob) {
        return select("name=?",false,ob.getName());
    }

    @Override
    public Groups change(Groups nouveau) {
        requete="update `groups` set name=?,description=? where id=?";
        this.connectionSql.Update(requete, 1, nouveau.getName(),nouveau.getDescription(),nouveau.getId());
        return nouveau;
    }

}
