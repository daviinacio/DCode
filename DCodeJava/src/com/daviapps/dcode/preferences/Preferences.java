package com.daviapps.dcode.preferences;

/** *  @author Davi, 13/12/2017
 * @param <K> : Key
 * @param <V> : Value */
public interface Preferences<K extends java.lang.Object, V extends java.lang.Object> {
    public boolean add(K key, V value);
    public boolean remove(K key);
    public boolean set(K key, V value);
    public V get(K key);
    public V get(K key, V ifNotFound);
    
    public boolean add(K [] keys, V [] values);
    public boolean set(K [] keys, V [] values);
    public V [] get(K [] keys);
    public V [] get(K [] keys, K ifNotFound);
    public void remove(K [] keys);
    
    public boolean contains(K key);
    
    public int getSize();
    
    public void load();
    public void save();
}
