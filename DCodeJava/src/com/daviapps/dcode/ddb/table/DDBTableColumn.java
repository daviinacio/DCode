package com.daviapps.dcode.ddb.table;

/**
 * @author Davi
 */
public class DDBTableColumn {
    // Properties
    private String columnName;
    private char type;
    private boolean allowNull, unique;
    private int increment;
    
    // Constructor
    public DDBTableColumn(String columnPropStr){
        String [] columnProp = columnPropStr.split("@");
        if(columnProp.length >= 1)
            this.columnName = columnProp[0];
        if(columnProp.length >= 2){
            //System.out.println(columnProp[1]);
            this.type =         columnProp[1].charAt(0);
            this.increment =    columnProp[1].charAt(3);
            this.allowNull =    columnProp[1].charAt(1) == '1';
            this.unique =       columnProp[1].charAt(2) == '1';
        }
        
        //System.out.println(String.format("%s, type: %s, allowNull: %s, unique: %s, increment: %s",
        //System.out.println(String.format("%s(%s, %s, %s, %s)",
        //        columnName, type, allowNull, unique, increment));
    }
    
    // Getters
    public String getName(){
        return this.columnName;
    }
    
    public char getType() {
        return type;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public boolean isUnique() {
        return unique;
    }

    public int getIncrement() {
        return increment;
    }
    
    protected String getAll(){
        return String.format("%s@%s%s%s%s", columnName, type, allowNull ? 1 : 0, unique ? 1 : 0, (char) increment);
    }
}
