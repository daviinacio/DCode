using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/**
 * @author Davi
 */

public class DCodePrefItemList {
    protected List<DCodePrefItem> list;
    protected DCode dcode;

    // Constructors
    
    public DCodePrefItemList(DCode dcode, String items){
        This(dcode, ToItemArray(dcode.unCode(items)));
    }
    
    public DCodePrefItemList(DCode dcode, String [] items){
        This(dcode, ToItemArray(items));
    }
    
    public DCodePrefItemList(DCode dcode, DCodePrefItem [] items){
        This(dcode, items);
    }

    // Main constructor
    private void This(DCode dcode, DCodePrefItem [] items) {
        list = ToList(items);
        this.dcode = dcode;
    }

    // Methods

    public void addItem(String key, String value) {
        list.Add(new DCodePrefItem(key, value));
    }

    public DCodePrefItem getItem(String key) {
        DCodePrefItem [] items = list.ToArray();
        for (int i = 0; i < items.Length; i++) {
            if (items [i].getKey().Equals(key)) {
                return items [i];
            }
        }

        return null;
    }

    // The setItem method is on DCodePreferences, becouse uses the getItem();

    public bool contains(String key) {
        return (getItem(key) != null);
    }

    
    // Static methods
    
    public static String [] ToStringArray(DCodePrefItem[] item){
        String [] _item = new String[item.Length];
        for(int i = 0; i < _item.Length; i++){
            _item[i] = item[i].ToString();
        }
        return _item;
    }

    public static String [] ToStringArray(List<DCodePrefItem> items) {
        return ToStringArray(ToItemArray(items));
    }
    
    public static DCodePrefItem[] ToItemArray(String [] items){
        DCodePrefItem [] _items = new DCodePrefItem[items.Length];
        for(int i = 0; i < _items.Length; i++){
            _items[i] = new DCodePrefItem(items[i]);
        }
        return _items;
    }
    
    public static List<DCodePrefItem> ToList(DCodePrefItem [] items){
        List<DCodePrefItem> list = new List<DCodePrefItem>();
        for(int i = 0; i < items.Length; i++){
            list.Add(items[i]);
        }
        return list;
    }
    
    public static DCodePrefItem [] ToItemArray(List<DCodePrefItem> items){
        return items.ToArray();
    }
    
    // Getters and Setters
    
    public List<DCodePrefItem> getList(){
        return list;
    }
    
    // Override methods

    override
    public String ToString() {
        return dcode.enCodeI(ToStringArray(list));
    }
}