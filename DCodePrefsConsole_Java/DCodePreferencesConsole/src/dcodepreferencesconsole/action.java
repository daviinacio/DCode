package dcodepreferencesconsole;

import com.daviapps.dcode.DCodeFile;

/**
 * @author Davi
 */
public class action {
    
    public static void openFile(String fileName){
        program.file = new DCodeFile(program.diretory, fileName){
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
