package com.daviapps.dcode.ddb.table;

import com.daviapps.dcode.DCode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTableFile {
    
    // Struct variables
    private static String rowSep = ";";
    
    // File variables
    private File file;
    private int passwd;
    
    // Content variables
    private DDBTableData data;
    private DDBTableHead head;
    
    // Constructors
    public DDBTableFile(String fileDir, int passwd){
        this(new File(fileDir), passwd);
    }
    public DDBTableFile(File file, int passwd){
        data = new DDBTableData();
        head = new DDBTableHead();
        //this.columns = new ArrayList<>();
        //this.rows = new ArrayList<>();
        this.file = file;
        this.passwd = passwd;
        
        read();
    } 
    public DDBTableFile(String fileDir){
        this(fileDir, 0);
    }  
    public DDBTableFile(File file){
        this(file, 0);
    }
    
    // Getter
    /*protected List<DDBTableRow> getRows(){
        return rows;
    }*/
    
    // File reader and setter
    public void read(){
        data.columns.clear();
        data.rows.clear();
        
        try {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            
            for (int i = 0; bufferedReader.ready(); i++) {
                String line = bufferedReader.readLine();
                //System.out.println(String.format("[%s] - %s", i, line));
                
                if(i == 0){ // Struct
                    this.head = new DDBTableHead(line);
                    
                    String [] col = head.columnsStr.split(":");
                    
                    for(String column : col)
                       data.columns.add(new DDBTableColumn(column));
                    
                } else if(i >= 1){ // Rows
                    data.rows.add(new DDBTableRow(data, line, rowSep));
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("DDBTableFile: File not found");
        } catch (IOException e) {
            System.err.println("DDBTableFile: " + e.getMessage());
        }
    }
    
    private void write(){
        String content = "";
        
        head.setColumns(data.columns);
        content = head.getAll();
        
        //System.out.println(data.rows.get(0).getAll(rowSep));
        
        content += System.getProperty("line.separator");
        
        for(DDBTableRow row : data.rows){
            content += row.getAll(rowSep);
            content += System.getProperty("line.separator");
        }
        
        System.out.println(content);
        
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(content.getBytes());
            //fileOutputStream.write(fileText.getBytes());
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error, arquivo não salvo");
        }
    }
    
    // SQL functions
    public int count(String columns, String where){
        return select(columns, where, null).rows.size();
    }
    
    /////// Update when implements order ///////
    public int max(String columnStr, String where){
        DDBTableData resultSelect = select(columnStr, where, null);
        DDBTableColumn column = resultSelect.columns.get(0);
        int result = 0;
        
        //System.out.println(column.getType());
        
        for(DDBTableRow row : resultSelect.rows){
            //System.out.println("Type: " + column.getType());
            if(column.getType() == 'i')
                if(Integer.parseInt(row.get(columnStr)) > result)
                    result = Integer.parseInt(row.get(columnStr));
        }
        
        return result;
    }
    
    // SQL data functions
    public void insert(String columns, String values){
        this.insert(columns, values.split(", "));
    }
    public void insert(String columns, String [] values){
        this.insert(columns.contains("*") ? head.columnsStr.split(":") : columns.split(", "), values);
    }
    public void insert(String [] columns, String [] values){
        //for(String column : columns)
        //    System.out.println(column);
        
        if(columns.length == values.length){
            DDBTableRow row = new DDBTableRow(data);
            //boolean canInsert = true;
             
            for (int i = 0; i < data.columns.size(); i++) {
                boolean insertNull = true;
                
                for (int j = 0; j < columns.length; j++) {
                    if(data.columns.get(i).getName().equals(columns[j].split("@")[0])){
                        row.add(values[j]);
                        insertNull = false;
                    }
                }
                
                DDBTableColumn column = data.columns.get(i);
                
                if(insertNull){
                    /*if(column.isUnique()){
                        if(count("*", column.getName() + " = " + 6) == 0)
                            row.add("" + max(column.getName(), null));
                        else
                            System.err.println("ERROR UNIQUE");
                    }*/
                    if(column.isAllowNull())
                        row.add("NULL");
                    else{
                        if(column.getIncrement() == 'n'){
                            System.err.println("DDBTableInsert.Error Null value on a column that don't allows null value.");
                            return;
                        } else if(column.getIncrement() > 0){
                            if(column.getType() == 'i')
                                row.add("" + ((int) count(column.getName(), null) + (column.getIncrement() - 1)));
                        }
                    }
                } else {
                    if(column.isUnique())
                        if(count("*", column.getName() + " = " + row.get(i)) > 0){
                            System.err.println("DDBTableInsert.Error [" + column.getName() + "]: " + row.get(i) + ", yet exists");
                            return;
                        }
                }
            }
            
            System.out.println("insert: " + row);
            data.rows.add(row);
            write();
        } else
            System.err.println("DDBTableFile: insert not equals column and values length");
        
        
        /*for(String column : columns)
            System.out.println(column);
        for(String value : values)
            System.out.println(value);*/
    }
    
    public void update(String [] columns, String [] values, String where){
        
    }
    
    public void delete(String where){
        
    }
    
    public DDBTableData select(String columns, String where, String order){
        return this.select(columns.contains("*") ? new String[0] : columns.split(", "), where, order);
    }
    
    public DDBTableData select(String [] columns, String where, String order){
        DDBTableData result;
        if(columns.length != 0) {
            result = new DDBTableData();
            for(String column : columns)
                result.columns.add(data.columns.get(data.getColumnIndex(column)));
        } else result = new DDBTableData(data.columns); // To avoids reload
        
        
        //System.out.println(columns.length);
        
        //for(DDBTableColumn column : result.columns)
        //    System.out.println(column.getName());
        
        for(int i = 0; i < data.rows.size(); i++){
            boolean include = true;
            
            if(where != null){
                include = false;
                String [] whereArray = where.toLowerCase().split(" ");
                if(whereArray.length == 3){ // One boolean
                    boolean inverse = whereArray[1].contains("!");
                    switch (whereArray[1].replace("!", "")) {
                        case "=": include = data.rows.get(i).get(whereArray[0]).toLowerCase().equals(whereArray[2]); break;
                        case "like": include = data.rows.get(i).get(whereArray[0]).toLowerCase().contains(whereArray[2]); break;
                    }
                    include ^= inverse;
                }
            }
            
            if(include){
                DDBTableRow row = new DDBTableRow(result);
                for (int j = 0; j < result.columns.size(); j++){
                    //System.out.println(columns[j]);
                    row.add(data.rows.get(i).get(result.columns.get(j).getName()));
                }
                result.rows.add(row);
            }
                //result.add(this.rows.get(i));
        }
        
        return result;
    }
    
    // Utils
    /*private String [] columnsStrToArray(String columns){
        String [] arrColumns = null;
        if(columns.equals("") || columns.contains("*")){
            arrColumns = new String[data.columns.size()];
            for (int i = 0; i < arrColumns.length; i++)
                arrColumns[i] = data.columns.get(i).getName();
        }
        
        return arrColumns == null ? columns.split(", ") : arrColumns;
    }*/
    
    // Interface methods
    /*@Override
    public int getColumnIndex(String columnName) {
        for (int i = 0; i < columns.size(); i++){
            //System.out.println(columns.get(i).getName() + " - " + columnName);
            if(columns.get(i).getName().equals(columnName))
                return i;
        }
        return -1;
    }*/
    
}
