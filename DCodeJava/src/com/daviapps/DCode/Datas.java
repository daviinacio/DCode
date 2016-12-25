package com.daviapps.dcode;

import java.util.Calendar;

/**
 * @author Davi, 10/11/2016
 */

public class Datas {
    protected final DCode dcode = new DCode(DCode.DATAs);

    // Private variables
    protected int day, month;
    protected String year;
    
    protected Calendar calendar;
    
    // Init methods
    
    private void initCalendar(){
        calendar = Calendar.getInstance();
    }

    // Constructors
    
    public Datas(){
        this(0, 0, "1999");
        this.setToday();
    }

    public Datas(String properties){
        String [] props = dcode.unCode(properties);
        day = Integer.parseInt(props[0]);
        month = Integer.parseInt(props[1]);
        year = props[2];
    }
    
    public Datas(int day, int month, String year){
        this.day = day; this.month = month; this.year = year;
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

    // Static methods

    public static Datas parseDatas(String data){
        return new Datas(data);
    }
    
    public  static  String toString(Datas data){
        return data.dcode.enCode(new String[] {"" + data.getDay(), "" + data.getMonth(), data.getYear()});
    }

    // Override methods

    @Override
    public String toString(){
        return toString(this);
    }
}
