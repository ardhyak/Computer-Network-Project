    package com.kunal;

import java.net.*;
import java.io.*;
public class Peer{
    public static void main(String[] args) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("> Enter username and port#: ");
        String[] setupValues=br.readLine().split(" ");
        ServerThread serverThread=new ServerThread(setupValues[1]);
        serverThread.start();
        new Peer().updateListenToPeers(br,setupValues[0],serverThread);
    }

    public void updateListenToPeers(BufferedReader bufferedReader,String username,ServerThread serverThread) throws Exception{
        System.out.println("> Enter(space separated) hostname:port#");
        System.out.println(" peers to receive messages from (s to skip):");
        String input = bufferedReader.readLine();
        String[] inputValues=input.split(" ");
        if (!input.equals("s")) for(int i=0;i<inputValues.length;i++){
            String[] address = inputValues[i].split(":");
            Socket socket=null;
            try{
                socket=new Socket(address[0],Integer.parseInt(address[1]));
                new PeerThread(socket).start();
            }
            
            catch(Exception e){
                if(socket!=null) socket.close();
                else System.out.println("invalid input. skipping to next step.");
            }
        }
        communicate(bufferedReader,username,serverThread);
    }
    public void communicate(BufferedReader bufferedReader,String username,ServerThread serverThread){
        try{
            System.out.println("> you can now communicate (e to exit,c to change)");
            boolean flag=true;
            while(flag) {
                String message = bufferedReader.readLine();
                if (message.equals("e")){
                    flag=false;
                    break;
                }
                else if(message.equals("c")){
                    updateListenToPeers(bufferedReader,username,serverThread);
                }
                else{
                    serverThread.sendMessage(username + ":" + message);
                }
            }
            System.exit(0);
        }
         catch (Exception e) {
            e.printStackTrace();
        }
    }



}