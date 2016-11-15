package com.daviapps.DCode;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Davi
 */

public class DCodeFile {
    protected final DCode dcode = new DCode('{', '_', '}');
    
    // Private variables
    protected File path, file;
    protected int statusKey;
    //private String externalStorage = "";
    
    // Static values
    public static int ALRIGHT = 100, ERROR = 101, EMPTY = 102;

    // File properties
    private String encodeType = "";
    private String title = "";
    private String passWd = "";
    
    // Constructors

    public DCodeFile(String pathName, String fileName){
        this(new File(Environment.getExternalStorageDirectory(), "/" + pathName + "/" + fileName));
    }

    public DCodeFile(String filePath){
        this(new File(filePath));
    }

    public DCodeFile(File file){
        this.file = file;
        this.path = file.getParentFile();
        if(!this.getFileText().equals("")){ // Alright
            if(dcode.unCode(getFileText()).length >= 4){
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
        } else
        if(statusKey == EMPTY){
            createBaseFile();
        }
    }
    
    // Methods
    
    public void createBaseFile(){
        if(this.getStatusKey() == EMPTY){
            this.statusKey = ALRIGHT;
            this.setTitle(file.getName());
            this.setEncodeType("DCode");
            this.setText("");
        }
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
        if(statusKey != ERROR){
            setFileText(dcode.enCodeI(new String[] {
                encodeType, title, passWd, text
            }));
        }
    }

    public final String getText(){
        if(statusKey == ALRIGHT){
            String [] props = null;
            if(!getFileText().equals("")){
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
            e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro, arquivo não carregado");
        }

        return FileText;
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
    
    public String getFileName(){
        return getFileName(file);
    }
    
    public boolean isDCodeExtention(){
        return isDCodeExtention(file);
    }
    
    public String getLast4Chars(){
        return getLast4Chars(file);
    }

    // static methods

    public static String getFileName(File file){
        char [] name = file.getName().toCharArray();
        String out = "";
        for(int i = 0; i < (name.length - 6); i++){
            out = out + name[i];
        }
        return out;
    }

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
}
