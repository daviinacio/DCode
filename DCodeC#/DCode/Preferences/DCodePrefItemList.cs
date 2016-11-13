using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class DCodePrefItemList {
    protected List<DCodePrefItem> list;
    protected DCode dcode;
    
    public DCodePrefItemList(DCode dcode, String items){
        list = toDCodePrefsItemList(toDCodePrefItemsArray(dcode.unCode(items)));
        this.dcode = dcode;
    }
    
    public DCodePrefItemList(DCode dcode, String [] items){
        list = toDCodePrefsItemList(toDCodePrefItemsArray(items));
        this.dcode = dcode;
    }
    
    public DCodePrefItemList(DCode dcode, DCodePrefItem [] items){
        list = toDCodePrefsItemList(items);
        this.dcode = dcode;
    }
    
    // Static methods
    
    public static String [] toStringArray(DCodePrefItem[] item){
        String [] _item = new String[item.Length];
        for(int i = 0; i < _item.Length; i++){
            _item[i] = item[i].ToString();
        }
        return _item;
    }
    
    public static DCodePrefItem[] toDCodePrefItemsArray(String [] items){
        DCodePrefItem [] _items = new DCodePrefItem[items.Length];
        for(int i = 0; i < _items.Length; i++){
            _items[i] = new DCodePrefItem(items[i]);
        }
        return _items;
    }
    
    public static List<DCodePrefItem> toDCodePrefsItemList(DCodePrefItem [] items){
        List<DCodePrefItem> list = new List<DCodePrefItem>();
        for(int i = 0; i < items.Length; i++){
            list.Add(items[i]);
        }
        return list;
    }
    
    public static DCodePrefItem [] toDCodePrefsItemsArray(List<DCodePrefItem> items){
        return items.ToArray();
        /*DCodePrefItem [] outp = new DCodePrefItem[items.Size()];
        for(int i = 0; i < items.size(); i++){
            outp[i] = items.get(i);
        }
        return outp;*/
    }
    
    // Getters and Setters
    
    public DCodePrefItem getItem(String key){
        DCodePrefItem [] items = list.ToArray();
        for(int i = 0; i < items.Length; i++){
            if(items[i].getKey().Equals(key)){
                return items [i];
            }
        }
        
        return null;
    }
    
    public List<DCodePrefItem> getList(){
        return list;
    }
    
    // Override methods

    override
    public String ToString() {
        return dcode.enCodeI(toStringArray(toDCodePrefsItemsArray(list)));
    }
}