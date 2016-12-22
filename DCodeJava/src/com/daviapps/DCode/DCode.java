package com.daviapps.DCode;

/**
 * @author Davi
 */

public class DCode {
    protected char open, space, close;
    // DCode modes
    public static int UNKNOWN = -1, NORMAL = 0, DATAs = 1, ARRAY = 2, FILE = 3, SMALL = 4;

    // Constructors
    
    public DCode(){ // For use static methods
        this(NORMAL);
    }

    public DCode(char open, char space, char close){
        this.open = open; this.space = space; this.close = close;
    }
    
    public DCode(int mode){
        if(mode == DCode.NORMAL || mode == DCode.UNKNOWN){
            open = '<'; space = ';'; close = '>';
        } else
        if(mode == DCode.DATAs){
            open = '('; space = '/'; close = ')';
        } else
        if(mode == DCode.ARRAY){
            open = '['; space = ':'; close = ']';
        } else
        if(mode == DCode.FILE){
            open = '{'; space = '_'; close = '}';
        } else
        if(mode == DCode.SMALL) {
            open = '.'; space = ','; close = '#';
        }
    }
    
    // enCode and unCode

    public String [] unCode(String in){
        String [] out = new String[lenght(in)];
        char [] _in = in.toCharArray();
        String word = "";
        boolean noOpen = DCode.countNum(in, open) < 1;
        boolean noClose = DCode.countNum(in, close) < 1;
        
        int opend = 0, x = 0;
        
        for(int i = 0; i < _in.length; i++){

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
                
                out[x] = word;
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

        return out;
    }

    public String [][] unCode(DCode dcode1, String in){
        String [] _out = unCode(in);
        String [][] out = new String[_out.length][];

        for(int i = 0; i < _out.length; i++)
            out[i] = dcode1.unCode(_out[i]);

        return out;
    }

    public String [][][] unCode(DCode dcode1, DCode dcode2, String in){
        String [][] _out = unCode(dcode1, in);
        String [][][] out = new String[_out.length][][];

        for(int i = 0; i < _out.length; i++){
            out[i] = new String[_out[i].length][];
            for(int j = 0; j < _out[i].length; j++)
                out[i][j] = unCode(_out[i][j]);
        }

        return out;
    }

    //Temporary function
    @Deprecated
    public String enCodeI(String [] in){
        String out = "" + open;

        for(int i = 0; i < in.length; i++){
            out = out + in[i] + space;
        }

        out = out + close;

        return out;
    }
    
    public String enCode(String [] in){ // Unidimencional
        return enCode(this, in);
    }
    
    public String enCode(String [][] in){ // Bidimencional
        return enCode(this, in);
    }
    
    public String enCode(DCode dcode1, String [][] in){ // Bidimencional
        return enCode(this, dcode1, in);
    }
    
    public String enCode(String [][][] in){ // Tridimencional
        return enCode(this, in);
    }
    
    public String enCode(DCode dcode2, String [][][] in){ // Tridimencional
        return enCode(this, dcode2, in);
    }
    
    public String enCode(DCode dcode1, DCode dcode2, String [][][] in){ // Tridimencional
        return enCode(this, dcode1, dcode2, in);
    }
    
    // Encode Method
    
    protected String enCode(DCode dcode, String [] in){ // Unidimencional
        String out = "" + dcode.open;

        for(int i = 0; i < in.length; i++)
            out = out + in[i] + dcode.space;

        out = out + dcode.close;

        return out;
    }
    
    protected String enCode(DCode dcode1, DCode dcode2, String [][] in){ // Bidimencional
        String out = "" + dcode1.open;

        for(int i = 0; i < in.length; i++)
            out = out + enCode(dcode2, in[i]) + dcode1.space;

        out = out + dcode1.close;

        return out;
    }
    
    
    protected String enCode(DCode dcode1, DCode dcode2, DCode dcode3, String [][][] in){ // Tridimencional
        String out = "" + dcode1.open;

        for(int i = 0; i < in.length; i++)
            out = out + enCode(dcode2, dcode3, in[i]) + dcode1.space;

        out = out + dcode1.close;

        return out;
    }
    
    public int lenght(String in){
        int out = 0;
        char [] _in = in.toCharArray();
        boolean noOpen = DCode.countNum(in, open) < 1;
        boolean noClose = DCode.countNum(in, close) < 1;
        int opend = 0;

        for(int i = 0; i < _in.length; i++){

            if(_in[i] == open){
                opend++;
            } else
            if(_in[i] == space){
                if(opend > 1){
                    continue;
                } else
                if(opend < 1 && !noOpen)
                    continue;
                else
                if(opend < 0 && !noClose)
                    continue;
                out++;
            } else
            if(_in[i] == close){
                opend--;
            } else {
                if(_in[i] == '\r') continue;
            }
        }
        
        return out;
    }
    
    @Deprecated
    public boolean verify(String in){
        int opens = countNum(in, this.open);
        int spaces = countNum(in, this.space);
        int closes = countNum(in, this.close);
        
        //int dimention = dimentions(in);
        //int length = this.unCode(in).length;
        
        boolean isAlReady = (
                    opens == closes
                );
        
        return isAlReady;
    }
    
    // Getters and Setters
    
    public int getMode(){ // To use this methods, the string most be some itens
        return DCode.getMode(this.enCode(new String[] {"Dcode"}));
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
    
    // Internal methods

    @Deprecated
    protected int dimentions(String in){
        return DCode.dimentions(this, in);
    }

    // Static methods
    
    public static int getMode(String in){ // To use this methods, the string most be some itens
        char [] _in = in.toCharArray();
        DCode [] dCodes ={
            new DCode(NORMAL), new DCode(DATAs), new DCode(ARRAY),
            new DCode(FILE), new DCode(SMALL)
        };
        
        for (int i = (_in.length -1); i >= 0; i--) {
            //System.out.println("i" + i + " - " + _in[i]);
            for (int j = 0; j < dCodes.length; j++) {
                //System.out.println("    j" + j + " - " + "c: " + dCodes[j].getClose()+ ", s: " + dCodes[j].getSpace());
                if(_in[i] == dCodes[j].getClose()) // Find last close
                    return j;
                if(_in[i] == dCodes[j].getSpace()) // Find last space
                    return j;
            }
        }
        
        return UNKNOWN;
    }

    @Deprecated
    public static int dimentions(DCode dcode, String in){
        int out = 0;
        char [] _in = in.toCharArray();
        int opend = 0;

        for(int i = 0; i < _in.length; i++){

            if(_in[i] == dcode.open){
                opend++;
                if(opend > out) out = opend;
            } else
            if(_in[i] == dcode.close){
                opend--;
            } else {

            }
        }

        return out;
    }
    
    public static int countNum(String in, char c){
        int out = 0;
        char [] _in = in.toCharArray();

        for(int i = 0; i < _in.length; i++){
            if(_in[i] == c) out++;
        }

        return out;
    }
    
    public static int lenght(DCode dcode, String in){ // This can generate a infinity loop with uncode
        return dcode.unCode(in).length;
    }
}
