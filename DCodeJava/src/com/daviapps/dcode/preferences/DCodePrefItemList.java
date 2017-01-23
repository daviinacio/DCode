package com.daviapps.dcode.preferences;

import com.daviapps.dcode.DCode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi, 11/11/2016
 */

public class DCodePrefItemList {
    protected DCode dcode;
    
    // Private variables
    protected List<DCodePrefItem> list;
    
    // Contructors
    
    public DCodePrefItemList(DCode dcode, String items){
        this(dcode, dcode.unCode(items));
    }
    
    public DCodePrefItemList(DCode dcode, String [] items){
        this(dcode, toItemArray(items));
    }
    
    // Main contructor
    public DCodePrefItemList(DCode dcode, DCodePrefItem [] items){
        list = toList(items);
        this.dcode = dcode;
    }
    
    // Methods
    
    public void addItem(String key, String value){
        System.out.println(key + " added with value: " + value);
        list.add(new DCodePrefItem(key, value));
    }
    
    public boolean removeItem(String key){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getKey().equals(key)){
                list.remove(i);
                System.out.println(key + " removed");
                return true;
            }
        }
        return false;
    }
    
    public DCodePrefItem getItem(String key){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getKey().equals(key)){
                return list.get(i);
            }
        }
        return null;
    }
    
    // The setItem method is on DCodePreferences, becouse uses the getItem();
    
    public boolean contains(String key){
        return (getItem(key) != null);
    }
    
    // Static methods
    
    public static String [] toStringArray(DCodePrefItem[] item){
        String [] _item = new String[item.length];
        for(int i = 0; i < _item.length; i++)
            _item[i] = item[i].toString();
        return _item;
    }
    
    public static String [] toStringArray(List<DCodePrefItem> items){
        return toStringArray(toItemArray(items));
    }
    
    public static DCodePrefItem[] toItemArray(String [] items){
        DCodePrefItem [] _items = new DCodePrefItem[items.length];
        for(int i = 0; i < _items.length; i++)
            _items[i] = new DCodePrefItem(items[i]);
        return _items;
    }
    
    public static List<DCodePrefItem> toList(DCodePrefItem [] items){
        List<DCodePrefItem> list = new ArrayList<>();
        for(int i = 0; i < items.length; i++)
            list.add(items[i]);
        return list;
    }
    
    public static DCodePrefItem [] toItemArray(List<DCodePrefItem> items){
        DCodePrefItem [] out = new DCodePrefItem[items.size()];
        for(int i = 0; i < items.size(); i++)
            out[i] = items.get(i);
        return out;
    }
    
    // Getters and Setters
    
    public List<DCodePrefItem> getList(){
        return list;
    }
    
    // Override methods

    @Override
    public String toString() {
        return dcode.enCode(toStringArray(list));
    }
    
}
