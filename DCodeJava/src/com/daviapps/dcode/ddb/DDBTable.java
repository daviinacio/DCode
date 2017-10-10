package com.daviapps.dcode.ddb;

import com.daviapps.dcode.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTable implements DDBTableColumnIndexListener{
    // Struct variables
    private static String rowSep = ";";
    private static DCode dcode = new DCode(DCode.NORMAL);
    private static DCode dcode_array = new DCode(DCode.ARRAY);
    
    
    // Variables
    private List<DDBTableColumn> columns;
    private File file;
    
    // File properties
    private String encodeType = "";
    private String tableName = "";
    //private List<String> columns;
    
    public DDBTable(String file){
        this.file = new File(file);
    }
    
    public DDBTable(File file){
        this.file = file;
    }
    
    protected List<DDBTableRow> loadQuery(String queryStr){
        System.out.println("Load query");
        System.out.println("File dir: " + this.file.getAbsolutePath());
        
        List<DDBTableRow> rows = new ArrayList<>();
        //List<DDBTableColumn> columns = new ArrayList();
        List<Integer> columnsIndex = new ArrayList<>();
        
        columns = new ArrayList();
        
        String [] query = queryStr.toLowerCase().split(" ");
        //String FileText = new String();
        //String Message;

        try {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            
            for (int i = 0; bufferedReader.ready(); i++) {
                String line = bufferedReader.readLine();
                //System.out.println(String.format("[%s] - %s", i, line));
                
                
                if(i == 0){ // Struct
                    String [] structs = dcode.unCode(line);
                    this.encodeType = structs[0];
                    this.tableName = structs[1];
                    
                    String [] col = dcode_array.unCode(structs[2]);
                    
                    for(String column : col)
                        columns.add(new DDBTableColumn(column));
                    
                    if(query[0].equals("select")){
                        if(query[1].equals("*")){
                            for (int j = 0; j < columns.size(); j++)
                                columnsIndex.add(j);
                        } else {
                            List<DDBTableColumn> qColumns = new ArrayList<>();
                            String [] queryColumns = query[1].split(",");
                            
                            for (int j = 0; j < queryColumns.length; j++)
                                for (int k = 0; k < columns.size(); k++)
                                    if(columns.get(k).getName().equals(queryColumns[j]))
                                        columnsIndex.add(k);
                        }
                    }
                } else if(i >= 1){ // Rows
                    if(query[0].equals("select")){
                        int where_init = queryStr.indexOf("where("),
                            where_end = queryStr.indexOf(")");
                    
                        String [] where = new String [3];

                        boolean whereWorking = where_init > 0;

                        if(whereWorking)
                            where = queryStr.substring(where_init + 6, where_end).split(" ");


                        if(true){
                            DDBTableRow row = new DDBTableRow();
                            String [] rowColumns = line.split(rowSep);
                            for (int j = 0; j < columnsIndex.size(); j++) {
                                row.add(columns.get(columnsIndex.get(j)), rowColumns[columnsIndex.get(j)]);
                            }

                            if(!whereWorking)
                                rows.add(row);
                            else{
                                if(where[1].equals("=") && rowColumns[getColumnIndex(where[0])].equals(where[2]))
                                    rows.add(row); else
                                if(where[1].equals("%") && rowColumns[getColumnIndex(where[0])].indexOf(where[2]) > -1)
                                    rows.add(row);
                            }   
                        }
                    }
                    
                    //DDBTableRow row = new DDBTableRow();
                    //System.out.println(String.format("[%s] - %s", i, line));
                }
            }
            
            //for (int i = 0; i < columnsIndex.size(); i++) 
            //    System.out.println("Columns: " + columns.get(columnsIndex.get(i)));
            
            //for(DDBTableColumn column : columns)
            //    System.out.println("Columns: " + column);

            /*while((Message = bufferedReader.readLine()) != null){
                    stringBuffer.append(Message+"\n");
            }

            FileText = stringBuffer.toString();*/

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Erro, arquivo não carregado");
        }

        return rows;
    }

    @Override
    public int getColumnIndex(String columnName) {
        int out = -1;
        for (int i = 0; i < columns.size(); i++) {
            if(columns.get(i).getName().equals(columnName))
                return i;
        }
        return out;
    }
    
    
}
