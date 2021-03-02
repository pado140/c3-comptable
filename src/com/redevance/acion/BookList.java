/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.acion;

import com.redevance.dao.bean.Livres;
import com.redevance.services.AppServices;
import javafx.collections.ObservableList;

/**
 *
 * @author Padovano
 */
public class BookList implements Action{
    public ObservableList<Livres> livres;
    public BookList() {
    
    }

    @Override
    public void init() {
        livres=AppServices.bookList();
        System.out.println("load");
    }

}
