package com.company;

import java.net.*;
import java.io.*;

/**
*This is a file from class lab
*
*
*/

/**
 * A subclass of DatagramSocket which contains 
 * methods for sending and receiving messages
 * @author M. L. Liu
 */
public class FTPDatagramSocket extends DatagramSocket {
private final int MAX_LEN = 10000;
   FTPDatagramSocket() throws SocketException, UnknownHostException {
       super( );

   }

   FTPDatagramSocket(int portNo) throws SocketException,UnknownHostException {
     super(portNo);

   }
   public void sendMessage(InetAddress serverHost,int receiverPort,String message) throws IOException {

         byte[ ] sendBuffer = message.getBytes( );                                     
         DatagramPacket datagram = new DatagramPacket(sendBuffer, sendBuffer.length,serverHost,receiverPort);
         this.send(datagram);
   } // end sendMessage

   public String receiveMessage() throws IOException {
         byte[ ] receiveBuffer = new byte[MAX_LEN];
         DatagramPacket datagram =new DatagramPacket(receiveBuffer, MAX_LEN);
         this.receive(datagram);
         String message = new String (receiveBuffer);
         return message;
   } //end receiveMessage
} //end class
