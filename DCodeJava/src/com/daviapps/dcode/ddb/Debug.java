package com.daviapps.dcode.ddb;

import com.daviapps.dcode.ddb.table.*;
import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Davi
 */
class Debug {
    public static void main(String[] args) {
        //long timeMachine = System.currentTimeMillis();
        
        //Date date = new Date();
        
        //-Calendar date = Calendar.getInstance();
        
        /*-try {
            date.setTime(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse("01/12/1980 01:30:00"));
            //date.setTime(new Date("21/09/1999 00:00:00"));
            //date.setTime(new Date("21 Sep 1999 00:00:00 GMT"));
            //date.setTime(new Date("21/09/1999 00:00:00 GMT"));
            //date.setTime(new Date("21/09/1999 00:00:00 UTC"));
            //date.setTimeZone(Time);
            //date.
        } catch (ParseException ex) {
            Logger.getLogger(Debug.class.getName()).log(Level.SEVERE, null, ex);
        }-*/
        
        //date.set(Calendar.MINUTE, 30);
        
        //date.setTime(new Date());
        
        //-System.out.println(date.get(Calendar.HOUR));
        
        //-System.out.println(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(date.getTime()));
        
        //-System.out.println(date.getTime().toGMTString());
        //System.out.println(date.getTime().toLocaleString());
        //System.out.println(date.getTime().toGMTString());
        
        
        //DDBTableItem item = new DDBTableItem(DDBTableItem.NULL.toDate());
        
        //System.out.println(item.toString());
        
        
        
        //return;
        
        //System.out.println(date.toString());
        
        
        //DDBTableFile table = new DDBTableFile("C:\\Users\\Davi\\Desktop\\items.ddbt");
        DDBTableFile table = new DDBTableFile("C:\\Users\\Davi\\Desktop\\cadastros.ddbt");
        
        table.createIfNotExists(new DDBTableColumn[]{
            new DDBTableColumn("id",    ColumnTypes.INTEGER,    true, false, true,  1),
            new DDBTableColumn("nome",  ColumnTypes.STRING,     false, true, false, 0),
            new DDBTableColumn("idade", ColumnTypes.INTEGER,    false, true, false, 0),
            new DDBTableColumn("peso",  ColumnTypes.FLOAT,      false, true, false, 0),
            new DDBTableColumn("estado",ColumnTypes.STRING,     false, true, false, 0),
            new DDBTableColumn("date", ColumnTypes.DATETIME,    false, true, false, 0),
        });
        
        //table.insertColumn(new DDBTableColumn("date", ColumnTypes.DATETIME, false, true, false, 0));
        
        //table.insert("nome, idade", "Exemplo, 6");
        
        //table.update("date", "21/09/1999 23:40:20", "nome = exemplo");
        
        //Calendar date = Calendar.getInstance();
        //date.setTime(table.select("date", "nome = exemplo", null).rows.get(0).get("date").toDate());
        
        //System.out.println(date.get(Calendar.DAY_OF_MONTH));
        
        //table.deleteColumn("title");
        //table.deleteColumn("oiuasd");
        
        //System.out.println("Min: " + table.min("idade", "estado = MG"));
        
        //return;
        
        //table.insertColumn(new DDBTableColumn("bool", ColumnTypes.BOOLEAN, false, true, false, 0));
        
        /*DDBTableFile table = 
                new DDBTableFile("C:\\\\Users\\\\Davi\\\\Desktop\\\\items.ddbt", 
                        new DDBTableColumn[]{
                            new DDBTableColumn("id", ColumnTypes.INTEGER, true, false, true, 1),
                            new DDBTableColumn("name", ColumnTypes.STRING, false, true, false, 0),
                            new DDBTableColumn("price", ColumnTypes.DOUBLE, false, true, false, 0),
                            new DDBTableColumn("count", ColumnTypes.DOUBLE, false, true, false, 0)
                        });*/
        
        //table.deleteColumn("velocidade");
        
        //table.insertColumn(new DDBTableColumn("velocidade", ColumnTypes.INTEGER, false, true, true, 1), DDBTableItem.NULL);
        //table.insertColumn(new DDBTableColumn("title", ColumnTypes.STRING, false, true, false, 0), "<p>Esta biblioteca é baseada em Threads, função que não está incluida na biblioteca padrão do Arduino. Por isso é fundamental a instalação da biblioteca <a>ArduinoThread-master</a>.</p>");
        //table.insertColumn(new DDBTableColumn("teste", ColumnTypes.STRING, false, true, false, 0), "<p>Outra biblioteca de terceiros utilizada neste projeto, é a <a>PortExpander_I2C</a>, mas não é nescessario baixar-la, a menos que queira utilizar a classe <a>CI01</a>.</p>");
        //table.insertColumn(new DDBTableColumn("teste2", ColumnTypes.STRING, false, true, false, 0), "<p>Esta biblioteca é baseada em Threads, função que não está incluida na biblioteca padrão do Arduino. Por isso é fundamental a instalação da biblioteca <a>ArduinoThread-master</a>.</p>");
        //table.insertColumn(new DDBTableColumn("oiuasd", ColumnTypes.STRING, false, true, false, 0), "<p>Outra biblioteca de terceiros utilizada neste projeto, é a <a>PortExpander_I2C</a>, mas não é nescessario baixar-la, a menos que queira utilizar a classe <a>CI01</a>.</p>");
        
        //table.update("id", "0", null);
        //table.update("velocidade, nome, idade", "3.0, sdfdsf, 6456", null);
        
        //table.insertColumn(new DDBTableColumn("velocidade", ColumnTypes.INTEGER, true, true, 0), '3');
        
        //System.out.println("Table Name: " + table.getTableName());
        
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
        
        //System.out.println("Tempo de load: " + ((long) System.currentTimeMillis() - timeMachine) + " ms"); 
        //timeMachine = System.currentTimeMillis();
        
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
        //query = "id <= 5";
        //query = "estado = rj";
        
        //query = new Scanner(System.in).nextLine();
        
        //System.out.println(query);
        
        //table.delete("estado = null");
        
        //table.insert("nome, estado, peso", "Davi, rj, 60");
        //table.insert("nome, estado", "Davi, rj");
        
        //for(int i = 0; i < 100; i++)
        //    table.insert("name, price, count", "produto, 1.99, 100");
        
        Scanner input = new Scanner(System.in);
        
        boolean running = true;
        while(running == true){
            switch(input.next()){
                case "insert":
                    String [] insertStr = input.nextLine().substring(1).split("  ");
            
                    if(insertStr.length >= 2)
                        table.insert(insertStr[0], insertStr[1]);
                    else
                        break;
                    break;
                
                case "select":
                    String columns = "*", where = null, order = null;
                    
                    String selectStr = input.nextLine().substring(1);
                    
                    int whereIndex = selectStr.indexOf("where");
                    int orderIndex = selectStr.indexOf("order");
                    int end = selectStr.length();
                    
                    columns = selectStr.substring(0, whereIndex != -1 ? whereIndex - 1 : orderIndex != -1 ? orderIndex - 1 : end);
                    if(whereIndex != -1) where = selectStr.substring(whereIndex + 6, orderIndex != -1 ? orderIndex : end);
                    if(orderIndex != -1) order = selectStr.substring(orderIndex + 6);
                    
                    
                    //System.out.println("Columns: " + columns + "| Where: " + where + "| Order: " + order);
                    
                    //timeMachine = System.currentTimeMillis();
                    
                    DDBTableData data =  table.select(columns, where, order);
                    
                    //System.out.println("Select time: " + ((long) System.currentTimeMillis() - timeMachine) + " ms");
                    
                    System.err.println(data);
                    for(DDBTableRow row : data.rows) System.out.println(row);
                    break;
                    
                case "exit": running = false;
            }
        }
        
        //table.insert("name", "batata");
        
        
        //System.out.println("Count: " + table.count("*", ""));
        
        //DDBTableData data = 
        //        table.select("*", query.equals("") ? null : query, "id");                //file.select(new String[]{"id", "name", "age", "peso"}, "id = 0", null);
                
        //table.update("estado", DDBTableItem.NULL.toString(), "nome = davi");
        //table.update("estado", DDBTableItem.NULL.toString(), "nome = rose");
        
        //System.out.println(table.count("*", null));
        
        //table.update("estado", "RS", "estado = null");
        
        //table.delete("estado = rs");
        
        table.write();
        
        //table.update("estado", "DF", "id = 20");
        
        //System.out.println(data.columns.get(0).getAll());
        
        //String c_column = "peso", c_query = "estado = rj";
        
        //System.out.print("Count: " + table.count("*", c_query));
        //System.out.print("\tMax: "   + table.max(c_column, c_query).toDouble()); // Min: 78ms, Max: 92ms - 11771 items
        //System.out.println("\tMin: "   + table.min(c_column, c_query).toFloat());
                
        //System.out.print("Count: " + table.count("*", null));
        //System.out.print("\tMax: " + table.max("id", null)); // Min: 78ms, Max: 92ms - 11771 items
        //System.out.println("  \tMin: " + table.min("id", null));
        
        //table.insert("id, nome, peso", "2, Davi, 18");
        //table.insert("nome, estado", "Davi, rj");
        
        //System.err.println(data);
        //for(DDBTableRow row : data.rows) System.out.println(row);
        
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
        
        //System.out.print("\nTempo total: " + ((long) System.currentTimeMillis() - timeMachine) + " ms    ");
    }
}
