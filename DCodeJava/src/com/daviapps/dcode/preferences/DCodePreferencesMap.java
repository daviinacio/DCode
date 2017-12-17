package com.daviapps.dcode.preferences;

import com.daviapps.dcode.DCodeFile;
import com.daviapps.dcode.exception.AddException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** @author Davi, 13/12/2017 */
public class DCodePreferencesMap implements Preferences {
    private DCodeFile file;
    protected Map<String, String> map;
    
    /* Don't needs throws Exeptions */
    
    // Constructors
    public DCodePreferencesMap(String pathname){
        this(new File(pathname));
    }
    public DCodePreferencesMap(File file){
        this(new DCodeFile(file));
    }
    public DCodePreferencesMap(DCodeFile file){
        this.map = new HashMap<>();
        this.file = file;
        
        this.load();
    }
    
    // File methods
    public void load() {
        System.out.println("DCPM.load()");
    }
    
    @Override
    public void save() {
        System.out.println("DCPM.save()");
    }
    
    // List methods
    @Override
    public boolean contains(String key) {
        return this.map.containsKey(key);
    }

    @Override
    public int getSize() {
        return this.map.size();
    }
    
    // Interactors
    @Override
    public boolean add(String key, String value) {
        if(!contains(key)){
            this.map.put(key, value);
            return true;
        } else return false;
    }
    
    @Override
    public boolean remove(String key) {
        if(contains(key)){
            this.map.remove(key);
            return true;
        } else return false;
    }

    @Override
    public String get(String key, String ifNotFound) {
        if(contains(key))
            return this.map.get(key);
        else return ifNotFound;
    }

    @Override
    public boolean set(String key, String value) {
        if(contains(key)){
            this.map.put(key, value);
            return true;
        } else return false;
    }
    
    // Others Interactors
    
    // Adders
    @Override public boolean add(String key, boolean value) {
        return this.add(key, Boolean.toString(value));
    }
    @Override public boolean add(String key, double value) {
        return this.add(key, Double.toString(value));
    }
    @Override public boolean add(String key, int value) {
        return this.add(key, Integer.toString(value));
    }
    
    // Adders array

    /**
     * @param keys
     * @param values
     * @return
     * @throws AddException
     */
    @Override public boolean [] add(String[] keys, String[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.add(keys[i], values[i]);
        else System.err.println("DCPM.add() - keys.length not equals values.length");
        return result;
    }
    @Override public boolean [] add(String[] keys, boolean[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.add(keys[i], values[i]);
        else System.err.println("DCPM.add() - keys.length not equals values.length");
        return result;
    }
    @Override public boolean [] add(String[] keys, double[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.add(keys[i], values[i]);
        else System.err.println("DCPM.add() - keys.length not equals values.length");
        return result;
    }
    @Override public boolean [] add(String[] keys, int[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.add(keys[i], values[i]);
        else System.err.println("DCPM.add() - keys.length not equals values.length");
        return result;
    }
    
    // Removers
    @Override public void remove(String[] keys) {
        for(String key : keys)
            this.remove(key);
    }
    
    // Getters
    @Override public String get(String key) {
        return this.get(key, null);
    }
    @Override public int getInt(String key) {
        return this.getInt(key, -1);
    }
    @Override public double getDouble(String key) {
        return this.getDouble(key, -1);
    }
    @Override public boolean getBool(String key) {
        return this.getBool(key, false);
    }
    
    @Override public int getInt(String key, int ifNotFound) {
        try { return Integer.parseInt(get(key)); }
        catch (NumberFormatException e) {
            System.err.println("DCPM.getInt() - Error parse to Integer");
        } return ifNotFound;
    }
    @Override public double getDouble(String key, double ifNotFound) {
        try { return Double.parseDouble(get(key)); }
        catch (NullPointerException e) {
            System.err.println("DCPM.getInt() - Error parse to Double");
        } return ifNotFound;
    }
    @Override public boolean getBool(String key, boolean ifNotFound) {
        try { return Boolean.parseBoolean(get(key)); }
        catch (IndexOutOfBoundsException e) {
            System.err.println("DCPM.getInt() - Error parse to Boolean");
        } return ifNotFound;
    }
    
    // Getters array
    @Override public String [] get(String[] keys) {
        return this.get(keys, null);
    }
    @Override public int [] getInt(String[] keys) {
        return this.getInt(keys, -1);
    }
    @Override public double [] getDouble(String[] keys) {
        return this.getDouble(keys, -1);
    }
    @Override public boolean [] getBool(String[] keys) {
        return this.getBool(keys, false);
    }
    
    @Override public String [] get(String[] keys, String ifNotFound) {
        String [] result = new String[keys.length];
        for (int i = 0; i < keys.length; i++)
            result[i] = this.get(keys[i], ifNotFound);
        return result;
    }
    @Override public int [] getInt(String[] keys, int ifNotFound) {
        int [] result = new int[keys.length];
        for (int i = 0; i < keys.length; i++)
            result[i] = this.getInt(keys[i], ifNotFound);
        return result;
    }
    @Override public double [] getDouble(String[] keys, double ifNotFound) {
        double [] result = new double[keys.length];
        for (int i = 0; i < keys.length; i++)
            result[i] = this.getDouble(keys[i], ifNotFound);
        return result;
    }
    @Override public boolean [] getBool(String[] keys, boolean ifNotFound) {
        boolean [] result = new boolean[keys.length];
        for (int i = 0; i < keys.length; i++)
            result[i] = this.getBool(keys[i], ifNotFound);
        return result;
    }
    
    // Setters
    @Override public boolean set(String key, int value) {
        return this.set(key, Integer.toString(value));
    }
    @Override public boolean set(String key, double value) {
        return this.set(key, Double.toString(value));
    }
    @Override public boolean set(String key, boolean value) {
        return this.set(key, Boolean.toString(value));
    }
    
    // Setters array
    @Override public boolean [] set(String[] keys, String[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.set(keys[i], values[i]);
        else System.err.println("DCPM.set([], []) - keys.length != values.length");
        return result;
    }
    @Override public boolean [] set(String[] keys, int[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.set(keys[i], values[i]);
        else System.err.println("DCPM.set([], []) - keys.length != values.length");
        return result;
    }
    @Override public boolean [] set(String[] keys, double[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.set(keys[i], values[i]);
        else System.err.println("DCPM.set([], []) - keys.length != values.length");
        return result;
    }
    @Override public boolean [] set(String[] keys, boolean[] values) {
        boolean [] result = new boolean[keys.length];
        if(keys.length == values.length)
            for (int i = 0; i < keys.length; i++)
                result[i] = this.set(keys[i], values[i]);
        else System.err.println("DCPM.set([], []) - keys.length != values.length");
        return result;
    }
}