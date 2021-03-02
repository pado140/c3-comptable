 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.redevance.dao.bean.Roles;
import java.util.Map;

/**
 *
 * @author Padovano
 */
public class RolesModel extends ImplModel<Roles>{
    private static RolesModel model=null;

    public synchronized static RolesModel getManager() {
        if(model==null)
            model=new RolesModel();
        return model;
    }

    @Override
    public Roles add(Roles objet) {
        requete="insert into roles(name,abbreviation,permission) values(?,?,?)";
        this.connectionSql.Update(requete, 1, objet.getName(),objet.getAbbreviation(),objet.getPermission().getName());
        objet.setId(this.connectionSql.getLast());
        return objet;
    }

    @Override
    public boolean remove(Roles Objet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Roles> searchBy(String conditions,boolean encaps, Object... critere) {
        ObservableList<Roles> roles=FXCollections.observableArrayList();
        requete="select id,name,abbreviation,permission from roles";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Roles role=new Roles(d);
            if(encaps)
                role.setMenu(listAcces(role));
            roles.add(role);
        });
        return roles;
    }

    @Override
    public Roles exists(Roles ob) {
        return select("name=?",false,ob.getName());
    }

    @Override
    public Roles change(Roles nouveau) {
        requete="update roles set name=?,abbreviation=?,permission=? where id=?";
        this.connectionSql.Update(requete, 1, nouveau.getName(),nouveau.getAbbreviation(),nouveau.getPermission().getName(),nouveau.getId());
        return nouveau;
    }

    public ObservableList<String> listAcces(Roles role) {
        ObservableList<String> roles=FXCollections.observableArrayList();
        requete="select item from role_access where role_id=?";
        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,role.getId());
        for(Map<String,Object> val:data){
            roles.add(val.get("item").toString());
        }
        return roles;
    }

    public ObservableList<Roles> load(String conditions, Object... critere) {
        ObservableList<Roles> roles=FXCollections.observableArrayList();
        requete="select id,name,abbreviation from group_role";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Roles role=new Roles(d);
            role.setMenu(listAcces(role));
            roles.add(role);
        });
        return roles;
    }
}
