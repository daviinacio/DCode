package com.daviapps.dcode.exception;

/** @author Davi, 17/12/2017 */
public class ParseException extends RuntimeException {
    
    public ParseException() { }
    
    public ParseException(String msg) {
        super(msg);
    }
    
    public ParseException(Throwable cause){
        super(cause);
    }
    
    public ParseException(String msg, Throwable cause){
        super(msg,cause);
    }
}
