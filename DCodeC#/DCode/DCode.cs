using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/**
 * @author Davi
 */

public class DCode {
    private char open, space, close;
    // DCode modes
    public static int NORMAL = 0, DATAs = 1, ARRAY = 2, FILE = 3;

    public DCode() { // For use static methods
        open = '<'; space = ';'; close = '>';
    }

    public DCode(char open, char space, char close) {
        this.open = open; this.space = space; this.close = close;
    }

    public DCode(int mode) {
        if (mode == DCode.NORMAL) {
            open = '<'; space = ';'; close = '>';
        } else
        if (mode == DCode.DATAs) {
            open = '('; space = '/'; close = ')';
        } else
        if (mode == DCode.ARRAY) {
            open = '['; space = ':'; close = ']';
        } else
        if (mode == DCode.FILE) {
            open = '{'; space = '_'; close = '}';
        }
    }

    // enCode and unCode

    public String [] unCode(String inp) {
        String [] outp = new String [this.lenght(inp)];
        char [] _in = inp.ToCharArray();
        String word = "";
        int opend = 0;

        int x = 0;
        for (int i = 0; i < _in.Length; i++) {

            if (_in [i] == open) {
                opend++;
                if (opend > 1) word = word + _in [i];
            } else
            if (_in [i] == space) {
                if (opend > 1) {
                    word = word + _in [i];
                    continue;
                }
                outp [x] = word;
                x++;
                word = "";
            } else
            if (_in [i] == close) {
                if (opend > 1) word = word + _in [i];
                opend--;
            } else {
                if (_in [i] == '\r' || opend < 1) continue;
                word = word + _in [i];
            }
        }

        return outp;
    }

    public String [][] unCode(DCode dcode1, String inp) {
        String [] _out = unCode(inp);
        String [] [] outp = new String [_out.Length] [];

        for (int i = 0; i < _out.Length; i++) {
            outp [i] = dcode1.unCode(_out [i]);
        }

        return outp;
    }

    public String [][][] unCode(DCode dcode1, DCode dcode2, String inp) {
        String [][] _out = unCode(dcode1, inp);
        String [][][] outp = new String [_out.Length][][];

        for (int i = 0; i < _out.Length; i++) {
            outp [i] = new String [_out [i].Length] [];
            for (int j = 0; j < _out [i].Length; j++) {
                outp [i] [j] = unCode(_out [i] [j]);
            }
        }

        return outp;
    }

    //Temporary function
    //@Deprecated
    public String enCodeI(String [] inp) {
        String outp = "" + open;

        for (int i = 0; i < inp.Length; i++) {
            outp = outp + inp [i] + space;
        }

        outp = outp + close;

        return outp;
    }

    public String enCode(String [] inp){ // Unidimencional
        return enCode(this, inp);
    }

    public String enCode(String [,] inp) { // Bidimencional
        return enCode(convertString(inp));
    }
    
    public String enCode(String [][] inp){ // Bidimencional
        return enCode(this, inp);
    }

    public String enCode(DCode dcode1, String [,] inp) { // Bidimencional
        return null;// enCode(dcode1, convertString(inp));
    }

    public String enCode(DCode dcode1, String [][] inp){ // Bidimencional
        return enCode(this, dcode1, inp);
    }

    public String enCode(String [,,] inp) { // Tridimencional
        return enCode(convertString(inp));
    }
    
    public String enCode(String [][][] inp){ // Tridimencional
        return enCode(this, inp);
    }

    public String enCode(DCode dcode2, String [,,] inp) { // Tridimencional
        return enCode(dcode2, convertString(inp));
    }
    
    public String enCode(DCode dcode2, String [][][] inp){ // Tridimencional
        return enCode(this, dcode2, inp);
    }

    public String enCode(DCode dcode1, DCode dcode2, String [,,] inp) { // Tridimencional
        return enCode(dcode1, dcode2, convertString(inp));
    }
    
    public String enCode(DCode dcode1, DCode dcode2, String [][][] inp){ // Tridimencional
        return enCode(this, dcode1, dcode2, inp);
    }
    
