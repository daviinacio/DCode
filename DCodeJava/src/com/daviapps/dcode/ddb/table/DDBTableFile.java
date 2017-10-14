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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
        
        //System.out.println(content);
        
        
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
    
    public int min(String columnStr, String where){
        DDBTableData resultSelect = select(columnStr, where, null);
        DDBTableColumn column = resultSelect.columns.get(0);
        int result = Integer.MAX_VALUE;
        
        //System.out.println(column.getType());
        
        for(DDBTableRow row : resultSelect.rows){
            //System.out.println("Type: " + column.getType());
            if(column.getType() == 'i')
                if(Integer.parseInt(row.get(columnStr)) < result)
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
        if(columns.length == values.length){
            DDBTableRow row = new DDBTableRow(data);
             
            for (int i = 0; i < data.columns.size(); i++) {
                boolean insertNull = true;
                
                for (int j = 0; j < columns.length; j++)
                    if(data.columns.get(i).getName().equals(columns[j].split("@")[0])){
                        row.add(values[j]); insertNull = false; }
                
                DDBTableColumn column = data.columns.get(i);
                
                if(insertNull){
                    if(column.isAllowNull())
                        row.add("NULL");
                    else{
                        if(column.getIncrement() == 'n'){
                            System.err.println("DDBTableInsert.Insert.Error: Null value on a column that don't allows null value.");
                            return;
                        } else if(column.getIncrement() > 0){
                            if(column.getType() == 'i')
                                row.add("" + ((int) max(column.getName(), null) + column.getIncrement()));
                        }
                    }
                } else {
                    if(column.isUnique())
                        if(count("*", column.getName() + " = " + row.get(i)) > 0){
                            System.err.println("DDBTableInsert.Insert.Error: [" + column.getName() + "]: " + row.get(i) + ", yet exists");
                            return;
                        }
                }
            }
            
            System.out.println("insert: " + row);
            data.rows.add(row);
            write();
        } else
            System.err.println("DDBTableFile.Insert.Error: insert not equals column and values length");
        
        
        /*for(String column : columns)
            System.out.println(column);
        for(String value : values)
            System.out.println(value);*/
    }
    
    public void update(String columns, String values, String where){
        this.update(columns.split(", "), values.split(", "), where);
    }
    
    public void update(String [] columns, String [] values, String where){
        if(columns.length == values.length){
            DDBTableData toUpdate = select("*", where, null);

            for(DDBTableRow row : toUpdate.rows){
                for(int i = 0; i < columns.length; i++)
                    if(!row.set(columns[i], values[i]))
                        System.err.println("DDBTableFile.Update.Error: row[" + toUpdate.rows.indexOf(row) + "]"
                                + " column [" + columns[i] + "] not founded");
            }
            
            write();
        } else
            System.err.println("DDBTableFile.Update.Error: columns length not equals values length");
        
    }
    
    public void delete(String where){ // Or Drop
        DDBTableData toDelete = select("*", where, null);
        for(DDBTableRow row : toDelete.rows)
            data.rows.remove(row);
        
        write();
    }
    
    public DDBTableData select(String columns, String where, String order){
        return this.select(columns.contains("*") ? new String[0] : columns.split(", "), where, order);
    }
    
    public DDBTableData select(String [] columns, String where, String order){
        DDBTableData result = null;
        
        // Columns
        if(columns.length != 0) {
            try{ result = new DDBTableData();
                for(String column : columns)
                    result.columns.add(data.columns.get(data.getColumnIndex(column)));
            } catch(java.lang.ArrayIndexOutOfBoundsException e){
                if(columns.length == 1 && columns[0].equals(""))
                    System.err.println("DDBTableFile.Select.Error: columns not defineds");
                else if(columns.length > 0)
                    System.err.println("DDBTableFile.Select.Error: column [" + columns[result.columns.size()] + "] not founded");
                //return result;
            }
        } else result = new DDBTableData(data.columns); // To avoids reload
        
        if(result.columns.isEmpty())
            return new DDBTableData();
        
        // Where        
        for(int i = 0; i < data.rows.size(); i++){
            boolean include = true;
            
            if(where != null && !where.equals("")){
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
                DDBTableRow row = null;
                
                if(!result.columns.equals(data.columns)){
                    row = new DDBTableRow(result);
                    for (int j = 0; j < result.columns.size(); j++){
                        //System.out.println(columns[j]);
                        row.add(data.rows.get(i).get(result.columns.get(j).getName()));
                    }
                } else
                    row = data.rows.get(i);
                
                result.rows.add(row);
            }
        }
        
        // Sort
        if(order != null && order != ""){
            for(String sort : order.split(", ")){
                if(sort.equals("")){
                    System.err.println("DDBTableFile.Select.Sort.Error: query order string error");
                    continue;
                }

                String [] _sort = sort.split(" ");
                final int column = result.getColumnIndex(_sort[0]);
                boolean ASC_DESC = true;
                
                if(column == -1){
                    System.err.println("DDBTableFile.Select.Sort.Error: column[" + _sort[0] + "]"
                            + " not selected");
                    continue;
                }

                //System.out.println(column);

                if(_sort.length >= 2)
                    ASC_DESC = _sort[1].equals("ASC"); 

                //System.out.println(ASC_DESC);

                Comparator com = new Comparator<DDBTableRow>(){
                    @Override
                    public int compare(DDBTableRow t, DDBTableRow t1) {
                        return t.get(column).compareTo(t1.get(column));
                    }
                };

                try{
                    Collections.sort(result.rows, ASC_DESC ? com : com.reversed());
                } catch (java.lang.NullPointerException e){
                    System.err.println("DDBTableFile.Select.Sort.Error: Colummn [" + column + "] not founded");
                }
            }
        }
        
        return result;
    }
}
