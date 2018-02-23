package com.daviapps.dcode;

import java.util.Calendar;

/** @author Davi, 10/11/2016 */
@Deprecated
public class Datas {
    protected final DCode dcode = new DCode(DCode.DATAs);

    // Private variables
    protected int day, month;
    protected String year;
    protected Horas time;
    
    protected Calendar calendar;
    
    // Init methods
    
    private void initCalendar(){
        calendar = Calendar.getInstance();
    }

    // Constructors
    
    public Datas(){
        //this(0, 0, "1999");
        this.setToday();
        this.time = new Horas();
    }

    public Datas(String properties){
        String [] props = dcode.unCode(properties);
        if(props.length >= 3){
            day = Integer.parseInt(props[0]);
            month = Integer.parseInt(props[1]);
            year = props[2];
        }
        if(props.length >= 4){
            time = new Horas(props[3]);
        }
    }
    
    public Datas(int day, int month, String year){
        this.day = day; this.month = month; this.year = year; //this.time = null;
    }
    
    public Datas(int day, int month, String year, int hours, int minutes, int seconds){
        this.day = day; this.month = month; this.year = year; this.time = new Horas(hours, minutes, seconds);
    }
    
    public Datas(int day, int month, String year, int hours, int minutes){
        this.day = day; this.month = month; this.year = year; this.time = new Horas(hours, minutes);
    }
    
    public Datas(int day, int month, int hours, int minutes){
        initCalendar();
        this.day = day; this.month = month; this.time = new Horas(hours, minutes);
        this.year = this.calendar.get(Calendar.YEAR) + ""; 
    }
    
    // Methods

    public void setToday(){
        initCalendar();
        this.day = calendar.get(Calendar.DAY_OF_MONTH) -1;
        this.month = calendar.get(Calendar.MONTH);
        this.year = calendar.get(Calendar.YEAR) + "";
    }
    
    // Getters and Setters

    public int getDay(){
        return day;
    }
    public void setDay(int day){
        this.day = day;
    }

    public int getMonth(){
        return month;
    }
    public void setMonth(int month){
        this.month = month;
    }

    public String getYear(){
        return year;
    }
    public void setYear(String year){
        this.year = year;
    }

    public Horas getTime() {
        return time;
    }

    public void setTime(Horas time) {
        this.time = time;
    }

    // Static methods

    public static Datas parseDatas(String data){
        return new Datas(data);
    }
    
    public  static  String toString(Datas data){
        if(data.getTime() == null)
            return data.dcode.enCode(new String[] {"" + data.getDay(), "" + data.getMonth(), data.getYear()});
        else
            return data.dcode.enCode(new String[] {"" + data.getDay(), "" + data.getMonth(), data.getYear(), data.getTime().toString()});
    }

    // Override methods

    @Override
    public String toString(){
        return toString(this);
    }
}
