package com.daviapps.dcode.ddb.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTableData implements ColumnIndexListener {
    // Data variables
    public List<DDBTableColumn> columns;
    public List<DDBTableRow> rows;
    
    // Constructors
    public DDBTableData(){
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }
    public DDBTableData(String columns){
        this(columns.split(", "));
    }
    public DDBTableData(String[] columns){
        this();
        for(String column : columns)
            this.columns.add(new DDBTableColumn(column));
        
        //for(DDBTableColumn column : this.columns)
        //    System.out.println(column.getName());
    }
    public DDBTableData(List<DDBTableColumn> columns){
        this.rows = new ArrayList<>();
        this.columns = columns;
    }
    
    // Content function
    public void clear(){
        this.columns.clear();
        this.rows.clear();
    }

    // Interface methods
    @Override
    public int getColumnIndex(String columnName) {
        for (int i = 0; i < columns.size(); i++){
            //System.out.println(columns.get(i).getName() + " - " + columnName);
            if(columns.get(i).getName().equals(columnName))
                return i;
        }
        return -1;
    }
    
    // String Methods

    @Override
    public String toString() {
        String str = "";
        for(DDBTableColumn col : this.columns)
            str += col.getName() + '\t';
        
        return str;
    }
}
