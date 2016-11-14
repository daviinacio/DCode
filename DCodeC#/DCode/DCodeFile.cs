using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

/**
 * @author Davi
 */

public class DCodeFile {
    private DCode dcode = new DCode('{', '_', '}');
    
    // Private variables
    //protected File path, file;
    private String path, file;
    protected int statusKey;
    //private String externalStorage = "";
    
    // Static values
    public static int ALRIGHT = 100, ERROR = 101, EMPTY = 102;

    // File properties
    private String encodeType = "";
    private String title = "";
    private String passWd = "";
    //private String text = "";
    
    // Constructors

    /*public DCodeFile(String pathName, String fileName){
        this(new File(Environment.getExternalStorageDirectory(), "/" + pathName + "/" + fileName));
    }*/

    public DCodeFile(String file){
        this.file = file;
        this.path = Path.GetPathRoot(file);

        if(!this.getFileText().Equals("")){ // Alright
            if(dcode.unCode(getFileText()).Length >= 4){
                statusKey = ALRIGHT;
                this.getText();
            } else { // Last vertion encoder
                statusKey = ERROR;
            }
        } else { // File empty
            statusKey = EMPTY;
        }

        if(statusKey == ERROR){
            this.setTitle("ERROR LOAD");
            this.setEncodeType("ERROR LOAD");
        }
    }
    
    // Methods
    
    public void createBaseFile(){
        if(this.getStatusKey() == EMPTY){
            this.statusKey = ALRIGHT;
            this.setTitle(getFileName(file));
            this.setEncodeType("DCode");
            this.setText("");
        }
    }

    public bool pathExists(){
        return File.Exists(path);
    }

    public bool exists(){
        return File.Exists(file);
    }
    
    public void delete(){
        File.Delete(file);
    }

    // File encoders and decoders

    public void setText(String text){
        if(statusKey != ERROR){
            setFileText(dcode.enCodeI(new String[] {
                encodeType, title, passWd, text
            }));
        }
    }

    public String getText(){
        if(statusKey == ALRIGHT){
            String [] props = null;
            if(!getFileText().Equals("")){
                props = dcode.unCode(getFileText());
                this.encodeType = props[0];
                this.title = props[1];
                this.passWd = props[2];
            }
            else{
                this.statusKey = EMPTY;
            }

            return props[3];
        }

        return "Error LOAD";
    }

    // File loaders

    private void setFileText(String fileText){
        File.WriteAllText(file, fileText);
    }

    private String getFileText(){
        return File.ReadAllText(file);
    }

    // Getters and setters

    public int getStatusKey(){
        return statusKey;
    }

    public void setEncodeType(String encodeType){
        this.encodeType = encodeType;
    }
    public String getEncodeType(){
        return encodeType;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }

    public void setPassWd(String passWd){
        this.passWd = passWd;
    }
    public String getPassWd(){
        return passWd;
    }

    public String getFile(){
        return file;
    }

    public String getPath(){
        return path;
    }
    
    // Internal methods

    public String getFileName() {
        return getFileName(file);
    }
    
    public bool isDCodeExtention(){
        return isDCodeExtention(file);
    }
    
    public String getLast4Chars(){
        return getLast4Chars(file);
    }

    // static methods

    public static String getFileName(String file){
        char [] name = Path.GetFileName(file).ToCharArray();
        String outp = "";
        for(int i = 0; i < (name.Length - 6); i++){
            outp = outp + name[i];
        }
        return outp;
    }

    public static bool isDCodeExtention(String file){
        String last4chars = getLast4Chars(file);
        bool outp = (
                last4chars.Equals("DCode") ||
                last4chars.Equals("dcode") ||
                last4chars.Equals("Dcode") ||
                last4chars.Equals("DCODE") 
                ) && !Path.IsPathRooted(file);

        return outp;
    }

    public static String getLast4Chars(String file){
        char [] name = Path.GetFileName(file).ToCharArray();
        String last4chars = "";

        if(name.Length >= 5){
            for(int i = name.Length - 5; i < name.Length; i++){
                last4chars = last4chars + name[i];
            }
        }

        return last4chars;
    }

}
