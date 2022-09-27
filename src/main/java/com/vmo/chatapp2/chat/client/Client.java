package com.vmo.chatapp2.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    //Constructor
    public Client(){
        try {
            System.out.println("Sending request to server!");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done!");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //start writing method
    private void startWriting() {
        Runnable r2 = () -> {
            System.out.println("writer started");
            try{
                while (!socket.isClosed()){
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
                System.out.println("Connection is closed!");
            } catch (Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    //start reading method
    private void startReading() {
        Runnable r1 = () -> {
            System.out.println("reader started...");
            try {
                while (true){
                    String msg = br.readLine();
                    if (msg.equals("exit")){
                        System.out.println("Chat is closed!");
                        socket.close();
                        break;
                    }
                    System.out.println("Server: "+msg);
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Connection closed!");
            }
        };
        new Thread(r1).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }
}
