using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Globalization;

/**
 * @author Davi
 */

public class Datas {
    private static DCode dcode = new DCode(DCode.DATAs);

    // Private variables
    private int day, month;
    private String year;
    private Horas time;
    
    private DateTime today;
    
    // Init methods
    
    private void initCalendar(){
        today = DateTime.Today;
    }

    // Constructors
    
    public Datas(){
        this.day = 0; this.month = 0; this.year = "1999";
        this.setToday();
        this.time = new Horas();
    }

    public Datas(String properties){
        String [] props = dcode.unCode(properties);
        if (props.Length >= 3) {
            day = int.Parse(props [0]);
            month = int.Parse(props [1]);
            year = props [2];
        }
        if (props.Length >= 4) {
            time = new Horas(props [3]);
        }
    }

    public Datas(int day, int month, String year) {
        this.day = day; this.month = month; this.year = year; //this.time = null;
    }

    public Datas(int day, int month, String year, int hours, int minutes, int seconds) {
        this.day = day; this.month = month; this.year = year; this.time = new Horas(hours, minutes, seconds);
    }

    public Datas(int day, int month, String year, int hours, int minutes) {
        this.day = day; this.month = month; this.year = year; this.time = new Horas(hours, minutes);
    }

    public Datas(int day, int month, int hours, int minutes) {
        initCalendar();
        this.day = day; this.month = month; this.time = new Horas(hours, minutes);
        this.year = today.Year + "";
    }
    
    // Methods

    public void setToday(){
        initCalendar();
        this.day = today.Day - 1;
        this.month = today.Month - 1;
        this.year = today.Year + "";
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
    
    public  static  String ToString(Datas data){
        if (data.getTime() == null)
            return Datas.dcode.enCode(new String [] { "" + data.getDay(), "" + data.getMonth(), data.getYear() });
        else
            return Datas.dcode.enCode(new String [] { "" + data.getDay(), "" + data.getMonth(), data.getYear(), data.getTime().ToString() });
    }

    // Override methods

    override
    public String ToString(){
        return ToString(this);
    }
}
