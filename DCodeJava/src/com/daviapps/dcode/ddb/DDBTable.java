package com.daviapps.dcode.ddb;

import com.daviapps.dcode.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Davi
 */
public class DDBTable {
    private static DCode dc_base = new DCode(DCode.FILE);
    private static DCode dc_struct = new DCode(DCode.ARRAY);
    private static DCode dc_data = new DCode(DCode.NORMAL);
    // Variables
    public List<List<String>> data;
    private List<String> columns;
    private DCodeFile file;
    
    public DDBTable(String file){
        this.file = new DCodeFile(file){
            @Override
            public void onAlright() {
                data = new ArrayList<>();
                columns = new ArrayList<>();
                // Load base
                String [] a_base = dc_base.unCode(this.getText());
                
                // Load struct
                String [] a_column = dc_struct.unCode(a_base[0]);
                String [] a_data = dc_struct.unCode(a_base[1]);
                
                for (String c : a_column)
                    columns.add(c);
                
                // Load data
                for (String d : a_data) {
                    String [] a_infos = dc_data.unCode(d);
                    List<String> l_infos = new ArrayList<>();
                    
                    for (String f : a_infos) {
                        l_infos.add(f);
                    }
                    
                    data.add(l_infos);
                }
            }
        };
    }
    
    public DDBTable(DCodeFile file){
        this.file = file;
    }
}
