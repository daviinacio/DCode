/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daviapps.dcode;

import java.util.Calendar;

/**
 *
 * @author Davi
 */
public class Horas {
    protected final static DCode dcode = new DCode(DCode.ARRAY);

    // Private variables
    protected int hours, minutes, seconds;
    
    protected Calendar calendar;
    
    // Constructors

    public Horas() {
        setNow();
    }
    
    public Horas(String props){
        String [] _props = dcode.unCode(props);
        if(_props.length >= 3){
            this.hours = Integer.getInteger(_props[0]);
            this.minutes = Integer.getInteger(_props[1]);
            this.seconds = Integer.getInteger(_props[2]);
        }
    }

    public Horas(int hours, int minutes, int seconds) {
        this.hours = hours; this.minutes = minutes; this.seconds = seconds;
    }
    
    public Horas(int hours, int minutes) {
        this.hours = hours; this.minutes = minutes; this.seconds = 0;
    }
    
    // Init methods
    
    private void initCalendar(){
        calendar = Calendar.getInstance();
    }
    
    // Methods

    public void setNow(){
        initCalendar();
        this.hours = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.seconds = calendar.get(Calendar.SECOND);
    }
    
    // Static methods
    
    public static Horas parceHoras(String hora){
        return new Horas(hora);
    }
    
    public static String toString(Horas hora){
        return hora.dcode.enCode(new String[] {"" + hora.hours, "" + hora.minutes, "" + hora.seconds});
    }
    
    // Override methods

    @Override
    public String toString() {
        return Horas.toString(this);
    }
    
}

