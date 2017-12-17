package com.daviapps.dcode.exception;

/** @author Davi, 17/12/2017 */
public class AddException extends RuntimeException {
    
    public AddException() {
        
    }
    public AddException(String msg) {
        super(msg);
    }
    
    public AddException(Throwable cause){
        super(cause);
    }
    
    public AddException(String msg, Throwable cause){
        super(msg,cause);
    }
}
