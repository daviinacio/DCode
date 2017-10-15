package com.daviapps.dcode.ddb.table;

/**
 * @author Davi
 */
public class DDBTableColumn {
    
    // Properties
    private String columnName;
    private ColumnTypes type = ColumnTypes.STRING;
    private boolean allowNull, unique;
    private int increment;
    
    // Constructors
    public DDBTableColumn(String columnName, ColumnTypes type, boolean allowNull, boolean unique, int increment) {
        this.columnName = columnName;
        this.type = type;
        this.allowNull = allowNull;
        this.unique = unique;
        this.increment = increment;
    }
    
    public DDBTableColumn(String columnName, ColumnTypes type, boolean allowNull, boolean unique, char increment) {
        this.columnName = columnName;
        this.type = type;
        this.allowNull = allowNull;
        this.unique = unique;
        this.increment = increment;
    }
    
    public DDBTableColumn(String columnPropStr){
        String [] columnProp = columnPropStr.split("@");
        if(columnProp.length >= 1)
            this.columnName = columnProp[0];
        if(columnProp.length >= 2){
            //System.out.println(columnProp[1]);
            this.type =         ColumnTypes.getTypeByChar(columnProp[1].charAt(0));
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
    
    public ColumnTypes getType() {
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
    
    // Setters

    protected void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    protected void setType(ColumnTypes type) {
        this.type = type;
    }

    protected void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    protected void setUnique(boolean unique) {
        this.unique = unique;
    }

    protected void setIncrement(int increment) {
        this.increment = increment;
    }
    
    
    protected String getAll(){
        return String.format("%s@%s%s%s%s", columnName, type.getType() ,allowNull ? 1 : 0, unique ? 1 : 0, (char) increment);
    }
}
