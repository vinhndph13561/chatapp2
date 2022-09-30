package com.vmo.chatapp2.chat.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI extends JFrame{
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    private JLabel heading = new JLabel("Client Area");
    private JTextArea messArea = new JTextArea();
    private JTextField messInput = new JTextField();
    private Font font = new Font("Aria",Font.PLAIN,20);

    public ClientGUI(){
        try {
            System.out.println("Sending request to server!");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("Connection done!");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvents();
            startReading();
//            startWriting();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void handleEvents() {
        messInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode()==10){
                    String contentToSend = messInput.getText();
                    messArea.append("Me: "+ contentToSend+ "\n");
                    out.println(contentToSend);
                    out.flush();
                    messInput.setText("");
                    messInput.requestFocus();
                }
            }
        });
    }

    private void createGUI() {
        this.setTitle("Client Message[END]");
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messArea.setFont(font);
        messInput.setFont(font);
        this.setLayout(new BorderLayout());
        this.add(heading, BorderLayout.NORTH);
        this.add(messArea,BorderLayout.CENTER);
        this.add(messInput,BorderLayout.SOUTH);
        this.setVisible(true);
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
                        messInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    messArea.append("Server: "+ msg +"\n");
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
        new ClientGUI();
    }
}
