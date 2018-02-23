package com.daviapps.dcode.preferences;

import com.daviapps.dcode.DCode;
import com.daviapps.dcode.Datas;
import com.daviapps.dcode.DCodeFile;
import java.io.File;

/** @author Davi, 11/11/2016 */
@Deprecated
public class DCodePreferences implements Preferences {
    protected final DCode dcode = new DCode(DCode.ARRAY);
    
    // Private variables
    protected DCodePrefItemList list;
    protected DCodeFile file;
    
    // Constructors
    
    public DCodePreferences(String file){
        this(new File(file));
    }
    
    public DCodePreferences(String pakegeName, String fileName){
        this(new DCodeFile("/android/data/" + pakegeName, fileName));
    }
    
    public DCodePreferences(File file){
        this(new DCodeFile(file));
    }
    
    // Main constructor
    public DCodePreferences(DCodeFile file){
        this.file = new DCodeFile(file.getFile()){
            @Override
            public void onAlright() {
                System.out.println("Preference: ALRIGHT");
                list = new DCodePrefItemList(DCodePreferences.this.dcode, this.getText());
                DCodePreferences.this.onAlright();
            }

            @Override
            public void onError() {
                System.err.println("Preference: File error load");
                DCodePreferences.this.onError();
            }

            @Override
            public void onEmpty() {
                System.err.println("Preference: File empty, base file ceated");
                super.onEmpty();
                DCodePreferences.this.onError();
            }

            @Override
            public void onNotFound() {
                System.err.println("File not founded, file created");
                super.onNotFound();
                DCodePreferences.this.onError();
            }
        };
    }
    
    // Loader methods
    @Override
    public void save(){
        if(file.getStatusKey() == DCodeFile.EMPTY)
            file.createBaseFile();
        else
        if(file.getStatusKey() == DCodeFile.NOTFOUNDED)
            file.createFile();
        
        if(file.getStatusKey() == DCodeFile.ALRIGHT){
            file.setText(list.toString());
            this.onSave();
        }
    }

    public void load() { }
    
    // Methods

    @Override
    public boolean contains(String key) {
        return list.contains(key);
    }
    
    @Override
    public boolean add(String key, String value){
        if(!list.contains(key)){
            list.addItem(key, value);
            return true;
        }
        return false; // If this item exists
    }
    
    @Override
    public boolean remove(String key){
        if(list.contains(key)){
            list.removeItem(key);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean set(String key, String value){
        if(list.contains(key)){
            list.getItem(key).setValue(value);
            return true;
        }
        return false;
    }
    
    @Override
    public String get(String key){ // GetString
        return get(key, null);
    }
    
    @Override
    public String get(String key, String ifNotFound){
        if(list.contains(key)) // If item did found
            return list.getItem(key).getValue();
        return ifNotFound;
    }
    
    // Getters, Setters and Adders
    
    // Index Getters
    
    public DCodePrefItem getByIndex(int i){
        return list.getList().get(i);
    }
    
    public String getValueByIndex(int i){
        return list.getList().get(i).getValue();
    }
    
    @Override
    public int getSize(){
        return list.getList().size();
    }
    
    // Adders
    @Override
    public boolean add(String key, int value){ // AddInteger
        return add(key, Integer.toString(value));
    }
    
    @Override
    public boolean add(String key, double value){ // AddDouble
        return add(key, Double.toString(value));
    }
    
    @Override
    public boolean add(String key, boolean value){ // AddBoolean
        return add(key, Boolean.toString(value));
    }
    
    public boolean add(String key, Datas value){ // AddDatas
        return add(key, Datas.toString(value));
    }
    
    // Setters
    @Override
    public boolean set(String key, int value){ // SetInteger
        return set(key, Integer.toString(value));
    }
    
    @Override
    public boolean set(String key, double value){ // SetDouble
        return set(key, Double.toString(value));
    }
    
    @Override
    public boolean set(String key, boolean value){ // SetBoolean
        return set(key, Boolean.toString(value));
    }
    
    public boolean set(String key, Datas value){ // SetDatas
        return set(key, Datas.toString(value));
    }
    
    // Getters
    @Override
    public int getInt(String key, int ifNotFound){ // GetInteger
        try { return Integer.parseInt(get(key)); }
        catch (IndexOutOfBoundsException e) {
            System.out.println(e.toString());
        }
        return ifNotFound;
    }
    
    @Override
    public int getInt(String key){ // GetInteger
        return getInt(key, 0);
    }
    
    @Override
    public double getDouble(String key, double ifNotFound){ // GetDouble
        try { return Double.parseDouble(get(key)); }
        catch (IndexOutOfBoundsException e) {
            System.err.println(e.toString());
        }
        return ifNotFound;
    }
    
    @Override
    public double getDouble(String key){ // GetDouble
        return getDouble(key, 0);
    }
    
    @Override
    public boolean getBool(String key, boolean ifNotFound){ // GetBoolean
        try { return Boolean.parseBoolean(get(key)); }
        catch (IndexOutOfBoundsException e){
            System.err.println(e.toString());
        }
        return ifNotFound;
    }
    
    @Override
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
    @Override
    public boolean [] add(String [] keys, String [] values){ // AddStringArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = add(keys[i], values[i]);
            }
        }
        
        return result;
    }
    @Override
    public boolean [] add(String [] keys, int [] values){ // AddIntegerArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = add(keys[i], values[i]);
            }
        }
        
        return result;
    }
    @Override
    public boolean [] add(String [] keys, double [] values){ // AddDoubleArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = add(keys[i], values[i]);
            }
        }
        return result;
    }
    @Override
    public boolean [] add(String [] keys, boolean [] values){ // AddBooleanArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = add(keys[i], values[i]);
            }
        }
        return result;
    }
    
    public boolean [] add(String [] keys, Datas [] values){ // AddDatasArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = add(keys[i], values[i]);
            }
        }
        return result;
    }
    
    // Setters array
    @Override
    public boolean [] set(String [] keys, String [] values){ // SetStringArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = set(keys[i], values[i]);
            }
        }
        return result;
    }
    
    @Override
    public boolean [] set(String [] keys, int [] values){ // SetIntegerArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = set(keys[i], values[i]);
            }
        }
        return result;
    }
    
    @Override
    public boolean [] set(String [] keys, double [] values){ // SetDoubleArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = set(keys[i], values[i]);
            }
        }
        return result;
    }
    
    @Override
    public boolean [] set(String [] keys, boolean [] values){ // SetBooleanArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = set(keys[i], values[i]);
            }
        }
        return result;
    }
    
    public boolean [] set(String [] keys, Datas [] values){ // SetDatasArray
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++){
                result[i] = set(keys[i], values[i]);
            }
        }
        return result;
    }
    
    // Getters array
    @Override
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
    
    @Override
    public String [] get(String [] keys){ // GetStringArray
        return get(keys, null);
    }
    
    @Override
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
    
    @Override
    public int [] getInt(String [] keys){ // GetIntegerArray
        return getInt(keys, 0);
    }
    
    @Override
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
    
    @Override
    public double [] getDouble(String [] keys){ // GetDoubleArray
        return getDouble(keys, 0);
    }
    
    @Override
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
    
    @Override
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
    @Override
    public void remove(String [] keys){
        for (String key : keys) {
            remove(key);
        }
    }
    
    // External methods
    
    public void onAlright(){}
    
    public void onError(){}
    
    public void onSave(){}
    
    // Override methods
    @Override
    public String toString() {
        return list.toString();
    }
    
}
