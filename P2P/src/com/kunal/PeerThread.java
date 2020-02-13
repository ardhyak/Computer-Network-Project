package com.kunal;
import java.io.*;
import java.net.*;

public class PeerThread extends Thread{
    private BufferedReader bufferedReader;
    public PeerThread(Socket socket) throws IOException{
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        boolean flag=true;
        while(flag){
            try{
                String s[]=bufferedReader.readLine().split(":");
                //if(jsonObject.containsKey("username"))
                    System.out.println("["+s[0]+"]: "+s[1]);
            } catch (Exception e){
                flag=false;
                interrupt();
            }
        }
    }
}