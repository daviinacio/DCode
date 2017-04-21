using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/* @author Davi */

public class Horas {
    private static DCode dcode = new DCode(DCode.ARRAY);

    // Private variables
    private int hours, minutes, seconds;

    private DateTime calendar;
    
    // Constructors

    public Horas() {
        setNow();
    }
    
    public Horas(String props){
        String [] _props = dcode.unCode(props);
        if(_props.Length >= 3){
            this.hours = int.Parse(_props[0]);
            this.minutes = int.Parse(_props[1]);
            this.seconds = int.Parse(_props[2]);
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
        calendar = DateTime.Today;
    }
    
    // Methods

    public void setNow(){
        initCalendar();
        this.hours = calendar.Hour;
        this.minutes = calendar.Minute;
        this.seconds = calendar.Second;
    }
    
    // Static methods
    
    public static Horas parceHoras(String hora){
        return new Horas(hora);
    }
    
    public static String ToString(Horas hora){
        return Horas.dcode.enCode(new String[] {"" + hora.hours, "" + hora.minutes, "" + hora.seconds});
    }
    
    // Override methods

    override
    public String ToString() {
        return Horas.ToString(this);
    }
    
}
