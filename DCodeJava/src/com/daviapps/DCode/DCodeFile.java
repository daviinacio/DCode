package com.daviapps.dcode;

import android.os.Environment;
import com.daviapps.ccode.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Davi, 10/11/2016
 */

public class DCodeFile {
    //protected final DCode dcode = new DCode('{', '_', '}');
    protected DCode dcode;
    protected CCode ccode;
    
    // Private variables
    protected File path, file;
    protected int statusKey;
    
    // Static values
    public static int ALRIGHT = 100, ERROR = 101, EMPTY = 102, NOTFOUNDED = 103, OTHERENCODER = 104;

    // File properties
    private String encodeType = "";
    private String title = "";
    private String passWd = "";
    
    // Constructors
    
    public DCodeFile(String pathName, String fileName, CCode ccode){
        this(new File(Environment.getExternalStorageDirectory(), "/" + pathName + "/" + fileName), ccode);
    }

    public DCodeFile(String pathName, String fileName){
        this(new File(Environment.getExternalStorageDirectory(), "/" + pathName + "/" + fileName));
    }
    
    public DCodeFile(String filePath, CCode ccode){
        this(new File(filePath), ccode);
    }

    public DCodeFile(String filePath){
        this(new File(filePath));
    }
    
    public DCodeFile(File file){
        this(file, new CCode());
    }

    public DCodeFile(File file, CCode ccode){
        this.file = file;
        this.path = file.getParentFile();
        
        this.dcode = new DCode(DCode.FILE);
        this.ccode = ccode;
        
        //this.getStatusKey();
        
        if(this.getStatusKey() == DCodeFile.OTHERENCODER) // Set DCode mode to mode of file
            if(DCode.getMode(this.getFileText()) != DCode.UNKNOWN) // If isn't unknown mode
                this.dcode = new DCode(DCode.getMode(this.getFileText())); // Set dcode to this mode

        if(statusKey == ERROR){
            this.setTitle("ERROR LOAD");
            //this.setEncodeType("ERROR LOAD");
        } else
        if(statusKey == EMPTY){
            this.setTitle("EMPTY");
            //this.setEncodeType("EMPTY");
            //createBaseFile();
        } else
        if(statusKey == NOTFOUNDED){
            this.setTitle("NOT FOUNDED");
            this.setEncodeType("NOT FOUNDED");
        }
    }
    
    // Methods
    
    public void overwriteFile(){
        this.setFileText("");
        createBaseFile();
    }
    
    public void createFile(){
        if(this.getStatusKey() == DCodeFile.NOTFOUNDED){
            try { file.createNewFile(); } catch (IOException ex) {}
            if(file.exists())
                createBaseFile();
            else
                System.err.println("Create file error.");
        } else
            System.err.println("File exists.");
    }
    
    public void createBaseFile(){
        if(this.getStatusKey() == DCodeFile.EMPTY){
            this.statusKey = ALRIGHT;
            this.setTitle(file.getName());
            this.setEncodeType("DCode");
            this.setText("");
        } else
            System.err.println("The file content will be losted.");
    }

    public boolean pathExists(){
        return path.exists();
    }

    public boolean exists(){
        return file.exists();
    }
    
    public void delete(){
        file.delete();
    }

    // File encoders and decoders

    public void setText(String text){
        if(statusKey == ALRIGHT){
            setFileText(ccode.enCrype(dcode.enCode(new String[] {
                encodeType, title, passWd, text
            })));
        } else{
            System.err.println("You can't writes this file.");
            System.err.println("This file contains unknown encode or is encrypted.");
        }
    }

    public final String getText(){
        if(statusKey == ALRIGHT){
            String [] props = null;
            if(!getFileText().equals("")){
                props = dcode.unCode(ccode.unCrype(getFileText()));
                if(props.length >= 4){
                    this.encodeType = props[0];
                    this.title = props[1];
                    this.passWd = props[2];
                } else{
                    statusKey = ERROR;
                    return "ERROR";
                }
            }
            else{
                this.statusKey = EMPTY;
            }

            return props[3];
        } else
        if(statusKey == NOTFOUNDED)
            return "NOT FOUNDED";
        
        return "ERROR LOAD";
    }

    // File loaders

    protected void setFileText(String fileText){
        
        if(!path.exists()){
            path.mkdir();
            System.out.println("Pasta " + path.getName() + " criada");
        }
        
        if(!file.exists()){
            try{
                file.createNewFile();
                System.out.println("Arquivo " + file.getName() + " criado");
            } catch (IOException e){
                System.out.println("Erro ao criar o arquivo " + file.getName());
            }
        }
        
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileText.getBytes());
            fileOutputStream.close();
            System.out.println("Arquivo salvo");
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error, arquivo não salvo");
        }
    }

    protected String getFileText(){
        String FileText = new String();
        String Message;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            while((Message = bufferedReader.readLine()) != null){
                    stringBuffer.append(Message+"\n");
            }

            FileText = stringBuffer.toString();

        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Erro, arquivo não carregado");
        }

        return FileText;
    }

    // Getters and setters
    
    public int getStatusKey(){
        if (file.exists()) {
            if (!this.getFileText().equals("")) { // Alright
                if (dcode.unCode(ccode.unCrype(getFileText())).length >= 4) {
                    statusKey = ALRIGHT;
                    this.getText();
                } else
                if(DCode.getMode(ccode.unCrype(getFileText())) != dcode.getMode()){ // Isn,t currently dcode mode
                    statusKey = OTHERENCODER;
                }else // Last vertion encoder
                    statusKey = ERROR;
            } else // File empty
                statusKey = EMPTY;
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

    public File getFile(){
        return file;
    }

    public File getPath(){
        return path;
    }

    public File getFilePath(){
        return file.getParentFile();
    }

    public File[] getFilesPath(){
        return path.listFiles();
    }
    
    // Internal methods
    
    @Deprecated
    public String getFileName(){
        return getFileName(file);
    }
    
    @Deprecated
    public boolean isDCodeExtention(){
        return isDCodeExtention(file);
    }
    
    @Deprecated
    public String getLast4Chars(){
        return getLast4Chars(file);
    }

    // static methods

    @Deprecated
    public static String getFileName(File file){
        char [] name = file.getName().toCharArray();
        String out = "";
        for(int i = 0; i < (name.length - 6); i++){
            out = out + name[i];
        }
        return out;
    }

    @Deprecated
    public static boolean isDCodeExtention(File file){
        String last4chars = getLast4Chars(file);
        boolean out = (
                last4chars.equals("DCode") ||
                last4chars.equals("dcode") ||
                last4chars.equals("Dcode") ||
                last4chars.equals("DCODE") 
                ) && !file.isDirectory();

        return out;
    }

    @Deprecated
    public static String getLast4Chars(File file){
        char [] name = file.getName().toCharArray();
        String last4chars = "";

        if(name.length >= 5){
            for(int i = name.length - 5; i < name.length; i++){
                last4chars = last4chars + name[i];
            }
        }

        return last4chars;
    }
    
    // Override methods

    @Override
    public String toString() {
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
        if(this.getStatusKey() == DCodeFile.OTHERENCODER)
            return "UNKNOWN MODE";
        else
        if(DCode.getMode(ccode.unCrype(this.getFileText())) == DCode.UNKNOWN)
            return "UNKNOWN MODE";
        return null;
    }
    
    
}
