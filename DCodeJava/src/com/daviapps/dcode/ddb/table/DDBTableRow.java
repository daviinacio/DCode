package com.daviapps.dcode.ddb.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTableRow {
    // Variables
    private DDBTableColumnIndexListener columnGetter;
    private List<String> contents;
    
    public DDBTableRow(DDBTableColumnIndexListener columnGetter){
        this.contents = new ArrayList<>();
        this.columnGetter = columnGetter;
    }
    public DDBTableRow(DDBTableColumnIndexListener columnGetter, String content, String contSplitter){
        this(columnGetter, content.split(contSplitter));
    }
    
    public DDBTableRow(DDBTableColumnIndexListener columnGetter, String [] content){
        this(columnGetter);
        
        for(String con : content)
            this.add(con);
    }
    
    // Adder
    public void add(String content){
        contents.add(content);
    }
    
    // Getter
    public String get(int i){
        return contents.get(i);
    }
    
    public String get(String columnName, String ifNotFound){
        int index = columnGetter.getColumnIndex(columnName);
        if(index == -1) return ifNotFound;
        else return this.get(index);
    }
    
    public String get(String columnName){
        return get(columnName, null);
    }
    
    protected String getAll(String contSplitter){
        String out = "";
        for (int i = 0; i < contents.size(); i++) {
            out += contents.get(i);
            
            if(i < contents.size() - 1)
                out += contSplitter;
        }
        return out;
    }
    
    // Setter
    protected boolean set(int i, String content){
        try{ contents.set(i, content); return true; } 
        //catch (java.lang.ArrayIndexOutOfBoundsException e){ return false; }
        catch (java.lang.IndexOutOfBoundsException e){ return false; }
    }
    
    protected boolean set(String columnName, String content){
        return set(columnGetter.getColumnIndex(columnName), content);
    }

    @Override
    public String toString() {
        String str = "";
        for(String con : contents)
            str += con + '\t';
        return str;
    }
}
