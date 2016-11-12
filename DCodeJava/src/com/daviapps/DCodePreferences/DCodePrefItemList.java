package com.daviapps.DCodePreferences;

import com.daviapps.DCode.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi
 */

public class DCodePrefItemList {
    protected List<DCodePrefItem> list;
    protected DCode dcode;
    
    public DCodePrefItemList(DCode dcode, String items){
        this(dcode, dcode.unCode(items));
    }
    
    public DCodePrefItemList(DCode dcode, String [] items){
        this(dcode, toDCodePrefItemsArray(items));
    }
    
    public DCodePrefItemList(DCode dcode, DCodePrefItem [] items){
        list = toDCodePrefsItemList(items);
        this.dcode = dcode;
    }
    
    // Static methods
    
    public static String [] toStringArray(DCodePrefItem[] item){
        String [] _item = new String[item.length];
        for(int i = 0; i < _item.length; i++){
            _item[i] = item[i].toString();
        }
        return _item;
    }
    
    public static DCodePrefItem[] toDCodePrefItemsArray(String [] items){
        DCodePrefItem [] _items = new DCodePrefItem[items.length];
        for(int i = 0; i < _items.length; i++){
            _items[i] = new DCodePrefItem(items[i]);
        }
        return _items;
    }
    
    public static List<DCodePrefItem> toDCodePrefsItemList(DCodePrefItem [] items){
        List<DCodePrefItem> list = new ArrayList<>();
        for(int i = 0; i < items.length; i++){
            list.add(items[i]);
        }
        return list;
    }
    
    public static DCodePrefItem [] toDCodePrefsItemsArray(List<DCodePrefItem> items){
        DCodePrefItem [] out = new DCodePrefItem[items.size()];
        for(int i = 0; i < items.size(); i++){
            out[i] = items.get(i);
        }
        return out;
    }
    
    // Getters and Setters
    
    /*public boolean setItem(String key, String value){
        if(this.getItem(key) != null){
            return true;
        }
        
        return false;
    }*/
    
    public DCodePrefItem getItem(String key){
        //DCodePrefItem [] listItems = new DCodePrefItem[2];
        /*DCodePrefItem [] listItems = {
            new DCodePrefItem("nome", "Ddas"),
            new DCodePrefItem("idade", "jd")
        };*/
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getKey().equals(key)){
                return list.get(i);
            }
        }
        
        return null;
        //return getItem(key, null);
    }
    
    /*public DCodePrefItem getItem(String key, String ifNotFound){
        return null;
    }*/
    
    public List<DCodePrefItem> getList(){
        return list;
    }
    
    // Override methods

    @Override
    public String toString() {
        return dcode.enCodeI(toStringArray(toDCodePrefsItemsArray(list)));
    }
    
}
