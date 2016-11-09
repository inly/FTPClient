package com.company;

import jdk.nashorn.internal.runtime.linker.Bootstrap;

import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;

/**
 * Created by t00141159 on 25/10/2016.
 */
public class Main
{
    String request,username,password,response,userResponse, host;
    private int port;
    private FTPController controller;
    private BufferedReader br;
    private InputStreamReader is;


    public Main(String[] args)throws Exception{

        //Check for connection parameters
        //Set to default if no parameters
        if(args.length ==1 ){
            port= Integer.parseInt(args[0]);
        }else if (args.length == 2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }else{
            host = "localhost";
            port = 2000;
        }
        is = new InputStreamReader(System.in);
        br = new BufferedReader(is);
        controller = new FTPController(host,port);
        createOrLogin();
        handleResponse();
    }


    private void handleResponse() throws IOException,SocketException {
        String code="";
        try {
            code = response.substring(0, 3);
        }catch(StringIndexOutOfBoundsException ex){

        }
            switch (code) {

            case "131":
                System.out.println(response);
                userResponse = askToUpload();
                response = controller.upload(userResponse);
                handleResponse();
                break;
            case "132":
                System.out.println(response);
                createOrLogin();
                handleResponse();
                break;
            case "133":
                System.out.println(response);
                createOrLogin();
                handleResponse();
                break;
            case "000":
                controller.quit();
            case "101"://Success_NoFiles
                System.out.println(response);
                userResponse = askToUpload();
                response = controller.upload(userResponse);
                handleResponse();
                break;
            case "102"://Success_WithFiles
                System.out.println(response);
                response = askUserAction();
                handleResponse();
                break;
            case "103"://Incorrect_Credentilas
                System.out.println("\n"+response);
                createOrLogin();
                handleResponse();
                break;
            case"111":
                System.out.println("\n"+response);
                response = askUserAction();
                handleResponse();
                break;
            case "112":
                System.out.println(response);
                response = askUserAction();
                handleResponse();
                break;
            case "121":
                System.out.println(response);
                response = askUserAction();
                handleResponse();
                break;
            case "122":
                System.out.println(response);
                response = askUserAction();
                handleResponse();
                break;
            default:
                response = askUserAction();
                handleResponse();

        }

    }//End handleRequest

    private String askUserAction() throws IOException,SocketException{
        System.out.println("Enter 1 for uplaod\n2 for download\n 'q' to quit");
        userResponse = br.readLine();
        String file="";
        switch(userResponse){
            case "1"://Upload
                System.out.println("Enter the path to file for upload");
                file = br.readLine();
                return response = controller.upload(file);

            case "2"://Download
                System.out.println("Enter the file name to download");
                file = br.readLine();
                return response = controller.download(file);

            case "q":
            controller.quit();
            return "";
            default:
                System.out.println("Wrong input: " + userResponse);
                askUserAction();

        }
        return"";
    }//End AskUserAction
    private void getCredentials() throws IOException {
        System.out.println("Enter your username");
        username = br.readLine();
        System.out.println("Enter your password");
        password = br.readLine();
    }

    private String askToUpload()throws IOException {
        System.out.println("Enter file location to upload or Enter 'q' to quit ");
        String answer = br.readLine();
        if (answer.equals("q")) {
            controller.quit();
            System.exit(0);
        } else {
            return answer;
        }
        return null;
    }

private void createOrLogin() throws  IOException{
    System.out.println("Enter number of action required\n1 - Create User\n2 - Login");
    userResponse = br.readLine();
    switch(userResponse){
        case "1":
            getCredentials();
            response = controller.createUser(username,password);

            break;
        case "2":
            getCredentials();
            response = controller.login(username,password);
            break;
    }
}

    public static void main(String[] args)throws  Exception{
                Main main = new Main(args);
    }
}
