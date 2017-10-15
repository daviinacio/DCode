package com.daviapps.dcode.ddb.table;

/**
 * @author Davi
 */
public enum ColumnTypes { 
    // Enumns
    INTEGER('i'), STRING('s'), CHARACTER('c'), DOUBLE('d'), FLOAT('f'), DATETIME('t'), BOOLEAN('b');
    
    // Variables
    private char type;
    
    // Constructors
    private ColumnTypes(char type) {
        this.type = type;
    }
    
    // Getters
    public char getType(){ return type; }
    
    // Check methods
    public boolean isOfType(char type){ return type == this.type; }
    public boolean isOfType(ColumnTypes type){ return type.type == this.type; }
    
    // Static methods
    protected static ColumnTypes getTypeByChar(char c){
        ColumnTypes type = null;
        for (ColumnTypes value : ColumnTypes.values())
            if (value.isOfType(c)) type = value;
        
        return type;
    }
}
