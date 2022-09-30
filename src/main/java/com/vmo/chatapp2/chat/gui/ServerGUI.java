package com.vmo.chatapp2.chat.gui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGUI {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter pw;
    public ServerGUI(){
        try {
            server= new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            socket =server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReading() {
        Runnable r1 = () -> {
            while (true){
                try {
                    String msg = br.readLine();
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }

    private void startWriting() {
        Runnable r2 = () -> {
            while (true){
                try {
                    BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    pw.println(content);
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}
