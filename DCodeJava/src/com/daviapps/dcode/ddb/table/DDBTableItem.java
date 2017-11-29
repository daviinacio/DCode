package com.daviapps.dcode.ddb.table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Davi
 */
public class DDBTableItem {
    // Static variables
    public static DDBTableItem NULL = new DDBTableItem("NULL");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    // Variables
    private String content;
    
    // Constructors
    public DDBTableItem(char content){ this.set(content); }
    public DDBTableItem(float content){ this.set(content); }
    public DDBTableItem(double content){ this.set(content); }
    public DDBTableItem(int content){ this.set(content); }
    public DDBTableItem(boolean content){ this.set(content); }
    public DDBTableItem(String content){ this.set(content); }
    public DDBTableItem(Date date){ this.set(date); }
    
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
    
    public int compareTo(String str){
        return compareTo(new DDBTableItem(str));
    }
    
    public int compareTo(DDBTableItem item){
        int compare = 0;
        
        if(isNumber() && !isNumber(item))
            item.set(0);
        
        //System.out.println(this.content + " - " + item.toString());
        if(isNumber() && isNumber(item)){
            if(this.toDouble() > item.toDouble()) compare = 1; else
            if(this.toDouble() < item.toDouble()) compare = -1;
        }if(isDate()&& isDate(item)){
            compare = this.toDate().compareTo(item.toDate());
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
            if(toString().charAt(0) == 'c')
                return toString().charAt(1);
            else return toString().charAt(0);
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
        return content.equals("1") || content.equals("true");
    }
    
    public int toTBoolean(){
        return toBoolean() ? 1 : 0;
    }
    
    public Date toDate(){
        try { return dateFormat.parse(!isNull() ? content : "01/01/1980 00:00:00"); } 
        catch (ParseException ex) {
            System.err.println("DDBTableItem.Error: ParseExeption, " + content + " is not Date.");
            return null;
        }
    }
    
    // Check
    public boolean isNumber(){
        return DDBTableItem.isNumber(this);
    }
    
    public boolean isBoolean(){
        return DDBTableItem.isBoolean(this);
    }
    
    public boolean isDate(){
        return DDBTableItem.isDate(content);
    }
    
    // Static methods
    public static boolean isNumber(DDBTableItem item){
        return DDBTableItem.isNumber(item.toString());
    }
    public static boolean isNumber(String value){
        try { Double.parseDouble(value); return true;
        } catch (java.lang.NumberFormatException e){ return false; }
    }
    
    public static boolean isBoolean(DDBTableItem item){
        return isBoolean(item.toString());
    }
    public static boolean isBoolean(String value){
        return (value.equals("1") || value.equals("0") || value.equals("true") || value.equals("false"));
    }
    
    public static boolean isDate(DDBTableItem item){
        return DDBTableItem.isDate(item.toString());
    }
    public static boolean isDate(String value){
        try { dateFormat.parse(value);  return true; } 
        catch (ParseException ex) {
            //System.err.println("DDBTableItem.Error: ParseExeption, " + value + " is not Date.");
            return false;
        }
    }
    
    // Override methods
    
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
    
    public void set(Date date){
        this.content = dateFormat.format(date);
    }
}
