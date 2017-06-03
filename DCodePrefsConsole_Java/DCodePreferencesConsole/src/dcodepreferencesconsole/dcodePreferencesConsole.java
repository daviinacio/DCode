package dcodepreferencesconsole;

import com.daviapps.dcode.*;
import java.io.File;

/**
 * @author Davi
 */
public class dcodePreferencesConsole {
    private static File diretory;
    private static DCodeFile file;
    
    public static void main(String[] args) {
        // Para depuração
        diretory = new File("C:\\Users\\Davi\\Desktop");
        openFile("dPrefs2.dcode");
        
        //System.err.println("" + file.getStatusKey());
    }
    
    // Console functions
    
    // Action functions
    
    private static void openFile(String fileName){
        file = new DCodeFile(diretory, fileName){
            @Override
            public void onAlright() {
                System.out.println("Alright");
            }

            @Override
            public void onEmpty() {
                System.err.println("Empty");
                super.onEmpty();
            }

            @Override
            public void onError() {
                System.err.println("Error");
            }

            @Override
            public void onNotFound() {
                System.err.println("Not founded");
                super.onNotFound();
            }
        };
    }
    
}
