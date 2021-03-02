package com.redevance.dao.dao;


import com.redevance.dao.bean.Groups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import com.redevance.dao.bean.Users;
import com.redevance.security.Hash;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class UserModel extends ImplModel<Users> {
    private static UserModel model=null;

    public synchronized static UserModel getManager() {
        if(model==null)
            model=new UserModel();
        return model;
    }
    
    @Override
    public Users add(Users data) {
        requete="insert into user(nom,prenom,sexe,adresse,naissance,mail,tel,username,defaultpass,pass,type,groups_id,enable) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,1)";
        this.connectionSql.Update(requete,1,data.getNom(),data.getPrenom(),data.getSexe(),data.getAdresse(),
                data.getNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE),data.getMail(),data.getTel(),data.getUsername(),Hash.crypt("pass"),
                Hash.crypt("pass"),data.getType(),data.getGroups().getId());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Users change(Users data) {
        requete="update user set nom=?,prenom=?,sexe=?,adresse=?,naissance=?,mail=?,tel=?,username=?," +
                "type=?,groups_id=?,pass=?,enable=? where id=?";
        this.connectionSql.Update(requete,0,data.getNom(),data.getPrenom(),data.getSexe(),data.getAdresse(),
                data.getNaissance().format(DateTimeFormatter.ISO_LOCAL_DATE),data.getMail(),data.getTel(),data.getUsername(),
                data.getType(),data.getGroups().getId(),data.getPass(),data.getEnable(),data.getId());
        return data;
    }

    @Override
    public boolean remove(Users ob) {
        return false;
    }

    @Override
    public ObservableList<Users> searchBy(String conditions,boolean encaps, Object... critere) {
        ObservableList<Users> users= FXCollections.observableArrayList();
        requete="select * from user";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Users u=new Users(d);
            if(encaps)
                u.setGroups((Groups)DAOFactory.createModel(DAOName.group).selectById(u.getGroups().getId()));
            users.add(u);
        });
        return users;
    }

    @Override
    public Users exists(Users ob) {
        return select("username=? and pass=?",false,ob.getUsername(),ob.getPass());
    }

}
