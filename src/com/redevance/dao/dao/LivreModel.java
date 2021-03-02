package com.redevance.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.redevance.dao.bean.Livres;

import java.util.Map;

public class LivreModel extends ImplModel<Livres> {
    private static LivreModel model=null;

    public synchronized static LivreModel getManager() {
        if(model==null)
            model=new LivreModel();
        return model;
    }
    
    @Override
    public Livres add(Livres data) {
        requete="insert into livre (isbn,titre,description,anneesortie,imagelivre,categorie_id,qty,prix,auteur_id) values(?,?,?,?,?,?,?,?,?)";
        connectionSql.Update(requete, 1, data.getIsbn(),data.getTitre(),data.getDescription(),data.getAnneesortie(),
                data.getPath(),data.getCategorie().getId(),data.getQty(),data.getPrix(),data.getAuteur().getId());
        data.setId(this.connectionSql.getLast());
        return data;
    }

    @Override
    public Livres change(Livres data) {
        requete="update livre set titre=?, isbn=?, description=?, categorie_id=?, imagelivre=? , prix=? ,qty=?,contrat_id=? where id=?";
        connectionSql.Update(requete, 0, data.getTitre(),data.getIsbn(),data.getDescription(),data.getCategorie().getId(),data.getPath(),
                data.getPrix(),data.getQty(),data.getContrat()!=null?data.getContrat().getId():data.getContrat(),data.getId());
        return data;
    }

    @Override
    public boolean remove(Livres ob) {
        requete="delete from livre where isbn=?";
        return connectionSql.Update(requete,0, ob.getIsbn());
    }

    @Override
    public ObservableList<Livres> searchBy(String conditions,boolean encaps, Object... critere) {
        ObservableList<Livres> livres= FXCollections.observableArrayList();
        requete="select * from livre_master";
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        //System.out.println(data);
        for(Map<String,Object> d:data){
            Livres livre=new Livres(d);
            //System.out.println(livre.getGenre());
            livres.add(livre);
        }
//        data.parallelStream().forEach(d->{
//            Livres livre=new Livres(d);
//            //System.out.println(livre.getGenre());
//            livres.add(livre);
//        });
        return livres;
    }

    @Override
    public Livres exists( Livres ob) {
        return select("titre=? and isbn=?",false,ob.getTitre(),ob.getIsbn());
    }

}
