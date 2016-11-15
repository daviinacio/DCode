package com.daviapps.DCodePreferences;

import com.daviapps.DCode.*;
import java.io.File;

/**
 * @author Davi
 */

public class DCodePreferences {
    protected DCodeFile file;
    protected final DCode dcode = new DCode('[', ':', ']');
    protected DCodePrefItemList list;
    
    // Constructors
    
    public DCodePreferences(String file){
        this(new File(file));
    }
    
    public DCodePreferences(File file){
        this(new DCodeFile(file));
    }
    
    // Main constructor
    public DCodePreferences(DCodeFile file){
        this.file = file;
        if(file.getStatusKey() == DCodeFile.ALRIGHT){
            System.out.println("Alright");
            list = new DCodePrefItemList(dcode, file.getText());
        } else
        if(file.getStatusKey() == DCodeFile.EMPTY){
            System.err.println("Empty");
            file.createBaseFile();
            System.out.println("Base file created");
        } else
        if(file.getStatusKey() == DCodeFile.ERROR){
            System.err.println("Error");
        }
    }
    
    // Loader methods
    
    public void save(){
        file.setText(list.toString());
    }
    
    // Methods
    
    public boolean add(String key, String value){
        if(!list.contains(key)){
            list.addItem(key, value);
            return true;
        }
        return false; // If this item exists
    }
    
    public boolean remove(String key){
        if(list.contains(key)){
            list.removeItem(key);
            return true;
        }
        return false;
    }
    
    public boolean set(String key, String value){
        if(list.contains(key)){
            list.getItem(key).setValue(value);
            return true;
        }
        return false;
    }
    
    public String get(String key, String ifNotFound){
        if(list.contains(key)) // If item did found
            return list.getItem(key).getValue();
        return ifNotFound;
    }
    
    // Getters, Setters and Adders
    
    // Adders
    
    public boolean add(String key, int value){ // AddInteger
        return add(key, Integer.toString(value));
    }
    
    public boolean add(String key, double value){ // AddDouble
        return add(key, Double.toString(value));
    }
    
    public boolean add(String key, boolean value){ // AddBoolean
        return add(key, Boolean.toString(value));
    }
    
    public boolean add(String key, Datas value){ // AddDatas
        return add(key, value.toString());
    }
    
    // Setters
    public boolean set(String key, int value){ // SetInteger
        return set(key, Integer.toString(value));
    }
    
    public boolean set(String key, double value){ // SetDouble
        return set(key, Double.toString(value));
    }
    
    public boolean set(String key, boolean value){ // SetBoolean
        return set(key, Boolean.toString(value));
    }
    
    public boolean set(String key, Datas value){ // SetDatas
        return set(key, value.toString());
    }
    
    // Getters
    public String get(String key){ // GetString
        return get(key, null);
    }
    
    public int getInt(String key, int ifNotFound){ // GetInteger
        try { return Integer.parseInt(get(key)); }
        catch (IndexOutOfBoundsException e) {
            System.out.println(e.toString());
        }
        return ifNotFound;
    }
    
    public int getInt(String key){ // GetInteger
        return getInt(key, 0);
    }
    
    public double getDouble(String key, double ifNotFound){ // GetDouble
        try { return Double.parseDouble(get(key)); }
        catch (IndexOutOfBoundsException e) {
            System.err.println(e.toString());
        }
        return ifNotFound;
    }
    
    public double getDouble(String key){ // GetDouble
        return getDouble(key, 0);
    }
    
    public boolean getBool(String key, boolean ifNotFound){ // GetBoolean
        try { return Boolean.parseBoolean(get(key)); }
        catch (IndexOutOfBoundsException e){
            System.err.println(e.toString());
        }
        return ifNotFound;
    }
    
    public boolean getBool(String key){ // GetBoolean
        return getBool(key, false);
    }
    
    public Datas getDatas(String key, Datas ifNotFound){ // GetDatas
        try { return Datas.parseDatas(get(key)); }
        catch (IndexOutOfBoundsException e){
            System.err.println(e.toString());
        }
        return ifNotFound;
    }
    
    public Datas getDatas(String key){ // GetDatas
        return getDatas(key, new Datas()); // Return a empty datas if not founded
    }
    
    // Getters, Setters, Adders and Removers array
    
    // Adders array
    public boolean add(String [] keys, String [] values){ // AddStringArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                add(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean add(String [] keys, int [] values){ // AddIntegerArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                add(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean add(String [] keys, double [] values){ // AddDoubleArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                add(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean add(String [] keys, boolean [] values){ // AddBooleanArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                add(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean add(String [] keys, Datas [] values){ // AddDatasArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                add(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    // Setters array
    public boolean set(String [] keys, String [] values){ // SetStringArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                set(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean set(String [] keys, int [] values){ // SetIntegerArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                set(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean set(String [] keys, double [] values){ // SetDoubleArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                set(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean set(String [] keys, boolean [] values){ // SetBooleanArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                set(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    public boolean set(String [] keys, Datas [] values){ // SetDatasArray
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                set(keys[i], values[i]);
            }
            return true;
        }
        return false;
    }
    
    // Getters array
    
    public String [] get(String [] keys, String ifNotFound){ // GetStringArray
        String [] outp = new String[keys.length];
        for(int i = 0; i < keys.length; i++){
            if(list.contains(keys[i])){
                outp[i] = get(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
    public String [] get(String [] keys){ // GetStringArray
        return get(keys, null);
    }
    
    public int [] getInt(String [] keys, int ifNotFound){ // GetIntegerArray
        int [] outp = new int[keys.length];
        for(int i = 0; i < keys.length; i++){
            if(list.contains(keys[i])){
                outp[i] = getInt(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
     public int [] getInt(String [] keys){ // GetIntegerArray
         return getInt(keys, 0);
     }
    
    public double [] getDouble(String [] keys, double ifNotFound){ // GetDoubleArray
        double [] outp = new double[keys.length];
        for(int i = 0; i < keys.length; i++){
            if(list.contains(keys[i])){
                outp[i] = getInt(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
    public double [] getDouble(String [] keys){ // GetDoubleArray
        return getDouble(keys, 0);
    }
    
    public boolean [] getBool(String [] keys, boolean ifNotFound){ // GetBooleanArray
        boolean [] outp = new boolean[keys.length];
        for(int i = 0; i < keys.length; i++){
            if(list.contains(keys[i])){
                outp[i] = getBool(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
    public boolean [] getBool(String [] keys){ // GetBooleanArray
        return getBool(keys, false);
    }
    
    public Datas [] getDatas(String [] keys, Datas ifNotFound){ // GetDatasArray
        Datas [] outp = new Datas[keys.length];
        for(int i = 0; i < keys.length; i++){
            if(list.contains(keys[i])){
                outp[i] = getDatas(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
    public Datas [] getDatas(String [] keys){ // GetDatasArray
        return getDatas(keys, new Datas());
    }
    
    // Removers array
    
    public void remove(String [] keys){
        for (String key : keys) {
            remove(key);
        }
    }
    
    // Override methods

    @Override
    public String toString() {
        return list.toString();
    }
    
}
