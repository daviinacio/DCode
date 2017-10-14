package com.daviapps.dcode.ddb;

import com.daviapps.dcode.ddb.table.DDBTableData;
import com.daviapps.dcode.ddb.table.DDBTableFile;
import java.io.Console;
import java.util.List;
import java.util.Scanner;

/**
 * @author Davi
 */
class Debug {
    public static void main(String[] args) {
        long timeMachine = System.currentTimeMillis();
        
        DDBTableFile table = new DDBTableFile("C:\\Users\\Davi\\Desktop\\dados.ddbt");
        
        //boolean var = true;
        //System.out.println(var ^= true);
        
        //table.insert("nome", "Teste2");
        //table.insert("nome, estado", "Davi, MG");
        //System.out.println("-------------------------------");
        
        //table.delete("nome = teste");
        
        //table.update("idade, estado", "18, SP", "estado = null");
        //table.update("peso", "74", "peso = null");
        
        //table.update("idade", "41", "nome = rose");
        
        String query = "";
        
        //query = new Scanner(System.in).nextLine();
        
        //System.out.println(query);
        
        DDBTableData data = 
                table.select("*", query.equals("") ? null : query, "id");                //file.select(new String[]{"id", "name", "age", "peso"}, "id = 0", null);
                
        //System.out.println(data.columns.get(0).getAll());
                
        //System.out.println("Count: " + table.count("*", null));
        //System.out.println("Max: " + table.max("id", null));
        //System.out.println("Min: " + table.min("id", null));
        
        //table.insert("id, nome, peso", "2, Davi, 18");
        //table.insert("nome, peso", "Davi, 18");
        
        System.err.println(data);
        for(com.daviapps.dcode.ddb.table.DDBTableRow row : data.rows){
            System.out.println(row);
        }
        
        /*System.out.println("-------------------------------");
        
        data = 
                table.select("id, nome, idade", null, null);
                //file.select(new String[]{"id", "name", "age", "peso"}, "id = 0", null);
        
        for(com.daviapps.dcode.ddb.table.DDBTableRow row : data.rows){
            System.out.println(row);
        }*/
        
        //file.insert("um, dois", "0, 4");
        
        
        
        
        
        
        
        
        //DDBTable table = new DDBTable("C:\\Users\\Davi\\Desktop\\dados.ddbt");
        
        //List<DDBTableRow> rows = table.loadQuery("select name,age where(id = 2)");
        
        //for(DDBTableRow row : rows){
        //    System.out.println(row);
        //}
        //System.out.println(table.data.get(0).get(1));
        
        System.out.print("\nTempo total: " + ((long) System.currentTimeMillis() - timeMachine) + " ms    ");
    }
}
