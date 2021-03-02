/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.dao.enumeration;

/**
 *
 * @author Padovano
 */
public enum TYPECONTRAT {
    A("Auteur"),
    E("Editeur");
    
    String type;
    private TYPECONTRAT(String val){
        this.type=val;
    }

    @Override
    public String toString() {
        return type;
    }
    
    
}
