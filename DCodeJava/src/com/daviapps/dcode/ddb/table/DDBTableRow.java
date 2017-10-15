package com.daviapps.dcode.ddb.table;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTableRow {
    // Variables
    private ColumnIndexListener columnGetter;
    private List<DDBTableItem> contents;
    
    public DDBTableRow(ColumnIndexListener columnGetter){
        this.contents = new ArrayList<>();
        this.columnGetter = columnGetter;
    }
    public DDBTableRow(ColumnIndexListener columnGetter, String content, String contSplitter){
        this(columnGetter, content.split(contSplitter));
    }
    
    public DDBTableRow(ColumnIndexListener columnGetter, String [] content){
        this(columnGetter);
        
        for(String con : content)
            this.add(con);
    }
    
    // Adder
    public void add(String content){
        contents.add(new DDBTableItem(content));
    }
    
    public void add(DDBTableItem content){
        contents.add(content);
    }
    
    // Getter
    public DDBTableItem get(int i){
        return contents.get(i);
    }
    
    public DDBTableItem get(String columnName, DDBTableItem ifNotFound){
        int index = columnGetter.getColumnIndex(columnName);
        if(index == -1) return ifNotFound;
        else return this.get(index);
    }
    
    public DDBTableItem get(String columnName){
        return get(columnName, null);
    }
    
    protected String getAll(String contSplitter){
        //return Arrays.toString(contents);
        String out = "";
        for (int i = 0; i < contents.size(); i++) {
            out += contents.get(i);
            
            if(i < contents.size() - 1)
                out += contSplitter;
        }
        return out;
    }
    
    // Setter
    protected boolean set(int i, DDBTableItem item){
        return this.set(i, item.toString());
    }
    protected boolean set(int i, String content){
        try{ contents.get(i).set(content); return true; } 
        //catch (java.lang.ArrayIndexOutOfBoundsException e){ return false; }
        catch (java.lang.IndexOutOfBoundsException e){ return false; }
    }
    
    protected boolean set(String columnName, DDBTableItem item){
        return this.set(columnName, item.toString());
    }
    
    protected boolean set(String columnName, String content){
        return set(columnGetter.getColumnIndex(columnName), content);
    }

    @Override
    public String toString() {
        String str = "";
        for(DDBTableItem con : contents)
            str += con.toString() + '\t';
        return str;
    }
}
