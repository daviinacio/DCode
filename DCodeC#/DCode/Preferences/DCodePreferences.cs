using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class DCodePreferences {
    private DCodeFile file;
    private DCode dcode = new DCode('[', ':', ']');
    private DCodePrefItemList list;
    
    /*public DCodePreferences(String file){
        this(new DCodeFile(file));
    }*/
    
    public DCodePreferences(DCodeFile file){
        this.file = file;
        if(file.getStatusKey() == DCodeFile.ALRIGHT){
            Console.WriteLine("Alright");
            list = new DCodePrefItemList(dcode, file.getText());
        } else
        if(file.getStatusKey() == DCodeFile.EMPTY){
            Console.WriteLine("Empty");
            file.createBaseFile();
            Console.WriteLine("Base file created");
        } else
        if(file.getStatusKey() == DCodeFile.ERROR){
            Console.WriteLine("Error");
        }
    }
    
    // Loader methods
    
    public void save(){
        file.setText(list.ToString());
    }
    
    // Getters and setters
    
    public bool set(String key, String value){
        if(list.getItem(key) != null){
            list.getItem(key).setValue(value);
            return true;
        }
        return false;
    }
    
    public String get(String key){
        return get(key, null);
    }
    
    public String get(String key, String ifNotFound){
        if(list.getItem(key) != null) // If item did found
            return list.getItem(key).getValue();
        return ifNotFound;
    }
}
