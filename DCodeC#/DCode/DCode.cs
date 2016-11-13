using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class DCode {
    private char open, space, close;

    public DCode() { // For use static methods
        open = '<'; space = ';'; close = '>';
    }

    public DCode(char open, char space, char close) {
        this.open = open; this.space = space; this.close = close;
    }

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

    public String enCodeI(String [] inp) {
        String outp = "" + open;

        for (int i = 0; i < inp.Length; i++) {
            outp = outp + inp [i] + space;
        }

        outp = outp + close;

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

    // Internal methods

    protected int dimentions(String inp) {
        return DCode.dimentions(this, inp);
    }

    // Static methods

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

    public static int countNum(String inp, char c) {
        int outp = 0;
        char [] _in = inp.ToCharArray();

        for (int i = 0; i < _in.Length; i++) {
            if (_in [i] == c) outp++;
        }

        return outp;
    }
}
