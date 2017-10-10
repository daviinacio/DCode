package com.daviapps.dcode.ddb;

import java.util.List;

/**
 * @author Davi
 */
class Debug {
    public static void main(String[] args) {
        DDBTable table = new DDBTable("C:\\Users\\Davi\\Desktop\\dados.ddb");
        
        List<DDBTableRow> rows = table.loadQuery("select name,age where(name % o)");
        
        for(DDBTableRow row : rows){
            System.out.println(row);
        }
        //System.out.println(table.data.get(0).get(1));
    }
}
