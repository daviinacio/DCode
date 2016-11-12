package com.daviapps.DCodePreferences;

import com.daviapps.DCode.*;
import java.io.File;

/**
 * @author Davi
 */

public class DCodePreferences {
    protected DCodeFile file;
    protected final DCode dcode = new DCode('[', ':', ']');
    protected DCodePrefItemList list;
    
    public DCodePreferences(String file){
        this(new File(file));
    }
    
    public DCodePreferences(File file){
        this(new DCodeFile(file));
    }
    
    public DCodePreferences(DCodeFile file){
        this.file = file;
        if(file.getStatusKey() == DCodeFile.ALRIGHT){
            System.out.println("Alright");
            list = new DCodePrefItemList(dcode, file.getText());
        } else
        if(file.getStatusKey() == DCodeFile.EMPTY){
            System.err.println("Empty");
            file.createBaseFile();
            System.out.println("Base file created");
        } else
        if(file.getStatusKey() == DCodeFile.ERROR){
            System.err.println("Error");
        }
    }
    
    // Loader methods
    
    public void save(){
        file.setText(list.toString());
    }
    
    // Getters and setters
    
    public boolean set(String key, String value){
        if(list.getItem(key) != null){
            list.getItem(key).setValue(value);
            return true;
        }
        return false;
    }
    
    public String get(String key){
        return get(key, null);
    }
    
    public String get(String key, String ifNotFound){
        if(list.getItem(key) != null) // If item did found
            return list.getItem(key).getValue();
        return ifNotFound;
    }
    
}
