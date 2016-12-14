package com.daviapps.DCode;

/**
 * @author Davi
 */

public class DCode {
    protected char open, space, close;

    public DCode(){ // For use static methods
        open = '<'; space = ';'; close = '>';
    }

    public DCode(char open, char space, char close){
        this.open = open; this.space = space; this.close = close;
    }

    public String [] unCode(String in){
        String [] out = new String[lenght(in)];
        char [] _in = in.toCharArray();
        String word = "";
        int opend = 0;

        int x = 0;
        for(int i = 0; i < _in.length; i++){

            if(_in[i] == open){
                opend++;
                if(opend > 1) word = word + _in[i];
            } else
            if(_in[i] == space){
                if(opend > 1){
                    word = word + _in[i];
                    continue;
                }
                out[x] = word;
                x++;
                word = "";
            } else
            if(_in[i] == close){
                if(opend > 1) word = word + _in[i];
                opend--;
            } else{
                if(_in[i] == '\r' || opend < 1) continue;
                word = word + _in[i];
            }
        }

        return out;
    }

    public String [][] unCode(DCode dcode1, String in){
        String [] _out = unCode(in);
        String [][] out = new String[_out.length][];

        for(int i = 0; i < _out.length; i++){
            out[i] = dcode1.unCode(_out[i]);
        }

        return out;
    }

    public String [][][] unCode(DCode dcode1, DCode dcode2, String in){
        String [][] _out = unCode(dcode1, in);
        String [][][] out = new String[_out.length][][];

        for(int i = 0; i < _out.length; i++){
            out[i] = new String[_out[i].length][];
            for(int j = 0; j < _out[i].length; j++){
                out[i][j] = unCode(_out[i][j]);
            }
        }

        return out;
    }

    //Temporary function
    public String enCodeI(String [] in){
        String out = "" + open;

        for(int i = 0; i < in.length; i++){
            out = out + in[i] + space;
        }

        out = out + close;

        return out;
    }
    
    public String enCode(String [] in){
        return enCode(this, in);
    }
    
    public String enCode(String [][] in){
        return enCode(this, in);
    }
    
    public String enCode(DCode dcode1, String [][] in){
        return enCode(this, dcode1, in);
    }
    
    public String enCode(String [][][] in){
        return enCode(this, in);
    }
    
    public String enCode(DCode dcode2, String [][][] in){
        return enCode(this, dcode2, in);
    }
    
    public String enCode(DCode dcode1, DCode dcode2, String [][][] in){
        return enCode(this, dcode1, dcode2, in);
    }
    
    // Encode Method
    
    protected String enCode(DCode dcode, String [] in){ // Unidimencional
        String out = "" + dcode.open;

        for(int i = 0; i < in.length; i++){
            out = out + in[i] + dcode.space;
        }

        out = out + dcode.close;

        return out;
    }
    
    protected String enCode(DCode dcode1, DCode dcode2, String [][] in){ // Bidimencional
        String out = "" + dcode1.open;

        for(int i = 0; i < in.length; i++){
            out = out + enCode(dcode2, in[i]) + dcode1.space;
        }

        out = out + dcode1.close;

        return out;
    }
    
    protected String enCode(DCode dcode1, DCode dcode2, DCode dcode3, String [][][] in){ // Tridimencional
        String out = "" + dcode1.open;

        for(int i = 0; i < in.length; i++){
            out = out + enCode(dcode2, dcode3, in[i]) + dcode1.space;
        }

        out = out + dcode1.close;

        return out;
    }

    public int lenght(String in){
        int out = 0;
        char [] _in = in.toCharArray();
        int opend = 0;

        for(int i = 0; i < _in.length; i++){

            if(_in[i] == open){
                opend++;
            } else
            if(_in[i] == space){
                if(opend > 1){
                    continue;
                }
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
    
    // Internal methods

    protected int dimentions(String in){
        return DCode.dimentions(this, in);
    }

    // Static methods

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
}
