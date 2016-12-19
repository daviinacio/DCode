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
    private DCode dcode = new DCode(DCode.DATAs);

    // Private variables
    private int day, month;
    private String year;
    
    private DateTime today;
    
    // Init methods
    
    private void initCalendar(){
        today = DateTime.Today;
    }

    // Constructors
    
    public Datas(){
        this.day = 0; this.month = 0; this.year = "1999";
        this.setToday();
    }

    public Datas(String properties){
        String [] props = dcode.unCode(properties);
        day = int.Parse(props[0]);
        month = int.Parse(props[1]);
        year = props[2];
    }
    
    public Datas(int day, int month, String year){
        this.day = day; this.month = month; this.year = year;
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

    // Static methods

    public static Datas parseDatas(String data){
        return new Datas(data);
    }
    
    public  static  String toString(Datas data){
        return data.dcode.enCode(new String [] { "" + data.getDay(), "" + data.getMonth(), data.getYear() });
    }

    // Override methods

    override
    public String ToString(){
        return toString(this);
    }
}
