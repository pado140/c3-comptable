package com.redevance.dao.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.redevance.dao.bean.Transaction;
import com.redevance.dao.bean.Users;

import java.util.Map;

public class TransactionModel extends ImplModel<Transaction> {
    private static TransactionModel model=null;

    public synchronized static TransactionModel getManager() {
        if(model==null)
            model=new TransactionModel();
        return model;
    }
    
    @Override
    public Transaction add(Transaction data) {
        String requete="insert into historicite(users_id,action,doc_id,res_id";
        return data;
    }

    @Override
    public Transaction change(Transaction data) {
        return null;
    }

    @Override
    public boolean remove(Transaction ob) {
        return false;
    }

    @Override
    public ObservableList<Transaction> searchBy(String conditions,boolean encaps, Object... critere) {
        requete="select id,users_id,created,action from historicite";
        ObservableList<Transaction> transactions= FXCollections.observableArrayList();
        if(conditions!=null && !conditions.trim().isEmpty())
            requete += " where "+conditions;

        ObservableList<Map<String,Object>> data=this.connectionSql.selectlist(requete,critere);
        System.out.println();
        data.parallelStream().forEach(d->{
            Transaction transac=new Transaction(d);
            if(encaps)
                transac.setUsers((Users)DAOFactory.createModel(DAOName.user).selectById(transac.getUsers().getId()));
            transactions.add(transac);
        });
        return transactions;
    }

    @Override
    public Transaction exists(Transaction ob) {
        return null;
    }
}
