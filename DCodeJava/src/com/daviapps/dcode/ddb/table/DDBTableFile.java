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
    private int encrypt;
    
    // Content variables
    private DDBTableData data;
    private DDBTableHead head;
    
    // Constructors
    public DDBTableFile(File file, DDBTableColumn [] columns, int encrypt){
        data = new DDBTableData();
        head = new DDBTableHead();
        //this.columns = new ArrayList<>();
        //this.rows = new ArrayList<>();
        this.file = file;
        this.encrypt = encrypt;
        
        if(file.exists())
            this.read();
        
        if(columns != null)
            this.createIfNotExists(columns);
    }
    public DDBTableFile(File file, DDBTableColumn [] columns){
        this(file, columns, 0);
    }
    public DDBTableFile(String fileDir, DDBTableColumn [] columns, int encrypt){
        this(new File(fileDir), columns, encrypt);
    }
    public DDBTableFile(String fileDir, DDBTableColumn [] columns){
        this(new File(fileDir), columns);
    }
    
    public DDBTableFile(String fileDir, int encrypt){
        this(new File(fileDir), encrypt);
    }
    public DDBTableFile(File file, int encrypt){
        this(file, null, encrypt);
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
        long timeMachine = System.currentTimeMillis();
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
        
        System.out.println("DDBTableFile.Read.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms"); 
    }
    
    public void write(){
        long timeMachine = System.currentTimeMillis();
        //-StringBuilder stringBuffer = new StringBuilder();
        //String content = "";
        
        head.setColumns(data.columns);
        
        //-stringBuffer.append(head.getAll());
        //content = head.getAll();
        
        //System.out.println(data.rows.get(0).getAll(rowSep));
        
        //-stringBuffer.append(System.getProperty("line.separator"));
        //content += System.getProperty("line.separator");
        
        /*-for(DDBTableRow row : data.rows){
            stringBuffer.append(row.getAll(rowSep));
            stringBuffer.append(System.getProperty("line.separator"));
            //content += row.getAll(rowSep);
            //content += System.getProperty("line.separator");
        }-*/
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(head.getAll().getBytes());
            fileOutputStream.write(System.getProperty("line.separator").getBytes());
            for(DDBTableRow row : data.rows){
                fileOutputStream.write(row.getAll(rowSep).getBytes());
                fileOutputStream.write(System.getProperty("line.separator").getBytes());
            }
            
            //-fileOutputStream.write(stringBuffer.toString().getBytes());
            //fileOutputStream.write(content.getBytes());
        } catch (FileNotFoundException e) {
            System.err.println("DDBTableFile.Write.Error: File not found");
        } catch (IOException e) {
            System.err.println("DDBTableFile.Write.Error: " + e.getMessage());
        }
        
        System.out.println("DDBTableFile.Write.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
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
        if(!file.exists() || data.columns.size() == 0){
            try { 
                this.file.createNewFile(); 
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
    
    public void createIfNotExists(){
        if(!file.exists()){
            try { 
                this.file.createNewFile(); 
                this.data = new DDBTableData();
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
    
    public DDBTableItem max(String columnStr, String where){
        long timeMachine = System.currentTimeMillis();
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
            
            if(row.get(columnStr).compareTo(result) > 0)
                result = row.get(columnStr);
        }
        
        System.out.println("DDBTableFile.Max.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
        
        return resultSelect.rows.size() > 0 ? result : DDBTableItem.NULL;
    }
    
    /*/////// Update when implements order ///////
    public DDBTableItem max(String columnStr, String where){
        long timeMachine = System.currentTimeMillis();
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
        
        System.out.println("DDBTableFile.Max.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
        
        return resultSelect.rows.size() > 0 ? result : DDBTableItem.NULL;
        
        //return result;
        
        //return select("*", where, columnStr + " DESC").rows.get(0).get(0);
    }*/
    
    public DDBTableItem min(String columnStr, String where){
        long timeMachine = System.currentTimeMillis();
        DDBTableData resultSelect = select("*", where, null);
        DDBTableColumn column = resultSelect.columns.get(resultSelect.getColumnIndex(columnStr));
        DDBTableItem result = DDBTableItem.NULL;
        
        if(column.getType().isOfType(ColumnTypes.INTEGER))        result = new DDBTableItem(Integer.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.FLOAT))     result = new DDBTableItem(Float.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.DOUBLE))    result = new DDBTableItem(Double.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.CHARACTER)) result = new DDBTableItem(Character.MAX_VALUE);
        else if(column.getType().isOfType(ColumnTypes.STRING))    result = new DDBTableItem("z");
        
        for(DDBTableRow row : resultSelect.rows){
            if(row.get(columnStr).isNull()) continue;
            
            if(row.get(columnStr).compareTo(result) < 0)
                result = row.get(columnStr);
        }
        
        System.out.println("DDBTableFile.Min.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
        
        return resultSelect.rows.size() > 0 ? result : DDBTableItem.NULL;
    }
    
    /////// Update when implements order ///////
    /*public DDBTableItem min(String columnStr, String where){
        long timeMachine = System.currentTimeMillis();
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
        
        System.out.println("DDBTableFile.Min.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
        
        return resultSelect.rows.size() > 0 ? result : DDBTableItem.NULL;
    }*/
    
    // SQL data functions
    public void insert(String columns, String values){
        this.insert(columns, values.split(", "));
    }
    public void insert(String columns, String [] values){
        this.insert(columns.contains("*") ? head.columnsStr.split(":") : columns.split(", "), values);
    }
    public void insert(String [] columns, String [] values){
        if(columns.length == values.length){
            long timeMachine = System.currentTimeMillis();
            DDBTableRow row = new DDBTableRow(data);
            
            if((columns.length == 1 && columns[0].equals("")) || columns.length == 0) {
                System.err.println("DDBTableFile.Insert.Error: Columns length = 0");
                return;
            }
            
            for (int i = 0; i < columns.length; i++)
                columns[i] = columns[i].split("@")[0];
             
            for (int i = 0; i < data.columns.size(); i++) {
                boolean insertNull = true;
                
                for (int j = 0; j < columns.length; j++){
                    
                    if(data.getColumnIndex(columns[j]) == -1){
                            System.err.println("DDBTableFile.Insert.Error: column [" + columns[j] + "] not defined"); return; }
                    
                    if(data.columns.get(i).getName().equals(columns[j])){
                        DDBTableItem item = new DDBTableItem(values[j]);
                        
                        if(!item.isNumber()){
                            if( data.columns.get(i).getType().isOfType(ColumnTypes.INTEGER) ||
                                data.columns.get(i).getType().isOfType(ColumnTypes.DOUBLE) ||
                                data.columns.get(i).getType().isOfType(ColumnTypes.FLOAT) ){
                                System.err.println("DDBTableFile.Insert.Insert.Error: value [" + item + "] is not " + data.columns.get(i).getType().name());
                                return;
                            } else{
                                row.add(item);  insertNull = false; 
                            }
                        }
                        if(data.columns.get(i).getType().isOfType(ColumnTypes.DATETIME) && !item.isDate()){
                            System.err.println("DDBTableFile.Insert.Insert.Error: value [" + item + "] is not " + data.columns.get(i).getType().name());
                                return;
                        } else {
                            if(data.columns.get(i).getType().isOfType(ColumnTypes.INTEGER)){
                                row.add("" + item.toInteger());  insertNull = false; }
                            else if(data.columns.get(i).getType().isOfType(ColumnTypes.DOUBLE)){
                                row.add("" + item.toDouble());  insertNull = false; }
                            else if(data.columns.get(i).getType().isOfType(ColumnTypes.FLOAT)){
                                row.add("" + item.toFloat());  insertNull = false; }
                            else if(data.columns.get(i).getType().isOfType(ColumnTypes.BOOLEAN)){
                                row.add("" + item.toTBoolean());  insertNull = false; }
                            else if(data.columns.get(i).getType().isOfType(ColumnTypes.DATETIME)){
                                row.add("" + item.toString());  insertNull = false; }
                        }
                        
                        // Insert value
                        //row.add(item);  insertNull = false; 
                    }
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
                        else if(column.getType().isOfType(ColumnTypes.BOOLEAN))
                            row.add("0");
                        else if(column.getType().isOfType(ColumnTypes.STRING))
                            row.add(DDBTableItem.NULL);
                        else if(column.getType().isOfType(ColumnTypes.DATETIME))
                            row.add(new DDBTableItem(DDBTableItem.NULL.toDate()).toString());
                    } else {
                        if(column.getIncrement() == 0){
                            System.err.println("DDBTableFile.Insert.Error: The column [" + column.getName() + "] don't allows null value.");
                            return;
                        } else if(column.getIncrement() != 0){
                            if(column.getType().isOfType(ColumnTypes.INTEGER))
                                row.add("" + ((int) max(column.getName(), null).toInteger() + (column.getIncrement() > 0 ? column.getIncrement() : -column.getIncrement())));
                            else if(column.getType().isOfType(ColumnTypes.FLOAT))
                                row.add("" + ((int) max(column.getName(), null).toFloat()+ (column.getIncrement() > 0 ? column.getIncrement() : -column.getIncrement())));
                            else if(column.getType().isOfType(ColumnTypes.DOUBLE))
                                row.add("" + ((int) max(column.getName(), null).toDouble()+ (column.getIncrement() > 0 ? column.getIncrement() : -column.getIncrement())));
                            else if(column.getType().isOfType(ColumnTypes.CHARACTER))
                                row.add("" + ((int) max(column.getName(), null).toChar() + (column.getIncrement() > 0 ? column.getIncrement() : -column.getIncrement())));
                        }
                    }
                } else {
                    if(column.isUnique())
                        if(count("*", column.getName() + " = " + row.get(i)) > 0){
                            System.err.println("DDBTableFile.Insert.Error: [" + column.getName() + "]: " + row.get(i) + ", yet exists");
                            return;
                        }
                }
            }
            
            System.out.println("DDBTableFile.Insert.Success: " + row + " inserted");
            System.out.println("DDBTableFile.Insert.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms"); 
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
            int updated = 0;
            long timeMachine = System.currentTimeMillis();
            
            for(int i = 0; i < columns.length; i++){
                
                for(DDBTableRow row : toUpdate.rows){
                //for(int i = 0; i < columns.length; i++){
                    int columnIndex = toUpdate.getColumnIndex(columns[i]);
                    
                    if(columnIndex == -1){
                        System.err.println("DDBTableFile.Update.Error: column [" + columns[i] + "] not defined"); break; }
                    
                    DDBTableItem item = new DDBTableItem(values[i]);
                    DDBTableColumn column = toUpdate.columns.get(columnIndex);
                    
                    if(column.isPrimaryKey()){
                        System.err.println("DDBTableFile.Update.Error: the column [" + column.getName() + "] is primary key, and can't be modified"); break; }
                    
                    if(column.isUnique() && count("*", columns[i] + " = " + values[i]) > 0){
                        System.err.println("DDBTableFile.Update.Error: this column is unique, and this value yes exists"); break; }
                    
                    if((column.getType().isOfType(ColumnTypes.BOOLEAN) && !item.isBoolean()) ||
                       (column.getType().isOfType(ColumnTypes.DATETIME) && !item.isDate())){
                        System.err.println("DDBTableFile.Update.Error: value [" + item + "] is not " + column.getType().name()); break;
                    } else if(!item.isNumber()){
                        if( column.getType().isOfType(ColumnTypes.INTEGER) ||
                            column.getType().isOfType(ColumnTypes.DOUBLE) ||
                            column.getType().isOfType(ColumnTypes.FLOAT) ){
                            System.err.println("DDBTableFile.Update.Error: value [" + item + "] is not " + column.getType().name());
                            break;
                        } else{
                            //row.add(item);  insertNull = false; 
                        }
                    }
                    
                    if(column.getType().isOfType(ColumnTypes.BOOLEAN))
                        item.set(item.toTBoolean());
                    else if(column.getType().isOfType(ColumnTypes.DOUBLE))
                        item.set(item.toDouble());
                    else if(column.getType().isOfType(ColumnTypes.FLOAT))
                        item.set(item.toFloat());
                    
                    if(!row.set(columns[i], item))
                        System.err.println("DDBTableFile.Update.Error: any error not allows update the row [" + row + "]");
                    else
                        updated++;
                    
                }
                    //if(!row.set(columns[i], values[i]))
                    //    System.err.println("DDBTableFile.Update.Error: row[" + toUpdate.rows.indexOf(row) + "]"
                     //           + " column [" + columns[i] + "] not founded");
                
                //if(toUpdate.rows.size() <= 50)
                //    System.out.println("update: " + row);
            }
            
            System.out.println("DDBTableFile.Update.Success: " + updated + " items updated");
            System.out.println("DDBTableFile.Update.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
            
            write();
        } else
            System.err.println("DDBTableFile.Update.Error: columns length not equals values length");
        
    }
    
    public void delete(String where){ // Or Drop
        DDBTableData toDelete = select("*", where, null);
        long timeMachine = System.currentTimeMillis();
        for(DDBTableRow row : toDelete.rows){
            data.rows.remove(row);
            if(toDelete.rows.size() <= 50)
                System.out.println("Delete: " + row);
        }
        
        System.out.println("DDBTableFile.Delete.Success: " + toDelete.rows.size() + " items deleted");
        System.out.println("DDBTableFile.Delete.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
        
        write();
    }
    
    public DDBTableData select(String columns, String where, String order){
        return this.select(columns.contains("*") ? new String[0] : columns.split(", "), where, order);
    }
    
    public DDBTableData select(String [] columns, String where, String order){
        long timeMachine = System.currentTimeMillis();
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
                    String operator = whereArray[1].replace("!", "");
                    
                    //System.out.println(operator);
                    //System.out.println(whereArray[0] + " - " + whereArray[1] + " - " + whereArray[2]);
                    //System.out.println(data.rows.get(i).get(whereArray[0]) + whereArray[1] + whereArray[2]);
                    
                    if(operator.equals("like")) include = data.rows.get(i).get(whereArray[0]).contains(whereArray[2]);
                    else{
                        if(operator.contains("="))
                            include = data.rows.get(i)
                                    .get(whereArray[0])
                                    .equals(whereArray[2]);
                        
                        if(!include){
                            if(operator.contains(">"))
                                include = data.rows.get(i).get(whereArray[0]).compareTo(whereArray[2]) > 0;
                            else if(operator.contains("<"))
                                include = data.rows.get(i).get(whereArray[0]).compareTo(whereArray[2]) < 0;
                        }
                    }
//                    switch (whereArray[1].replace("!", "")) {
//                        case "=": include = data.rows.get(i).get(whereArray[0]).equals(whereArray[2]); break;
//                        case "like": include = data.rows.get(i).get(whereArray[0]).contains(whereArray[2]); break;
//                    }
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
                    Collections.sort(result.rows, ASC_DESC ? com : Collections.reverseOrder(com));
                } catch (java.lang.NullPointerException e){
                    System.err.println("DDBTableFile.Select.Sort.Error: Colummn [" + column + "] not founded");
                }
            }
        }
        
        System.out.println("DDBTableFile.Select.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms"); 
        
        return result;
    }
    
    // Column table functions
    public void insertColumn(DDBTableColumn [] columns){
        for(DDBTableColumn column : columns)
            this.insertColumn(column);
    }
    
    public void insertColumn(DDBTableColumn column){
        this.insertColumn(column, DDBTableItem.NULL);
    }
    public void insertColumn(DDBTableColumn column, String defaultValue){
        this.insertColumn(column, new DDBTableItem(defaultValue));
    }
    public void insertColumn(DDBTableColumn column, int defaultValue){
        this.insertColumn(column, new DDBTableItem(defaultValue));
    }
    public void insertColumn(DDBTableColumn column, char defaultValue){
        this.insertColumn(column, new DDBTableItem(defaultValue));
    }
    public void insertColumn(DDBTableColumn column, float defaultValue){
        this.insertColumn(column, new DDBTableItem(defaultValue));
    }
    public void insertColumn(DDBTableColumn column, double defaultValue){
        this.insertColumn(column, new DDBTableItem(defaultValue));
    }
    public void insertColumn(DDBTableColumn column, boolean defaultValue){
        this.insertColumn(column, new DDBTableItem(defaultValue));
    }
    public void insertColumn(DDBTableColumn column, DDBTableItem defaultValue){
        long timeMachine = System.currentTimeMillis();
        // Check if a column with this name alredy exists
        for(DDBTableColumn col : data.columns)
            if(col.getName().equals(column.getName())){
                System.err.println("DDBTableFile.InsertColumn.Error: column [" + column.getName() + "] yet exists"); return; }
        
        // Avoid String NULL on unique columns
        if(column.isUnique() && 
               (column.getType().isOfType(ColumnTypes.STRING) ||
                column.getType().isOfType(ColumnTypes.DATETIME) ||
                column.getType().isOfType(ColumnTypes.BOOLEAN))){
            System.err.println("DDBTableFile.InsertColumn.Error: the column [" + column.getName() + "] is unique and is [" + column.getType().name() + "]");
            return;
        }
        
        // Needs increments to be unique
        if(column.isUnique() && column.getIncrement() == 0){
            System.err.println("DDBTableFile.InsertColumn.Error: the column [" + column.getName() + "] is unique, but has not increment");
            return;
        }
        
        // Avoid NULL values on columns that not allows null values
        if((defaultValue == DDBTableItem.NULL || defaultValue == null) && !column.isAllowNull()){
            System.err.println("DDBTableFile.InsertColumn.Error: the new column [" + column.getName() + "] do not allows null value");
            return;
        
        // Avoid type values conflicts
        } else if((!defaultValue.isNumber() && (
                column.getType().isOfType(ColumnTypes.INTEGER) ||
                column.getType().isOfType(ColumnTypes.DOUBLE) ||  
                column.getType().isOfType(ColumnTypes.FLOAT))) ||
                
                (!defaultValue.isDate() && column.getType().isOfType(ColumnTypes.DATETIME))){
            
            if(!defaultValue.isNull()){
                System.err.println("DDBTableFile.InsertColumn.Error: the value [" + defaultValue + "] is not " + column.getType().name());
                return;
            } else defaultValue = DDBTableItem.NULL;
        }
        
        // Insert Columns
        this.data.columns.add(column);
        // Insert the new column on rows
        for(DDBTableRow row : data.rows){
            if(column.getType().isOfType(ColumnTypes.INTEGER))
                row.add("" + defaultValue.toInteger());
            else if(column.getType().isOfType(ColumnTypes.FLOAT))
                row.add("" + defaultValue.toFloat());
            else if(column.getType().isOfType(ColumnTypes.DOUBLE))
                row.add("" + defaultValue.toDouble());
            else if(column.getType().isOfType(ColumnTypes.CHARACTER))
                row.add("" + defaultValue.toChar());
            else if(column.getType().isOfType(ColumnTypes.BOOLEAN))
                row.add("" + defaultValue.toTBoolean());
            else if(column.getType().isOfType(ColumnTypes.STRING))
                row.add(defaultValue);
            else if(column.getType().isOfType(ColumnTypes.DATETIME))
                row.add(new DDBTableItem(defaultValue.toDate()).toString());
            
            // Applying the increment on Number column
            if(column.isUnique() && column.getIncrement() != 0)
                if(column.getType().isOfType(ColumnTypes.INTEGER)) defaultValue.set(defaultValue.toInteger() + column.getIncrement());
                else if (column.getType().isOfType(ColumnTypes.DOUBLE)) defaultValue.set(defaultValue.toDouble() + column.getIncrement());
                else if(column.getType().isOfType(ColumnTypes.FLOAT)) defaultValue.set(defaultValue.toFloat() + column.getIncrement());
        }
        
        // Write the modifies on table file
        write();
        
        // Report the insert success
        System.out.println("DDBTableFile.InsertColumn.Success: column [" + column.getName() + "] inserted");
        System.out.println("DDBTableFile.InsertColumn.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
    }
    
    public void deleteColumn(String columnName){
        long timeMachine = System.currentTimeMillis();
        int columnIndex = data.getColumnIndex(columnName);
        
        if(columnIndex > -1){
            DDBTableColumn column = data.columns.get(columnIndex);
            
            if(column.isPrimaryKey()){
                System.err.println("DDBTableFile.DeleteColumn.Error: the column [" + columnName + "] is primary key, and can't be removed");
                return;
            }
            
            data.columns.remove(column);
            for(DDBTableRow row : data.rows)
                row.remove(columnIndex);
            
            System.out.println("DDBTableFile.DeleteColumn.Success: column [" + columnName + "] was removed");
            
        } else{
            System.err.println("DDBTableFile.DeleteColumn.Error: column [" + columnName + "] not founded");
            return;
        }
        
        System.out.println("DDBTableFile.DeleteColumn.Time: " + ((long) System.currentTimeMillis() - timeMachine) + "ms");
        
        write();
    }
}
