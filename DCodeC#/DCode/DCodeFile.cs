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
    private DCode dcode;
    //protected CCode ccode;
    
    // Private variables
    private String path, file;
    protected int statusKey;
    
    // Static values
    public static int ALRIGHT = 100, ERROR = 101, EMPTY = 102, NOTFOUNDED = 103, OTHERENCODER = 104;

    // File properties
    private String encodeType = "";
    private String title = "";
    private String passWd = "";
    
    // Constructors

    public DCodeFile(String file){
        this.file = file;
        this.path = Path.GetDirectoryName(file);

        this.dcode = new DCode(DCode.FILE);

        //this.getStatusKey();

        if(this.getStatusKey() == DCodeFile.OTHERENCODER) // Set DCode mode to mode of file
            if(DCode.getMode(this.getFileText()) != DCode.UNKNOWN) // If isn't unknown mode
                this.dcode = new DCode(DCode.getMode(this.getFileText())); // Set dcode to this mode

        if (statusKey == ERROR) {
            this.setTitle("ERROR LOAD");
            //this.setEncodeType("ERROR LOAD");
        } else
        if (statusKey == EMPTY) {
            this.setTitle("EMPTY");
            //this.setEncodeType("EMPTY");
            //createBaseFile();
        } else
        if (statusKey == NOTFOUNDED) {
            this.setTitle("NOT FOUNDED");
            this.setEncodeType("NOT FOUNDED");
        }
    }
    
    // Methods

    public void overwriteFile() {
        this.setFileText("");
        createBaseFile();
    }

    public void createFile() {
        if (this.getStatusKey() == DCodeFile.NOTFOUNDED) {
            this.setFileText("");
        }
    }
    
    public void createBaseFile(){
        if (this.getStatusKey() == EMPTY) {
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
        String _text = dcode.enCode(new String [] { encodeType, title, passWd, text });
        if (statusKey == ALRIGHT) {
            setFileText(/*ccode == null ?*/ _text /*: ccode.enCrype(_text)*/);
        } else {
            Console.WriteLine("You can't writes this file.");
            Console.WriteLine("This file contains unknown encode or is encrypted.");
        }
    }

    public String getText(){
        if (statusKey == ALRIGHT) {
            String [] props = null;
            if (!getFileText().Equals("")) {
                String text = /*ccode == null ?*/ getFileText() /*: ccode.unCrype(getFileText())*/;
                props = dcode.unCode(text);
                if (props.Length >= 4) {
                    this.encodeType = props [0];
                    this.title = props [1];
                    this.passWd = props [2];
                } else {
                    statusKey = ERROR;
                    return "ERROR";
                }
            } else {
                this.statusKey = EMPTY;
            }

            return props [3];
        } else
            if (statusKey == NOTFOUNDED)
                return "NOT FOUNDED";

        return "ERROR LOAD";
    }

    // File loaders

    private void setFileText(String fileText){
        try {   // Open the text file using a stream reader.
            using (StreamWriter sr = new StreamWriter(file)) {
                // Read the stream to a string, and write the string to the console.
                sr.Write(fileText);
            }
        } catch (Exception e) {
            Console.WriteLine(e.Message);
        }
        //File.WriteAllText(file, fileText);
    }

    public String getFileText() {
        return DCodeFile.getFileText(this.file);
    }

    private static String getFileText(String file){
        String fileText = "";

        try {   // Open the text file using a stream reader.
            using (StreamReader sr = new StreamReader(file)) {
                // Read the stream to a string, and write the string to the console.
                fileText = sr.ReadToEnd();
            }
        } catch (Exception e) {
            Console.WriteLine(e.Message);
        }

        return fileText;
    }

    // Getters and setters

    public int getStatusKey(){
        if (File.Exists(file)) {
            //if(!isDCode(this)){ statusKey = ERROR; return statusKey; } else // Return error if is not a DCode file
            if (!this.getFileText().Equals("")) { // Alright
                if (dcode.unCode(getFileText()).Length >= 4) {
                    statusKey = ALRIGHT;
                    this.getText();
                } else
                if (DCode.getMode(this.getFileText()) != dcode.getMode()) { // Isn,t currently dcode mode
                    statusKey = OTHERENCODER;
                } else { // Last vertion encoder
                statusKey = ERROR;
                }
            } else { // File empty
                statusKey = EMPTY;
            }
        } else
            statusKey = NOTFOUNDED;

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

    [System.Obsolete]
    public String getFileName() {
        return getFileName(file);
    }

    [System.Obsolete]
    public bool isDCodeExtention(){
        return isDCodeExtention(file);
    }

    [System.Obsolete]
    public String getLast4Chars(){
        return getLast4Chars(file);
    }

    // static methods

    public static bool isDCode(String file) {
        return DCodeFile.isDCode(file, new DCode(DCode.FILE));
    }

    public static bool isDCode(String file, DCode dcode) {
        char [] text = DCodeFile.getFileText(file).ToCharArray();
        int opens = 0, spaces = 0, closes = 0;
        for (int i = 0; i < (text.Length >= 50 ? 50 : text.Length); i++) {
            if (text [i] == dcode.getOpen())
                opens++;
            else if (text [i] == dcode.getSpace())
                spaces++;
            else if (text [i] == dcode.getClose())
                closes++;

            if (spaces >= 3)
                return true;
        }
        return false;
        //return (opens == closes) && (spaces >= 3);
    }

    [System.Obsolete]
    public static String getFileName(String file){
        return Path.GetFileNameWithoutExtension(file);
        /*char [] name = Path.GetFileName(file).ToCharArray();
        String outp = "";
        for(int i = 0; i < (name.Length - 6); i++){
            outp = outp + name[i];
        }
        return outp;*/
    }

    [System.Obsolete("This method will be removed on version 1.2")]
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

    [System.Obsolete]
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

    override
    public String ToString() {
        if(this.getStatusKey() == DCodeFile.ALRIGHT)
            return dcode.enCode(new String[] {this.getEncodeType(), this.getTitle(), this.getPassWd(), this.getText()});
        else
        if(this.getStatusKey() == DCodeFile.ERROR)
            return "ERROR";
        else
        if(this.getStatusKey() == DCodeFile.EMPTY)
            return "EMPTY";
        else
        if(this.getStatusKey() == DCodeFile.NOTFOUNDED)
            return "NOT FOUNDED";
        else
        if(DCode.getMode(this.getFileText()) == DCode.UNKNOWN)
            return "UNKNOWN MODE";
        return null;
    }

}
