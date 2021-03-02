/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.acion;

import com.redevance.dao.bean.Livres;
import com.redevance.services.AppServices;

/**
 *
 * @author Padovano
 */
public class SaveBook implements Action{
    private Livres livre;
    public SaveBook(Livres l) {
        livre=l;
    }

    @Override
    public void init() {
        livre=AppServices.Savebook(livre);
    }

    public Livres getLivre() {
        return livre;
    }

    public void setLivre(Livres livre) {
        this.livre = livre;
    }
    
    
}
