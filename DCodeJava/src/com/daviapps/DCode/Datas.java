package com.daviapps.DCode;

/**
 * @author Davi
 */

public class Datas {
    protected DCode dcode = new DCode('(', '/', ')');

    // Private variables
    protected int day, month;
    protected String year;

    // Constructors
    
    public Datas(int day, int month, String year){
        this.day = day; this.month = month; this.year = year;
    }

    public Datas(){
        day = 0; month = 0; year = "1999";
        this.setToday();
    }

    public Datas(String properties){
        String [] props = new DCode('(', '/', ')').unCode(properties);
        day = Integer.parseInt(props[0]);
        month = Integer.parseInt(props[1]);
        year = props[2];
    }
    
    // Methods

    public void setToday(){

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
        String [] props = {"" + data.getDay(), "" + data.getMonth(), data.getYear()};
        return data.dcode.enCodeI(props);
    }

    // Override methods

    //Override
    public String toString(){
        String [] props = {"" + day, "" + month, year};
        return dcode.enCodeI(props);
    }
}
