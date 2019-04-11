using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/**
 * @author Davi
 */

public class DCodePreferences {
    private DCode dcode = new DCode(DCode.ARRAY);

    // Private variables
    private DCodeFile file;
    private DCodePrefItemList list;

    // Constructors

    public DCodePreferences(String file) {
        This(new DCodeFile(file));
    }

    public DCodePreferences(DCodeFile file) {
        This(file);
    }

    // Main constructor
    private void This(DCodeFile file) {
        this.file = file;


        if(file.getStatusKey() == DCodeFile.EMPTY){
            Console.WriteLine("Preference: File empty, base file ceated");
            file.createBaseFile();
        } else
        if(file.getStatusKey() == DCodeFile.NOTFOUNDED){
            Console.WriteLine("Preference: File not founded, file created");
            file.createFile();
        } else
        if(file.getStatusKey() == DCodeFile.ERROR){
            Console.WriteLine("Preference: File error load");
        } else
        if(file.getStatusKey() == DCodeFile.OTHERENCODER) {
            Console.WriteLine("Preference: File with other encode mode");
        }
        
        // Second verification
        if(file.getStatusKey() == DCodeFile.ALRIGHT){
            Console.WriteLine("Preference: ALRIGHT");
            list = new DCodePrefItemList(dcode, file.getText());
        }
    }

    // Loader methods

    public void save() {
        file.setText(list.ToString());
        Console.WriteLine("Preference: Saved");
    }

    // Methods

    public bool Add(String key, String value) {
        if (!list.contains(key)) {
            list.addItem(key, value);
            return true;
        }
        return false; // If this item exists
    }

    public bool Remove(String key) {
        if (list.contains(key)) {
            list.removeItem(key);
            return true;
        }
        return false;
    }

    public bool Set(String key, String value) {
        if (list.contains(key)) {
            list.getItem(key).setValue(value);
            Console.WriteLine("Preference: '" + key + "' setted to: '" + value + "'");
            return true;
        }
        return false;
    }

    public String Get(String key, String ifNotFound) {
        if (list.contains(key)) // If item did found
            return list.getItem(key).getValue();
        return ifNotFound;
    }

    // Methods

    public DCodeFile GetFile() {
        return this.file;
    }

    public DCodePrefItem GetByIndex(int i) {
        return list.getItem(i);
    }

    public int GetCount() {
        return list.getCount();
    }

    public bool Contains(String key) {
        return list.contains(key);
    }

    // Getters, Setters and Adders

    // Adders

    public bool Add(String key, int value) { // AddInteger
        return Add(key, value + "");
    }

    public bool Add(String key, double value) { // AddDouble
        return Add(key, value + "");
    }

    public bool Add(String key, bool value) { // AddBoolean
        return Add(key, value + "");
    }

    public bool Add(String key, Datas value) { // AddDatas
        return Add(key, value + "");
    }

    // Setters
    public bool Set(String key, int value) { // SetInteger
        return Set(key, value + "");
    }

    public bool Set(String key, double value) { // SetDouble
        return Set(key, value + "");
    }

    public bool Set(String key, bool value) { // SetBoolean
        return Set(key, value + "");
    }

    public bool Set(String key, Datas value) { // SetDatas
        return Set(key, value.ToString());
    }

    // Getters
    public String Get(String key) { // GetString
        return Get(key, null);
    }

    public int GetInt(String key, int ifNotFound) { // GetInteger
        try { return int.Parse(Get(key)); } 
        catch (FormatException) {
            Console.WriteLine("Key: " + key + " is not a Integer");
        }
        return ifNotFound;
    }

    public int GetInt(String key) { // GetInteger
        return GetInt(key, 0);
    }

    public double GetDouble(String key, int ifNotFound) { // GetDouble
        try { return Double.Parse(Get(key)); } catch (FormatException) {
            Console.WriteLine("Key: " + key + " is not a Double");
        }
        return ifNotFound;
    }

    public double GetDouble(String key) { // GetDouble
        return GetDouble(key, 0);
    }

