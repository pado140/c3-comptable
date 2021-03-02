package com.redevance.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.redevance.dao.bean.Auteurs;

import java.util.Map;

public class AuteurModel extends ImplModel<Auteurs> {
    private static AuteurModel model=null;

    public synchronized static AuteurModel getManager() {
        if(model==null)
            model=new AuteurModel();
        return model;
    }

    @Override
    public Auteurs add(Auteurs data) {
        requete="insert into auteur(nom,prenom,telephone,email) values(?,?,?,?)";
        this.connectionSql.Update(requete,1,data.getNom(),data.getPrenom(),data.getTelephone(),data.getEmail());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Auteurs change(Auteurs data) {
        requete="update auteur set nom=?,prenom=?,telephone=?,email=? where id=?";
        this.connectionSql.Update(requete,0,data.getNom(),data.getPrenom(),data.getTelephone(),data.getEmail(),data.getId());
        return data;
    }

    @Override
    public boolean remove(Auteurs ob) {
        return false;
    }

    @Override
    public ObservableList<Auteurs> searchBy(String conditions,boolean encaps, Object... critere) {
        ObservableList<Auteurs> auteurs= FXCollections.observableArrayList();
        requete="select * from Auteur";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.stream().forEach(d->{
                Auteurs a=new Auteurs(d);
//                if(a.getImage()==null)
//                    a.setPathimage(AppUtils.PATH_RES+"\\default.png");
            auteurs.add(a);});
        return auteurs;
    }

    @Override
    public Auteurs exists(Auteurs ob) {
        return select("nom=? and prenom=?",false,ob.getNom(),ob.getPrenom());
    }

}
