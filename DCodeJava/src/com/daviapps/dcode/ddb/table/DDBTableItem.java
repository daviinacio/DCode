package com.daviapps.dcode.ddb.table;

/**
 * @author Davi
 */
public class DDBTableItem {
    // Static variables
    public static DDBTableItem NULL = new DDBTableItem("NULL");
    // Variables
    private String content;
    
    // Constructors
    public DDBTableItem(char content){ this.set(content); }
    public DDBTableItem(float content){ this.set(content); }
    public DDBTableItem(double content){ this.set(content); }
    public DDBTableItem(int content){ this.set(content); }
    public DDBTableItem(boolean content){ this.set(content); }
    public DDBTableItem(String content){ this.set(content); }
    
    // Verification functions
    public boolean equals(DDBTableItem item) {
        return this.equals(item.toString());
    }
    
    public boolean equals(String content) {
        return this.content.toLowerCase().equals(content.toLowerCase());
    }
    
    public boolean contains(String content){
        return this.content.toLowerCase().contains(content.toLowerCase());
    }
    
    public int compareTo(DDBTableItem item){
        int compare = 0;
        if(isNumber() && isNumber(item)){
            if(this.toDouble() > item.toDouble()) compare = 1; else
            if(this.toDouble() < item.toDouble()) compare = -1;
        } else
            compare = this.content.toLowerCase().compareTo(item.toString().toLowerCase());
        return compare;
    }
    
    public boolean isNull(){
        return this.equals(DDBTableItem.NULL);
    }
    
    // Getters
    public char toChar(){
        if(!isNumber())
            return toString().charAt(0);
        else return (char) toInteger();
    }
    
    public float toFloat(){
        if(isNumber())
            return Float.parseFloat(content);
        else return 0;
    }
    
    public double toDouble(){
        if(isNumber())
            return Double.parseDouble(content);
        else return 0;
    }
    
    public int toInteger(){
        if(isNumber())
            return (int) this.toDouble();
        else return 0;
    }
    
    public boolean toBoolean(){
        return content.equals("1");
    }
    
    // Check
    public boolean isNumber(){
        return this.isNumber(this);
    }
    
    public boolean isNumber(DDBTableItem item){
        return this.isNumber(item.toString());
    }
    
    public boolean isNumber(String value){
        try { Double.parseDouble(value); return true;
        } catch (java.lang.NumberFormatException e){ return false; }
    }
    
    @Override
    public String toString() {
        return content;
    }
    
    // Setters
    public void set(char value){
        this.content = "" + ((int) value);
    }
    
    public void set(float value){
        this.content = "" + value;
    }
    
    public void set(double value){
        this.content = "" + value;
    }
    
    public void set(int value){
        this.content = "" + value;
    }
    
    public void set(boolean value){
        this.content = "" + value;
    }
    
    public void set(String value){
        this.content = value;
    }
}