    public bool GetBool(String key, bool ifNotFound) { // GetBoolean
        try { return bool.Parse(Get(key)); } 
        catch (FormatException) {
            Console.WriteLine("Key: " + key + " is not a Boolean");
        }
        return ifNotFound;
    }

    public bool GetBool(String key) { // GetBoolean
        return GetBool(key, false);
    }

    public Datas GetDatas(String key, Datas ifNotFound){
        try { return Datas.parseDatas(Get(key)); } 
        catch (FormatException) {
            Console.WriteLine("Key: " + key + " is not a Datas");
        }
        return ifNotFound;
    }

    public Datas GetDatas(String key) {
        return GetDatas(key, new Datas()); // Return a empty datas if not founded
    }

    // Getters, Setters, Adders and Removers array

    // Adders Array
    public bool Add(String [] keys, String [] values) { // AddStringArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Add(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Add(String [] keys, int [] values) { // AddIntegerArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Add(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Add(String [] keys, double [] values) { // AddDoubleArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Add(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Add(String [] keys, bool [] values) { // AddBooleanArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Add(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Add(String [] keys, Datas [] values) { // AddDatasArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Add(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    // Setters Array
    public bool SetString(String [] keys, String [] values) { // SetStringArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Set(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Set(String [] keys, int [] values) { // SetIntegerArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Set(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Set(String [] keys, double [] values) { // SetDoubleArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Set(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Set(String [] keys, bool [] values) { // SetBooleanArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Set(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    public bool Set(String [] keys, Datas [] values) { // SetDatasArray
        if (keys.Length == values.Length) {
            for (int i = 0; i < keys.Length; i++) {
                Set(keys [i], values [i]);
            }
            return true;
        }
        return false;
    }

    // Getters array
    
    public String [] Get(String [] keys, String ifNotFound){ // GetStringArray
        String [] outp = new String [keys.Length];
        for (int i = 0; i < keys.Length; i++) {
            if(list.contains(keys[i])){
                outp[i] = Get(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
    public String [] Get(String [] keys){ // GetStringArray
        return Get(keys, null);
    }

    public int [] GetInt(String [] keys, int ifNotFound) { // GetIntegerArray
        int [] outp = new int [keys.Length];
        for (int i = 0; i < keys.Length; i++) {
            if(list.contains(keys[i])){
                outp[i] = GetInt(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
     public int [] GetInt(String [] keys){ // GetIntegerArray
         return GetInt(keys, 0);
     }

     public double [] GetDouble(String [] keys, double ifNotFound) { // GetDoubleArray
        double [] outp = new double [keys.Length];
        for (int i = 0; i < keys.Length; i++) {
            if(list.contains(keys[i])){
                outp[i] = GetInt(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
    public double [] GetDouble(String [] keys){ // GetDoubleArray
        return GetDouble(keys, 0);
    }
    
    public bool [] GetBool(String [] keys, bool ifNotFound){ // GetBooleanArray
        bool [] outp = new bool [keys.Length];
        for (int i = 0; i < keys.Length; i++) {
            if(list.contains(keys[i])){
                outp[i] = GetBool(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }

    public bool [] GetBool(String [] keys) { // GetBooleanArray
        return GetBool(keys, false);
    }

    public Datas [] GetDatas(String [] keys, Datas ifNotFound) { // GetDatasArray
        Datas [] outp = new Datas[keys.Length];
        for (int i = 0; i < keys.Length; i++) {
            if(list.contains(keys[i])){
                outp[i] = GetDatas(keys[i]);
            } else {
                outp[i] = ifNotFound;
            }
        }
        return outp;
    }
    
    public Datas [] GetDatas(String [] keys){ // GetDatasArray
        return GetDatas(keys, new Datas());
    }
    
    // Removers array
    
    public void Remove(String [] keys){
        for (int i = 0; i < keys.Length; i++) {
            Remove(keys [i]);
        }
    }
    
    // Override methods

    override
    public String ToString() {
        return list.ToString();
    }
}