package com.daviapps.dcode.ddb.table;

import com.daviapps.dcode.DCode;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTableHead {
    private static DCode dcode = new DCode(DCode.NORMAL);
    // Properties
    protected String tableName, encodeType, columnsStr;
    
    // Constructors
    public DDBTableHead(){}
    public DDBTableHead(String headStr){
        String [] head = dcode.unCode(headStr);
        
        this.encodeType = head[0];
        this.tableName = head[1];
        this.columnsStr = head[2];
    }
    
    // Setter
    public void setColumns(List<DDBTableColumn> columns){
        this.columnsStr = "";
        for (int i = 0; i < columns.size(); i++) {
            columnsStr += columns.get(i).getAll();
            if(i < columns.size() - 1)
                columnsStr += ":";
        }
    }
    
    // Getters
    public String getAll(){
        return dcode.enCode(new String[]{encodeType, tableName, columnsStr});
    }
}
