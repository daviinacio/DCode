package com.daviapps.dcode.preferences;

import com.daviapps.dcode.DCodeFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/** @author Davi, 13/12/2017 */
public class DCodePreferencesMap <K, V> implements Preferences<K, V> {
    private Map<K, V> map;
    
    public DCodePreferencesMap(String pathname){
        this(new File(pathname));
    }
    public DCodePreferencesMap(File file){
        this(new DCodeFile(file));
    }
    public DCodePreferencesMap(DCodeFile dFile){
        this.map = new HashMap<>();
    }

    @Override
    public boolean add(K key, V value) {
        if(!this.contains(key)){
            return this.map.put(key, value) != value;
        } else return false;
    }

    @Override
    public boolean remove(K key) {
        return this.map.remove(key) != null;
    }

    @Override
    public boolean set(K key, V value) {
        if(this.contains(key)){
            return this.map.put(key, value) != value;
        } else return false;
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public V get(K key, V ifNotFound) {
        V result = this.get(key);
        return result != null ? result : ifNotFound;
    }

    @Override
    public boolean add(K [] keys, V [] values) {
        if(keys.length == values.length){
            for(int i = 0; i < keys.length; i++)
                this.add(keys[i], values[i]);
        } return true;
    }

    @Override
    public boolean set(K[] keys, V[] values) {
        return false;
    }

    @Override
    public V[] get(K[] keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V[] get(K[] keys, K ifNotFound) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(K[] keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
