package com.daviapps.dcode.ddb;

/**
 * @author Davi
 */
public class DDBTableColumn {
    private String type = "";
    private String name = "";
    
    public DDBTableColumn(String columnStr){
        this(columnStr, ".");
    }
    
    public DDBTableColumn(String columnStr, String splitStr){
        String [] props = columnStr.split("_");
        //System.out.println(columnStr);
        if(props.length >= 2){
            this.type = props[0];
            this.name = props[1];
        }
    }
    
    // Getters

    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", type, name);
    }
}
