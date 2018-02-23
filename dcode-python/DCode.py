class DCode:
    # DCode modes
    UNKNOWN = -1; NORMAL = 0; DATAs = 1; ARRAY = 2; FILE = 3; SMALL = 4;
    
    def __init__(self, open, space, close):
         self.open = open
         self.space = space
         self.close = close
         
    def __init__(self, mode):
        if mode == DCode.NORMAL or mode == DCode.UNKNOWN:
             self.open = '<'; self.space = ';'; self.close = '>';
        elif mode == DCode.DATAs:
             self.open = '('; self.space = '/'; self.close = ')';
        elif mode == DCode.ARRAY:
             self.open = '['; self.space = ':'; self.close = ']';
        elif mode == DCode.FILE:
             self.open = '{'; self.space = '_'; self.close = '}';
        elif mode == DCode.SMALL:
             self.open = '.'; self.space = ','; self.close = '#';

    # enCode and unCode

    def unCode(self, inp):
        out = [];
        _inp = list(inp);
        word = "";
        noOpen = DCode.countNum(inp, self.open) < 1;
        noClose = DCode.countNum(inp, self.close) < 1;

        opend = 0; x = 0;
        
        for i in range(0, len(_inp)):
            if(_inp[i] == self.open):
                opend += 1;
                if(opend > 1): word = word + _inp[i];
            elif(_inp[i] == self.space):
                if(opend > 1):
                    word = word + _inp[i];
                    continue;
                elif(opend < 1 and not noOpen):
                    continue;
                elif(opend < 0 and not noClose):
                    continue;
                
                out[x] = word;
                x += 1;
                word = "";
            elif(_inp[i] == self.close):
                if(opend > 1): word = word + _inp[i];
                opend -= 1;
            else :
                if(_inp[i] == '\r' or (opend < 1 and not noOpen) or (opend < 0 and not noClose)): continue;
                word = word + _inp[i];
            
        return out;

    # Static methods

    def countNum(inp, c):
        out = 0;
        _inp = list(inp);

        for i in range(0, len(_inp)):
            if(_inp[i] == c): out += 1;

        return out;

x =  DCode(DCode.NORMAL)
