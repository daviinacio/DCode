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
    public static int UNKNOWN = -1, NORMAL = 0, DATAs = 1, ARRAY = 2, FILE = 3, SMALL = 4;

    public DCode() { // For use static methods
        open = '<'; space = ';'; close = '>';
    }

    public DCode(char open, char space, char close) {
        this.open = open; this.space = space; this.close = close;
    }

    public DCode(int mode) {
        if (mode == DCode.NORMAL || mode == DCode.UNKNOWN) {
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
        } else
        if (mode == DCode.SMALL) {
            open = '.'; space = ','; close = '#';
        }
    }

    // enCode and unCode

    public String [] unCode(String inp) {
        String [] outp = new String[lenght(inp)];
        char [] _in = inp.ToCharArray();
        String word = "";
        bool noOpen = DCode.countNum(inp, open) < 1;
        bool noClose = DCode.countNum(inp, close) < 1;
        
        int opend = 0, x = 0;
        
        for(int i = 0; i < _in.Length; i++){

            if(_in[i] == open){
                opend++;
                if(opend > 1) word = word + _in[i];
            } else
            if(_in[i] == space){
                if(opend > 1){
                    word = word + _in[i];
                    continue;
                } else
                if(opend < 1 && !noOpen)
                    continue;
                else
                if(opend < 0 && !noClose)
                    continue;
                
                outp[x] = word;
                x++;
                word = "";
            } else
            if(_in[i] == close){
                if(opend > 1) word = word + _in[i];
                opend--;
            } else{
                if(_in[i] == '\r' || (opend < 1 && !noOpen) || (opend < 0 && !noClose)) continue;
                word = word + _in[i];
            }
        }

        return outp;
    }

    public String [][] unCode(DCode dcode1, String inp) {
        String [] _out = unCode(inp);
        String [] [] outp = new String [_out.Length] [];

        for (int i = 0; i < _out.Length; i++) 
            outp [i] = dcode1.unCode(_out [i]);

        return outp;
    }

    public String [][][] unCode(DCode dcode1, DCode dcode2, String inp) {
        String [][] _out = unCode(dcode1, inp);
        String [][][] outp = new String [_out.Length][][];

        for (int i = 0; i < _out.Length; i++) {
            outp [i] = new String [_out [i].Length] [];
            for (int j = 0; j < _out [i].Length; j++)
                outp [i] [j] = unCode(_out [i] [j]);
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

        for(int i = 0; i < inp.Length; i++)
            outp = outp + inp[i] + dcode.space;

        outp = outp + dcode.close;

        return outp;
    }
    
    private String enCode(DCode dcode1, DCode dcode2, String [][] inp){ // Bidimencional
        String outp = "" + dcode1.open;

        for(int i = 0; i < inp.Length; i++)
            outp = outp + enCode(dcode2, inp[i]) + dcode1.space;

        outp = outp + dcode1.close;

        return outp;
    }
    
    private String enCode(DCode dcode1, DCode dcode2, DCode dcode3, String [][][] inp){ // Tridimencional
        String outp = "" + dcode1.open;

        for(int i = 0; i < inp.Length; i++)
            outp = outp + enCode(dcode2, dcode3, inp[i]) + dcode1.space;

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

    public char getOpen() {
        return open;
    }

    public char getSpace() {
        return space;
    }

    public char getClose() {
        return close;
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
        char [] _in = inp.ToCharArray();
        DCode [] dCodes ={
            new DCode(NORMAL), new DCode(DATAs), new DCode(ARRAY),
            new DCode(FILE), new DCode(SMALL)
        };
        
        for (int i = (_in.Length -1); i >= 0; i--) {
            //System.out.println("i" + i + " - " + _in[i]);
            for (int j = 0; j < dCodes.Length; j++) {
                //System.out.println("    j" + j + " - " + "c: " + dCodes[j].getClose()+ ", s: " + dCodes[j].getSpace());
                if(_in[i] == dCodes[j].getClose()) // Find last close
                    return j;
                if(_in[i] == dCodes[j].getSpace()) // Find last space
                    return j;
            }
        }
        
        return UNKNOWN;
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
