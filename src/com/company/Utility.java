package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by inly4 on 30/10/2016.
 */
public class Utility {
private String downloadFolder = "UserDownloadFolder";


    public void checkDownloadFolder(String username){
        File folder = new File("c:"+File.separator+downloadFolder);
        if(!folder.exists()){
            if(folder.mkdir()){
                System.out.println("folder created");
            }
        }
    }
    public void checkFolder(String username){
        File folder = new File("c:"+File.separator+downloadFolder+File.separator+username);
        if(!folder.exists()){
            if(folder.mkdir()){
                System.out.println(username+" folder created");
            }
        }
    }

    public void saveFile(String[] responseParams,String username )throws IOException {
        File file = new File("C:"+File.separator+downloadFolder+File.separator+username+File.separator+responseParams[3]);
        if(file.createNewFile()){
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(responseParams[2]);
            out.close();
        }else{
            System.out.println("File not created");
        }
    }
    public String getFiles(String username){

        File folder = new File("c:"+File.separator+ downloadFolder +File.separator+username);
        File[] files = folder.listFiles();
        String fileparam="";
        for(File f : files){
            fileparam+=f.getName()+"\n";
        }
        return fileparam;
    }


}
