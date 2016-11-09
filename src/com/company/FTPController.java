package com.company;

import java.net.*;
import java.io.*;

public class FTPController {
    private FTPDatagramSocket mySocket;
    private InetAddress serverHost;
    private String[] responseParams;
    private String username;
    private Parser parser;
    private Utility util;
    private int serverPort;
    private String downloadFolder;

    FTPController(String serverHost, int serverPort) throws SocketException, UnknownHostException {
        this.serverHost = InetAddress.getByName(serverHost);
        this.serverPort = serverPort;
        this.mySocket = new FTPDatagramSocket();
        downloadFolder = "UserDownlaodFolders";
        util = new Utility();
        parser = new Parser();
    }
public String createUser(String username,String password)throws SocketException,IOException{
    String message = "130:Create User:"+username+":"+password+":";
    String response="";
    mySocket.sendMessage(serverHost,serverPort,message);
    response = mySocket.receiveMessage();
    response = parser.parseCreateResponse(response);
    return response;
}
    public String login( String username,String password) throws SocketException, IOException {
        String message = "100:login:"+username+":"+password+":";
        String response = "";
        this.username = username;
        mySocket.sendMessage(serverHost,serverPort,message);
        // now receive the echo
        response = mySocket.receiveMessage();
        response = parser.parseLoginResponse(response,responseParams,username);
        return response;
    }//End Login

    public String upload(String file) throws IOException
    {
        /*
            firtbitIf file was entered by filename only add
            in full path to user folder
         */
        String filename = file;
        File folder = new File("C:"+File.separator+downloadFolder+File.separator+username);
        File[] files = folder.listFiles();
        if(files!=null){
            for(File f : files){
                if(f.getName().equalsIgnoreCase(file)){
                    file = "C:"+File.separator+downloadFolder+File.separator+username+File.separator+file;
                }
            }
        }
        //construct protocol
        BufferedReader br = new BufferedReader(new FileReader(file));
        String message = "110:uplaod:";
        while( br.readLine() != null){
            message+=br.readLine()+"\n";
        }
        String[] splitAddress = file.split("/");
        message+=":"+splitAddress[splitAddress.length-1]+":";

        mySocket.sendMessage(serverHost,serverPort,message);
        String response = mySocket.receiveMessage();
        response = parser.parseUploadResponse(response,username);
        return response;
    }//End Upload

    public String download(String file)throws SocketException,IOException{
        String message = "120:download:"+file+":";
        mySocket.sendMessage(serverHost,serverPort,message);
        String response = mySocket.receiveMessage();
        response = parser.parseDownloadResponse(response,username);
        return response;
    }

    public void quit()throws  IOException {
        String message = "000:Quit:"+username+":";
        mySocket.sendMessage(serverHost, serverPort, message);
        String response = mySocket.receiveMessage();
        if (response.substring(0, 3).equals("000")) {
            System.out.println("Connection closed\nGood Bye " + username);
            mySocket.close();
            System.exit(0);
        }else{
        }
    }

} //end class
