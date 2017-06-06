package dcodepreferencesconsole;

import com.daviapps.dcode.DCodeFile;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Davi
 */
public class program{
    // Preference variables
    protected static File diretory;
    protected static DCodeFile file;
    // Running variables
    protected static boolean running = true;
    
    public static void main(String [] args){
        console.clear();
        
        // Para depuração
        diretory = new File("C:\\Users\\Davi\\Desktop");
        action.openFile("dPrefs2.dcode");
        
        while (running){
            switch(console.newCommand()){
                case "clear": console.clear(); break;
                
                case "file": console.file(); break;
                
                case "close": close(); break;
                case "exit": close(); break;
            }
        }
    }
    
    public static void close(){
        running = false;
        //System.exit(0);
    }
}
