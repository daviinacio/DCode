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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        if(file.exists())
            this.read();
    } 
    public DDBTableFile(String fileDir){
        this(fileDir, 0);
    }  
    public DDBTableFile(File file){
        this(file, 0);
    }
    
    // Getter
    public String getTableName(){
        return file.getName().split(".ddbt")[0];
    }
    
    // File reader and writer
    protected void read(){
        data.clear();
        
        try {
            FileInputStream fileInputStream = new FileInputStream(this.file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            //StringBuffer stringBuffer = new StringBuffer();
            
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
            System.err.println("DDBTableFile.Read.Error: File not found");
        } catch (IOException e) {
            System.err.println("DDBTableFile.Read.Error: " + e.getMessage());
        }
    }
    
    public void write(){
        StringBuilder stringBuffer = new StringBuilder();
        //String content = "";
        
        head.setColumns(data.columns);
        
        stringBuffer.append(head.getAll());
        //content = head.getAll();
        
        //System.out.println(data.rows.get(0).getAll(rowSep));
        
        stringBuffer.append(System.getProperty("line.separator"));
        //content += System.getProperty("line.separator");
        
        for(DDBTableRow row : data.rows){
            stringBuffer.append(row.getAll(rowSep));
            stringBuffer.append(System.getProperty("line.separator"));
            //content += row.getAll(rowSep);
            //content += System.getProperty("line.separator");
        }
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(stringBuffer.toString().getBytes());
            //fileOutputStream.write(content.getBytes());
        } catch (FileNotFoundException e) {
            System.err.println("DDBTableFile.Write.Error: File not found");
        } catch (IOException e) {
            System.err.println("DDBTableFile.Write.Error: " + e.getMessage());
        }
    }
    
    // File create
    /*public void createIfNotExists(String columns){
        createIfNotExists(columns.split(", "));
    }
    
    public void createIfNotExists(String [] columns){
        for(String column : columns)
            this.data.columns.add(new DDBTableColumn(column));
    }*/
    
    public boolean exists(){
        return file.exists();
    }
    
    public void createIfNotExists(DDBTableColumn [] columns){
        this.createIfNotExists(new ArrayList<>(Arrays.asList(columns)));
    }
    
    public void createIfNotExists(List<DDBTableColumn> columns){
        if(!file.exists()){
            try { 
                file.createNewFile(); 
                this.data = new DDBTableData(columns);
                write();
                
                System.out.println("DDBTableFile.Create.Success: table file created on '"
                        + file.getPath() + "'");
                
            } catch (IOException e) {
                System.err.println("DDBTableFile.Create.Error: error on create table file.");
                //Logger.getLogger(DDBTableFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // SQL functions
    public int count(String columns, String where){
        return select(columns, where, null).rows.size();
    }
    
    /////// Update when implements order ///////
    public DDBTableItem max(String columnStr, String where){
        //DDBTableData resultSelect = select(columnStr, where, null);
        //DDBTableColumn column = resultSelect.columns.get(0);
        DDBTableData resultSelect = select("*", where, null);
        DDBTableColumn column = resultSelect.columns.get(resultSelect.getColumnIndex(columnStr));
        DDBTableItem result = DDBTableItem.NULL;
        
        if(column.getType().isOfType(ColumnTypes.INTEGER))        result = new DDBTableItem(0);
        else if(column.getType().isOfType(ColumnTypes.FLOAT))     result = new DDBTableItem(0);
        else if(column.getType().isOfType(ColumnTypes.DOUBLE))    result = new DDBTableItem(0);
        else if(column.getType().isOfType(ColumnTypes.CHARACTER)) result = new DDBTableItem(Character.MIN_VALUE);
        else if(column.getType().isOfType(ColumnTypes.STRING))    result = new DDBTableItem("a");
        
        for(DDBTableRow row : resultSelect.rows){
            if(row.get(columnStr).isNull()) continue;
            
            else if(column.getType().isOfType(ColumnTypes.INTEGER)){
                if(row.get(columnStr).toInteger() > result.toInteger())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.FLOAT)){
                if(row.get(columnStr).toFloat() > result.toFloat())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.DOUBLE)){
                if(row.get(columnStr).toDouble() > result.toDouble())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.CHARACTER)){
                if(row.get(columnStr).toChar() > result.toChar())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.STRING)){
                if(row.get(columnStr).compareTo(result) > 0)
                    result = row.get(columnStr);
            }
        }
        
        return resultSelect.rows.size() > 0 ? result : DDBTableItem.NULL;
        
        //return result;
        
        //return select("*", where, columnStr + " DESC").rows.get(0).get(0);
    }
    
    /////// Update when implements order ///////
    public DDBTableItem min(String columnStr, String where){
        //DDBTableData resultSelect = select(columnStr, where, null);
        //DDBTableColumn column = resultSelect.columns.get(0);
        DDBTableData resultSelect = select("*", where, null);
        DDBTableColumn column = resultSelect.columns.get(resultSelect.getColumnIndex(columnStr));
        DDBTableItem result = DDBTableItem.NULL;
        
        if(column.getType().isOfType(ColumnTypes.INTEGER))        result = new DDBTableItem(Integer.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.FLOAT))     result = new DDBTableItem(Float.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.DOUBLE))    result = new DDBTableItem(Double.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.CHARACTER)) result = new DDBTableItem(Character.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.STRING))    result = new DDBTableItem("z");
        
        //System.out.println("Column Type: " + column.getType().name());
        
        for(DDBTableRow row : resultSelect.rows){
            if(row.get(columnStr).isNull()) continue;
            
            else if(column.getType().isOfType(ColumnTypes.INTEGER)){
                if(row.get(columnStr).toInteger() < result.toInteger())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.FLOAT)){
                if(row.get(columnStr).toFloat() < result.toFloat())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.DOUBLE)){
                if(row.get(columnStr).toDouble() < result.toDouble())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.CHARACTER)){
                if(row.get(columnStr).toChar() < result.toChar())
                    result = row.get(columnStr);
                
            } else if(column.getType().isOfType(ColumnTypes.STRING)){
                if(row.get(columnStr).compareTo(result) < 0)
                    result = row.get(columnStr);
            }
        }
        
        return resultSelect.rows.size() > 0 ? result : DDBTableItem.NULL;
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
            
            for (int i = 0; i < columns.length; i++)
                columns[i] = columns[i].split("@")[0];
             
            for (int i = 0; i < data.columns.size(); i++) {
                boolean insertNull = true;
                
                for (int j = 0; j < columns.length; j++)
                    if(data.columns.get(i).getName().equals(columns[j])){
                        DDBTableItem item = new DDBTableItem(values[j]);
                        
                        //System.out.println(item);
                        
                        if(!item.isNumber()){
                            if( data.columns.get(i).getType().isOfType(ColumnTypes.INTEGER) ||
                                data.columns.get(i).getType().isOfType(ColumnTypes.DOUBLE) ||
                                data.columns.get(i).getType().isOfType(ColumnTypes.FLOAT) ){
                                System.err.println("DDBTableInsert.Insert.Error: value [" + item + "] is not " + data.columns.get(i).getType().name());
                                return;
                            } else{
                                row.add(item);  insertNull = false; 
                            }
                        } else {
                            if(data.columns.get(i).getType().isOfType(ColumnTypes.INTEGER)){
                                row.add("" + item.toInteger());  insertNull = false; }
                            else if(data.columns.get(i).getType().isOfType(ColumnTypes.DOUBLE)){
                                row.add("" + item.toDouble());  insertNull = false; }
                            else if(data.columns.get(i).getType().isOfType(ColumnTypes.FLOAT)){
                                row.add("" + item.toFloat());  insertNull = false; }
                        }
                        
                        /*if(         data.columns.get(i).getType().isOfType(ColumnTypes.INTEGER) ||
                                    data.columns.get(i).getType().isOfType(ColumnTypes.DOUBLE) ||
                                    data.columns.get(i).getType().isOfType(ColumnTypes.FLOAT) ){
                            if(!item.isNumber()){
                                System.err.println("DDBTableInsert.Insert.Error: value [" + item + "] is not " + data.columns.get(i).getType().name());
                                return;
                            }
                            
                        } else if(  data.columns.get(i).getType().isOfType(ColumnTypes.BOOLEAN)){
                            
                        } else if(  data.columns.get(i).getType().isOfType(ColumnTypes.STRING) ||
                                    data.columns.get(i).getType().isOfType(ColumnTypes.CHARACTER)){
                            
                        } else if(  data.columns.get(i).getType().isOfType(ColumnTypes.DATETIME)){
                            
                        }*/
                        
                        // Insert value
                        //row.add(item);  insertNull = false; 
                    }
                
                DDBTableColumn column = data.columns.get(i);
                
                if(insertNull){
                    if(column.isAllowNull()){
                        if(column.getType().isOfType(ColumnTypes.INTEGER))
                            row.add("" + DDBTableItem.NULL.toInteger());
                        else if(column.getType().isOfType(ColumnTypes.FLOAT))
                            row.add("" + DDBTableItem.NULL.toFloat());
                        else if(column.getType().isOfType(ColumnTypes.DOUBLE))
                            row.add("" + DDBTableItem.NULL.toDouble());
                        else if(column.getType().isOfType(ColumnTypes.CHARACTER))
                            row.add("" + DDBTableItem.NULL.toChar());
                        else if(column.getType().isOfType(ColumnTypes.STRING))
                            row.add(DDBTableItem.NULL);
                    } else {
                        if(column.getIncrement() == 'n'){
                            System.err.println("DDBTableInsert.Insert.Error: Null value on a column that don't allows null value.");
                            return;
                        } else if(column.getIncrement() > 0){
                            if(column.getType().isOfType(ColumnTypes.INTEGER))
                                row.add("" + ((int) max(column.getName(), null).toInteger() + column.getIncrement()));
                            else if(column.getType().isOfType(ColumnTypes.FLOAT))
                                row.add("" + ((int) max(column.getName(), null).toFloat()+ column.getIncrement()));
                            else if(column.getType().isOfType(ColumnTypes.DOUBLE))
                                row.add("" + ((int) max(column.getName(), null).toDouble()+ column.getIncrement()));
                            else if(column.getType().isOfType(ColumnTypes.CHARACTER))
                                row.add("" + ((int) max(column.getName(), null).toChar() + column.getIncrement()));
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
    
    public void update(String columns, String [] values, String where){
        this.update(columns.split(", "), values, where);
    }
    
    public void update(String [] columns, String [] values, String where){
        if(columns.length == values.length){
            DDBTableData toUpdate = select("*", where, null);

            for(DDBTableRow row : toUpdate.rows){
                for(int i = 0; i < columns.length; i++)
                    if(!row.set(columns[i], values[i]))
                        System.err.println("DDBTableFile.Update.Error: row[" + toUpdate.rows.indexOf(row) + "]"
                                + " column [" + columns[i] + "] not founded");
                
                if(toUpdate.rows.size() <= 50)
                    System.out.println("update: " + row);
            }
            
            System.out.println("DDBTableFile.Update.Success: " + toUpdate.rows.size() + " items updated");
            
            write();
        } else
            System.err.println("DDBTableFile.Update.Error: columns length not equals values length");
        
    }
    
    public void delete(String where){ // Or Drop
        DDBTableData toDelete = select("*", where, null);
        for(DDBTableRow row : toDelete.rows){
            data.rows.remove(row);
            if(toDelete.rows.size() <= 50)
                System.out.println("Delete: " + row);
        }
        
        System.out.println("DDBTableFile.Delete.Success: " + toDelete.rows.size() + " items deleted");
        
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
                        case "=": include = data.rows.get(i).get(whereArray[0]).equals(whereArray[2]); break;
                        case "like": include = data.rows.get(i).get(whereArray[0]).contains(whereArray[2]); break;
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
    
    // Column table functions
    public void insertColumn(DDBTableColumn column){
        insertColumn(column, null);
    }
    public void insertColumn(DDBTableColumn column, String defaultValue){
        
    }
}
