using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/**
 * @author Davi
 */

public class DCodePreferences {
    private DCodeFile file;
    private DCode dcode = new DCode('[', ':', ']');
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
        if (file.getStatusKey() == DCodeFile.ALRIGHT) {
            Console.WriteLine("Alright");
            list = new DCodePrefItemList(dcode, file.getText());
        } else
            if (file.getStatusKey() == DCodeFile.EMPTY) {
                Console.WriteLine("Empty");
                file.createBaseFile();
                Console.WriteLine("Base file created");
            } else
                if (file.getStatusKey() == DCodeFile.ERROR) {
                    Console.WriteLine("Error");
                }
    }

    // Loader methods

    public void save() {
        file.setText(list.ToString());
    }

    // Methods

    public bool Add(String key, String value) {
        if (!list.contains(key)) {
            list.addItem(key, value);
            return true;
        }
        return false; // If this item exists
    }

    public bool Set(String key, String value) {
        if (list.contains(key)) {
            list.getItem(key).setValue(value);
            return true;
        }
        return false;
    }

    public String Get(String key, String ifNotFound) {
        if (list.contains(key)) // If item did found
            return list.getItem(key).getValue();
        return ifNotFound;
    }

    // Getters, Setters and Adders

    // Adders

    public bool Add(String key, int value) { // AddInteger
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

    public int GetInt(String key, int ifNotFound) {
        try { return int.Parse(Get(key)); } 
        catch (FormatException) {
            Console.WriteLine("Key: " + key + " is not a Integer");
        }
        return ifNotFound;
    }

    public int GetInt(String key) {
        return GetInt(key, 0);
    }

    public bool GetBool(String key, bool ifNotFound){
        try { return bool.Parse(Get(key)); } 
        catch (FormatException) {
            Console.WriteLine("Key: " + key + " is not a Boolean");
        }
        return ifNotFound;
    }

    public bool GetBool(String key) {
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

    // Getters, Setters and Adders array

    // Adders
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

    // Setters
    public bool Set(String [] keys, String [] values) { // SetStringArray
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
}