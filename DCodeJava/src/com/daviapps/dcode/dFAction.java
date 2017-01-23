/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daviapps.dcode;

/**
 *
 * @author Davi
 */
public abstract class dFAction {
    private DCodeFile dFile;
    
    // Constructors
    public dFAction(DCodeFile dFile){
        this.dFile = dFile;
        constructAction();
    }
    
    public dFAction(){}
    
    private void constructAction(){
        if(dFile.getStatusKey() == DCodeFile.NOTFOUNDED)
            this.onNotFound();
        else if(dFile.getStatusKey() == DCodeFile.EMPTY)
            this.onEmpty();
        
        if(dFile.getStatusKey() == DCodeFile.ALRIGHT)
            this.onAlright();
    }
    
    // Setters
    public void setDFile(DCodeFile dFile){
        this.dFile = dFile;
        constructAction();
    }
    
    // Methods
    
    public void onNotFound(){
        if(dFile != null){
            dFile.createFile();
        }
    }
    
    public  void onEmpty(){
        if(dFile != null){
            dFile.createBaseFile();
        }
    }
    
    public abstract void onAlright();
}
