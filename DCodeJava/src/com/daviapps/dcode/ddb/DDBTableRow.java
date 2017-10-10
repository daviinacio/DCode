package com.daviapps.dcode.ddb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTableRow {
    private List<DDBTableColumn> columns;
    private List<String> content;
    
    /*public DDBTableRow(List<Strings> columns){
        this.content = new ArrayList<>();
        this.columns = columns;
    }
    
    public DDBTableRow(String [] columns){
        this.content = new ArrayList<>();
        this.columns = new ArrayList<>();
        for(String column : columns)
            this.columns.add(new DDBTableColumn(column));
    }*/
    
    public DDBTableRow(){
        this.content = new ArrayList<>();
        this.columns = new ArrayList<>();
    }
    
    // Setter
    public void add(DDBTableColumn column, String cont){
        this.columns.add(column);
        this.content.add(cont);
    }
    
    // Getters
    public String getAt(int i){
        return getAt(i, null);
    }
    
    public String getAt(int i, String ifNotFounded){
        if(i < 0 || i >= this.content.size())
            return ifNotFounded;
        else return this.content.get(i);
    }
    
    public String get(String key){
        return get(key, null);
    }
    
    public String get(String key, String ifNotFounded){
        return getAt(this.columns.indexOf(key), ifNotFounded);
    }
    
    
    //private DDBTableColumnIndexListener columnIndexListener;
    
    /*public DDBTableRow(DDBTableColumnIndexListener columnIndexListener){
        this.columnIndexListener = columnIndexListener;
    }*/
    
    /*private List<String> columns;
    
    public DDBTableRow(String [] columns){
        this.columns = new ArrayList<>();
        for(String column : this.columns)
            this.columns.add(column);
    }*/

    @Override
    public String toString() {
        String col = "", cont = "";
        for(DDBTableColumn c : this.columns) col += c.getName() + "; ";
        for(String c : this.content) cont += c + "; ";
        //return String.format("%s\n%s", col, cont);
        return String.format("%s", cont);
    }
}
