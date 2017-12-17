 package com.daviapps.dcode.preferences;
 
/** @author Davi */
public class dcp {
    public static Preferences prefs = new DCodePreferencesMap("C:\\Users\\Davi\\Desktop\\dPrefs.dcode");
    //public static Preferences prefs = new DCodePreferences("C:\\Users\\Davi\\Desktop\\dPrefs.dcode");
    public static void main(String [] args){
        
        //prefs.add("nome", "Davi");
        //prefs.add("idade", 18);
        
        //int i = 1/0;

        //java.lang.ArithmeticException
        
        String [] keys = {"nome", "idade"};
        
        prefs.add(keys, new String[]{"Davi", "18"});

        for(String value : prefs.get(keys)) System.out.print(value + "; "); System.out.println();

        //prefs.remove("nome", "idade");
        
        //prefs.set("idade", 65.0);
        prefs.set(new String[]{"nome", "idade"}, new String[]{"Pardall", "65"});
        //prefs.set(new String[]{"nome", "idade"}, new int[]{12, 2});
        //prefs.set(new String[]{"nome", "idade"}, new boolean[]{true, false});

        for(String value : prefs.get(keys)) System.out.print(value + "; "); System.out.println();
        
        //System.out.println(prefs.add("nome", "Davi"));
        //System.out.println(prefs.get("nome", "Não encontrado"));
        
        //System.out.println(prefs.add("idade", 18));
        //System.out.println(prefs.getInt("idade"));;
        
        //System.out.println(prefs.add("dcode", new DCode()));
        //System.out.println(prefs.get("dcode"));
        
        //DCode dc = (DCode) prefs.get("dcode");
        
        //System.out.println(prefs.getSize());
        
        //int idade = prefs.getInt("idade");
        
        prefs.save();
                
        //System.out.println(idade);
        
        //Object obj = 1000;
        //System.out.println(obj);
        
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


//class MyClass <X> {
//    <T> MyClass (T t) {
//        System.out.println(t.toString());
//    }
//    
//    public T get(){
//        
//    }
//}