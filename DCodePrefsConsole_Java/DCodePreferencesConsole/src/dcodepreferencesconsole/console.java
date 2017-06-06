package dcodepreferencesconsole;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Davi
 */
public class console {
    // public
    protected static Scanner input = new Scanner(System.in);;
    
    
    public static void file(){
        System.out.println("" + input.hasNext());
        if(true)
            System.err.println("Type any command");
        else{
            System.out.println("File");
        }
    }
    
    public static void clear(){
        /*final String operatingSystem = System.getProperty("os.name");

        try{
            if (operatingSystem .contains("Windows"))
                Runtime.getRuntime().exec("cls");
            else 
                Runtime.getRuntime().exec("clear");
        } catch (IOException ex) {
            Logger.getLogger(console.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        cabecalho();
    }
    
    public static void cabecalho(){
        System.out.println(" DCode Preferences Console Java");
        System.out.println("   copyright © DaviApps 2017 ");

        System.out.println("-------------------------------");
        
        System.out.println("");
    }
    
    public static String newCommand(){
        return newCommand("> ");
    }
    
    public static String newCommand(String text){
        System.out.print(text);
        return input.next();
    }
}
