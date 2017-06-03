package com.daviapps.dcode;

import android.os.Environment;
//import com.daviapps.ccode.*;
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
    //protected CCode ccode;
    
    // Private variables
    protected File path, file;
    protected int statusKey;
    
    // Static values
    public static int ALRIGHT = 100, ERROR = 101, EMPTY = 102, NOTFOUNDED = 103, OTHERENCODER = 104;
    
    // File assigned=s
    public static int DCODEFILE = 0x00, PREFERENCEFILE = 0x01;

    // File properties
    private String encodeType = "";
    private String title = "";
    private int assign = DCODEFILE;
    private String text = "";
    
    // Constructors
    
//    public DCodeFile(String pathName, String fileName, CCode ccode){
//        this(new File(Environment.getExternalStorageDirectory(), "/" + pathName + "/" + fileName), ccode);
//    }

    public DCodeFile(String pathName, String fileName){
        this(new File(Environment.getExternalStorageDirectory(), "/" + pathName + "/" + fileName));
    }
    
//    public DCodeFile(String filePath, CCode ccode){
//        this(new File(filePath), ccode);
//    }

    public DCodeFile(String filePath){
        this(new File(filePath));
    }
    
//    public DCodeFile(File file){
//        this(file, null);
//    }
    
    public DCodeFile(File path, String file){
        this(new File(path, file));
    }

    public DCodeFile(File file/*, CCode ccode*/){
        this.file = file;
        this.path = file.getParentFile();
        
        this.dcode = new DCode(DCode.FILE);
//        this.ccode = ccode;

        loadStatusKey();
        
        if(statusKey == OTHERENCODER){
            int encodeMode = DCode.getMode(this.getFileText());
            if(encodeMode != DCode.UNKNOWN)
                this.dcode = new DCode(encodeMode);
        }

        if(statusKey == ERROR){
            this.setTitle("ERROR LOAD");
            this.onError();
        } else
        if(statusKey == EMPTY){
            this.setTitle("EMPTY");
            this.onEmpty();
        } else
        if(statusKey == NOTFOUNDED){
            this.setTitle("NOT FOUNDED");
            this.onNotFound();
        }
        
        if(this.getStatusKey() == ALRIGHT)
            this.onAlright();
    }
    
    // Methods
    
    public void overwriteFile(){
        this.setFileText("");
        createBaseFile();
    }
    
    public void createFile(){
        if(this.getStatusKey() == DCodeFile.NOTFOUNDED){
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            try { file.createNewFile(); } catch (IOException ex) {}
            if(file.exists()){
                System.out.println("File created.");
                createBaseFile();
            }
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
            System.out.println("Base file created.");
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
//        String _text = dcode.enCode(new String[] { encodeType, title, text });
//        if(statusKey == ALRIGHT){
//            setFileText(/*ccode == null ? */_text/* : ccode.enCrype(_text)*/);
//        } else{
//            System.err.println("You can't writes this file.");
//            System.err.println("This file contains unknown encode or is encrypted.");
//        }
        saveFile();
    }

    public final String getText(){
        loadFile();
        if(statusKey == ALRIGHT)
            return this.text;
        
        return "ERROR LOAD";
    }
    
    public void loadFile(){
        if(file != null && statusKey == ALRIGHT)
            if(file.exists()){
                String fileContent = this.getFileText();
                String [] props = dcode.unCode(fileContent);
                
                if(props[2].equals("")) props[2] = "" + DCODEFILE;
                
                this.encodeType = props[0];
                this.title = props[1];
                this.assign = Integer.parseInt(props[2]);
                this.text = props[3];
            }
    }
    
    public void saveFile(){
        if(file != null && statusKey == ALRIGHT)
            if(file.exists()){
                setFileText(dcode.enCode(new String[]{encodeType, title, "" + assign, text}));
            }
    }
    
    public void statusKeyAction(dFAction action){
        action.setDFile(this);
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
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(fileText.getBytes());
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error, arquivo não salvo");
        }
        System.out.println("Arquivo salvo");
    }
    
    protected String getFileText(){
        return DCodeFile.getFileText(this.file);
    }

    protected static String getFileText(File file){
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
        loadStatusKey();
        return statusKey;
    }
    
    private void loadStatusKey(){
        if(file != null){
            if(!file.exists()){ statusKey = NOTFOUNDED ; return;}
            String fileText = this.getFileText();
            
            if(fileText.equals("")){ statusKey = EMPTY; return;}
            if(DCode.getMode(fileText) != dcode.getMode()){statusKey = OTHERENCODER; return;}
                
            String [] props = dcode.unCode(fileText);

            if(props.length >= 4)
                statusKey = ALRIGHT;
            else
                statusKey = ERROR;
        }
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

    public int getAssign() {
        return assign;
    }
    public void setAssign(int assign) {
        this.assign = assign;
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
    
    // External methods
    
    public void onAlright(){}
    
    public void onError(){}
    
    public void onNotFound(){
        this.createFile();
    }
    
    public void onEmpty(){
        this.createBaseFile();
    }
    
    // Internal methods
    
    public boolean isDCode(){
        return DCodeFile.isDCode(file);
    }

    // static methods
    
    public static boolean isDCode(File file){
        return DCodeFile.isDCode(file, new DCode(DCode.FILE));
    }
    
    public static boolean isDCode(File file, DCode dcode){
        char [] text = DCodeFile.getFileText(file).toCharArray();
        int opens = 0, spaces = 0, closes = 0;
        for (int i = 0; i < (text.length >= 50 ? 50 : text.length); i++){
            if(text[i] == dcode.getOpen())
                opens++;
            else if(text[i] == dcode.getSpace())
                spaces++;
            else if(text[i] == dcode.getClose())
                closes++;
            
            if(spaces >= 3)
                return true;
        }
        return false;
        //return (opens == closes) && (spaces >= 3);
    }
    
    // Override methods

    @Override
    public String toString() {
        String text = /*ccode == null ? */getFileText()/* : ccode.unCrype(getFileText())*/;
        if(this.getStatusKey() == DCodeFile.ALRIGHT)
            return dcode.enCode(new String[] {this.getEncodeType(), this.getTitle(), this.getText()});
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
        if(DCode.getMode(text) == DCode.UNKNOWN)
            return "UNKNOWN MODE";
        return null;
    }
}
