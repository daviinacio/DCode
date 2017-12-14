 package com.daviapps.dcode.preferences;

import java.util.HashMap;
import java.util.Map;

/** @author Davi */
public class dcp {
    public static Preferences prefs = new DCodePreferencesMap<String, String>("C:\\Users\\Davi\\Desktop\\dPrefs.dcode");
    //public static Preferences prefs = new DCodePreferences("C:\\Users\\Davi\\Desktop\\dPrefs.dcode");
    public static void main(String [] args){
        System.out.println(prefs.get("nome"));
        System.out.println(prefs.getSize());
        
        
        
        //prefs.teste("key", "value");
        
        //Object integer = 10;
        //System.out.println((int) integer + 10);
        
        
        /*Map<String, String> map = new HashMap<String, String>();
        
        map.put("chave", "valor");
        map.put("nome", "Davi");
        
        System.out.println(map.);
        
        for(String key : map.keySet()){
            System.out.println("Key: " + key + "\tValue: " + map.get(key));
        }*/
    }
}
