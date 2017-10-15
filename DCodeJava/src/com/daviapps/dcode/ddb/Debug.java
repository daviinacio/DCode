package com.daviapps.dcode.ddb;

import com.daviapps.dcode.ddb.table.*;
import java.io.Console;
import java.util.List;
import java.util.Scanner;

/**
 * @author Davi
 */
class Debug {
    public static void main(String[] args) {
        long timeMachine = System.currentTimeMillis();
        
        DDBTableFile table = new DDBTableFile("C:\\Users\\Davi\\Desktop\\cadastros.ddbt");
        
        table.createIfNotExists(new DDBTableColumn[]{
            new DDBTableColumn("id", ColumnTypes.INTEGER, false, true, 1),
            new DDBTableColumn("nome", ColumnTypes.STRING, true, false, 'n'),
            new DDBTableColumn("idade", ColumnTypes.INTEGER, true, false, 'n'),
            new DDBTableColumn("peso", ColumnTypes.FLOAT, true, false, 'n'),
            new DDBTableColumn("estado", ColumnTypes.STRING, true, false, 'n')
        });
        
        System.out.println("Table Name: " + table.getTableName());
        
        /*
        
        Eficience test
            14/10/2017 - 1005 items
                Loagind select order id:
                    max: 31ms
                    min: 15ms
                Loagind select without order:
                    max: 15ms
                    min: 0ms
                Loading file and select:
                    max: 187ms
                    med: 125ms
                    min: 78ms
                Loading count:
                    max: 15ms
                    min: 0ms
        
        
        */
        
        //DDBTableItem item = new DDBTableItem("100.5");
        
        //item.set("TExto");
        
        //System.out.println(item.toString());
        //System.out.println(item.toDouble());
        //System.out.println(item.toChar());
        //System.out.println(item.toInteger());
        
        //boolean var = true;
        //System.out.println(var ^= true);
        
        //table.insert("nome", "Teste2");
        //table.insert("nome, estado", "Davi, MG");
        //System.out.println("-------------------------------");
        
        //table.update("estado", "DFd", "estado = df");
        
        System.out.println("Tempo de load: " + ((long) System.currentTimeMillis() - timeMachine) + " ms"); 
        timeMachine = System.currentTimeMillis();
        
        //for(int i = 0; i < 1000; i++) table.insert(new String[]{"nome"}, new String[]{"Teste2"});
        //for(int i = 0; i < 10000; i++) table.insert("nome", "Teste2");
        
        //System.out.println("Inserted");
        
        //table.write();
        
        //table.delete("nome = teste");
        
        //table.delete("peso = null");
        
        //table.update("idade, estado", "18, SP", "estado = null");
        //table.update("peso", "74", "peso = null");
        
        //table.update("idade", "41", "nome = rose");
        
        String query = "";
        //query = "id = 5";
        //query = "estado = rj";
        
        //query = new Scanner(System.in).nextLine();
        
        //System.out.println(query);
        
        //table.delete("estado = null");
        
        //table.insert("nome, estado, peso", "Davi, rj, 60");
        //table.insert("nome, estado", "Davi, rj");
        
        DDBTableData data = 
                table.select("*", query.equals("") ? null : query, "id");                //file.select(new String[]{"id", "name", "age", "peso"}, "id = 0", null);
                
        //table.update("estado", DDBTableItem.NULL.toString(), "nome = davi");
        //table.update("estado", DDBTableItem.NULL.toString(), "nome = rose");
        
        //System.out.println(table.count("*", null));
        
        //table.update("estado", "RS", "estado = null");
        
        //table.delete("estado = rs");
        
        //table.write();
        
        //table.update("estado", "DF", "id = 20");
        
        //System.out.println(data.columns.get(0).getAll());
        
        String c_column = "peso", c_query = "estado = rj";
        
        System.out.print("Count: " + table.count("*", c_query));
        System.out.print("\tMax: "   + table.max(c_column, c_query).toDouble()); // Min: 78ms, Max: 92ms - 11771 items
        System.out.println("\tMin: "   + table.min(c_column, c_query).toFloat());
                
        System.out.print("Count: " + table.count("*", null));
        System.out.print("\tMax: " + table.max("id", null)); // Min: 78ms, Max: 92ms - 11771 items
        System.out.println("  \tMin: " + table.min("id", null));
        
        //table.insert("id, nome, peso", "2, Davi, 18");
        //table.insert("nome, estado", "Davi, rj");
        
        System.err.println(data);
        for(DDBTableRow row : data.rows) System.out.println(row);
        
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
