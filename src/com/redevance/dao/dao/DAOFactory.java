/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.dao;

/**
 *
 * @author Padovano
 */
public class DAOFactory<T> {
    private static ImplModel mod;
    public static ImplModel createModel(DAOName type){
        ImplModel model=null;
    switch(type){
        case category:
            model=CategoryModel.getManager();
        break;
        case auteur:
            model=AuteurModel.getManager();
        break;
        case livre:
            model=LivreModel.getManager();
        break;
        case vente:
            model=VenteModel.getManager();
        break;
        case detailvente:
            model=VenteDetailModel.getManager();
        break;
        case client:
            model=ClientModel.getManager();
        break;
        case user:
            if(model==null)
                model=UserModel.getManager();
            break;
        case role:
            if(model==null)
                model=RolesModel.getManager();
            break;
        case group:
            if(model==null)
                model=GroupsModel.getManager();
            break;
        case contrat:
            model=ContratModel.getManager();
            break;
        case depense:
            model=DepenseModel.getManager();
            break;
        case redevance:
            model=RedevanceModel.getManager();
            break;
//        case transac:
//            if(transactionModel==null){
//                transactionModel=new TransactionModel();
//            }
//            return transactionModel;

    }
    
    return model;
    }
}
