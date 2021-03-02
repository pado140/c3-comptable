/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redevance.acion;

import com.redevance.utils.Loader;

/**
 *
 * @author Padovano
 */
public interface Action{
    public void init();
    default void run(){
        System.out.println("loading...");
        Loader l=new Loader(this::init);
//        l.stateProperty().addListener((o,a,n)->{
//            if(l.getState()==n.READY){
//                l.restart();
//                System.out.println("loading...");
//            }
//        });
        l.start();
    }
}
