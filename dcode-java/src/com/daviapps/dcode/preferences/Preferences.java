package com.daviapps.dcode.preferences;

/** @author Davi, 17/12/2017 */
public interface Preferences extends PreferencesBase<String, String>{
    // Adders
    public boolean add(String key, int value);
    public boolean add(String key, double value);
    public boolean add(String key, boolean value);
    
    public boolean [] add(String [] keys, int [] values);
    public boolean [] add(String [] keys, double [] values);
    public boolean [] add(String [] keys, boolean [] values);
    
    // Setters
    public boolean set(String key, int value);
    public boolean set(String key, double value);
    public boolean set(String key, boolean value);
    
    public boolean [] set(String [] keys, int [] values);
    public boolean [] set(String [] keys, double [] values);
    public boolean [] set(String [] keys, boolean [] values);
    
    // Getters
    public int getInt(String key);
    public double getDouble(String key);
    public boolean getBool(String key);
    
    public int [] getInt(String [] keys);
    public double [] getDouble(String [] keys);
    public boolean [] getBool(String [] keys);
    
    public int getInt(String key, int ifNotFound);
    public double getDouble(String key, double ifNotFound);
    public boolean getBool(String key, boolean ifNotFound);
    
    public int [] getInt(String [] keys, int ifNotFound);
    public double [] getDouble(String [] keys, double ifNotFound);
    public boolean [] getBool(String [] keys, boolean ifNotFound);
    
}
