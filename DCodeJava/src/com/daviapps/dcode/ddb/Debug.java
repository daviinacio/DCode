package com.daviapps.dcode.ddb;

/**
 * @author Davi
 */
class Debug {
    public static void main(String[] args) {
        DDBTable table = new DDBTable("C:\\Users\\Davi\\Desktop\\ddb.DCode");
        
        System.out.println(table.data.get(0).get(1));
    }
}
