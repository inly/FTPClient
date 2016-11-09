package com.company;

import java.io.IOException;

/**
 * Created by inly4 on 30/10/2016.
 */
public class Parser {

    private Utility util;

    public Parser(){
        util = new Utility();
    }


    public String parseLoginResponse(String response,String[] responseParams,String username){
        responseParams = response.split(":");
        String[] files;
        String message="";
        switch (responseParams[0]){
            case "101":
                message+=responseParams[0]+"\t"+responseParams[1]+"\n";
                message+="------------"+username+"------------\n\n"+ responseParams[2] + "\n\n----------------------------";
                util.checkFolder(username);

                break;
            case "102":
                message+=responseParams[0]+"\t"+responseParams[1]+"\n";
                message+="------------"+username+"------------\n\n";
                files = responseParams[2].split(",");
                for(String file : files){
                    message+= file + "\n";
                }
                message+="----------------------------\n";
                break;
            case "103":
                message+=responseParams[0]+"\t"+responseParams[1]+"\n----------------------------\n"+"Username: "+responseParams[2]+"\nPassword: " + responseParams[3]
                        +"\n----------------------------\n";
                break;
        }
        return message;
    }//End parseLogin

    public String parseUploadResponse(String response,String username){
        String[] responseParams = response.split(":");
        String code = responseParams[0];
        String message ="";
        switch(code){
            case "111":
                message+=responseParams[0]+"\t"+responseParams[1]+"\n";
                String[] files = responseParams[2].split(",");
                String fileOutput="";
                for(String s : files){
                    fileOutput += s+"\n";
                }
                message+="------------"+username+"------------\n\n"+ fileOutput + "\n\n----------------------------";
                return message;

            case "112":
                message+=responseParams[0]+"\t"+responseParams[1]+"\n";
                files = responseParams[2].split(",");
                fileOutput="";
                for(String s : files){
                    fileOutput += s+"\n";
                }
                message+="------------"+username+"------------\n\n"+ fileOutput + "\n\n----------------------------";
                return message;
        }
        return "";
    }//End parseUpload

    public String parseDownloadResponse(String response,String username)throws IOException {
        String[] responseParams = response.split(":");
        String code = responseParams[0];
        String message="";
        switch (code){
            case "121":
                util.saveFile(responseParams,username);
                System.out.println(code);
                message += code+"\t"+ responseParams[1] +"\n-----------"+username+"----------------\n";
                message += util.getFiles(username);
                message+="-----------------------------------";
                return message;
            case "122":
                message += code+"\t"+ responseParams[1] +"\n-----------"+username+"----------------\n";
                message += util.getFiles(username);
                message+="-----------------------------------";
                return message;
        }
        return"";
    }//End parseDownload

    public String parseCreateResponse(String response){
        String[] responseParams = response.split(":");
        String code = responseParams[0];
        String message ="";
        switch(code){
            case "131":
                message+=responseParams[0]+"\t"+responseParams[1]+"\n";
                message+="-----------"+responseParams[2]+"-------------\n"+"No Files"+ "\n----------------------------";
                return message;

            case "132":
                message = responseParams[0]+"\t" + responseParams[1]+"\n---------------------\n"
                        +responseParams[2]+"\n---------------------\n";
                return message;
            case "133":
                message = responseParams[0] +"\t" + responseParams[1]+"\n"+responseParams[2];
                return message;

        }
        return "";
    }
}
