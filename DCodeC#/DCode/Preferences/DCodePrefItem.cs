using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/**
 * @author Davi
 */

public class DCodePrefItem {
    protected DCode dcode = new DCode();
    protected String key, value;
    
    public DCodePrefItem(String key, String value){
        this.key = key; this.value = value;
    }
    
    public DCodePrefItem(String props){
        String [] _props = dcode.unCode(props);
        this.key = _props[0];
        this.value = _props[1];
    }
    
    // Getters and Setters

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    // Override methods

    override
    public String ToString() {
        return dcode.enCodeI(new String[] {key, value});
    }
}