    // Encode Method
    
    private String enCode(DCode dcode, String [] inp){ // Unidimencional
        String outp = "" + dcode.open;

        for(int i = 0; i < inp.Length; i++){
            outp = outp + inp[i] + dcode.space;
        }

        outp = outp + dcode.close;

        return outp;
    }
    
    private String enCode(DCode dcode1, DCode dcode2, String [][] inp){ // Bidimencional
        String outp = "" + dcode1.open;

        for(int i = 0; i < inp.Length; i++){
            outp = outp + enCode(dcode2, inp[i]) + dcode1.space;
        }

        outp = outp + dcode1.close;

        return outp;
    }
    
    private String enCode(DCode dcode1, DCode dcode2, DCode dcode3, String [][][] inp){ // Tridimencional
        String outp = "" + dcode1.open;

        for(int i = 0; i < inp.Length; i++){
            outp = outp + enCode(dcode2, dcode3, inp[i]) + dcode1.space;
        }

        outp = outp + dcode1.close;

        return outp;
    }

    public int lenght(String inp) {
        int outp = 0;
        char [] _in = inp.ToCharArray();
        int opend = 0;

        for (int i = 0; i < _in.Length; i++) {

            if (_in [i] == open) {
                opend++;
            } else
            if (_in [i] == space) {
                if (opend > 1) {
                    continue;
                }
                outp++;
            } else
            if (_in [i] == close) {
                opend--;
            } else {
                if (_in [i] == '\r') continue;
            }
        }

        return outp;
    }

    public bool verify(String inp){
        int opens = countNum(inp, this.open);
        int spaces = countNum(inp, this.space);
        int closes = countNum(inp, this.close);
        
        //int dimention = dimentions(in);
        //int length = this.unCode(in).length;
        
        bool isAlReady = (
                    opens == closes
                );
        
        return isAlReady;
    }
    
    // Getters and Setters
    
    public int getMode(){ // To use this methods, the string most be some itens
        return DCode.getMode(this.enCode(new String[] {"Dcode", ""}));
    }

    // String methods

    private String [][] convertString(String [,] inp){
        String [][] outp = new String [inp.Length][];

        for (int i = 0; i < outp.Length; i++) {
            //String uniDimen = new String[inp[i].Length];
        }

        return outp;
    }

    private String [] [] [] convertString(String [, ,] inp) {
        return null;
    }

    private String [,] convertString(String [][] inp) {
        return null;
    }

    private String [,,] convertString(String [][][] inp) {
        return null;
    }

    // Internal methods

    //@Deprecated
    protected int dimentions(String inp) {
        return DCode.dimentions(this, inp);
    }

    // Static methods

    public static int getMode(String inp){ // To use this methods, the string most be some itens
        String [] normal = new DCode(NORMAL).unCode(inp);
        String [] datas = new DCode(DATAs).unCode(inp);
        String [] array = new DCode(ARRAY).unCode(inp);
        String [] file = new DCode(FILE).unCode(inp);
        
        if(normal.Length > 0)
            return NORMAL;
        else
        if(datas.Length > 0)
            return DATAs;
        else
        if(array.Length > 0)
            return ARRAY;
        else
        if(file.Length > 0)
            return FILE;
        
        return NORMAL;
    }

    //@Deprecated
    public static int dimentions(DCode dcode, String inp) {
        int outp = 0;
        char [] _in = inp.ToCharArray();
        int opend = 0;

        for (int i = 0; i < _in.Length; i++) {

            if (_in [i] == dcode.open) {
                opend++;
                if (opend > outp) outp = opend;
            } else
            if (_in [i] == dcode.close) {
                opend--;
            } else {

            }
        }

        return outp;
    }

    //@Deprecated
    public static int countNum(String inp, char c) {
        int outp = 0;
        char [] _in = inp.ToCharArray();

        for (int i = 0; i < _in.Length; i++) {
            if (_in [i] == c) outp++;
        }

        return outp;
    }

    public static int lenght(DCode dcode, String inp){ // This can generate a infinity loop with uncode
        return dcode.unCode(inp).Length;
    }
}
