package com.vmo.chatapp2.Auth.gui;

import com.vmo.chatapp2.Auth.form.AuthForm;
import com.vmo.chatapp2.Auth.service.AuthService;
import com.vmo.chatapp2.home.gui.HomeClientGUI;
import com.vmo.chatapp2.utils.CommonMsg;
import com.vmo.chatapp2.utils.CommonResponse;
import com.vmo.chatapp2.utils.OkHttpClientCommon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AuthClientGUI extends JFrame{
    public JPanel panel1;
    public JTextField txtUsername;
    public JButton btnLogin;
    private JButton btnSignIn;
    private JPasswordField txtPassword;

    public AuthClientGUI() {
        this.setTitle("Login");
        this.setSize(440, 624);
        this.setLocationRelativeTo(null);
        addEvents();
    }

    public void addEvents(){
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OkHttpClientCommon ok = new OkHttpClientCommon();
                AuthForm user = new AuthForm();
                user.setUsername(txtUsername.getText());
                user.setPassword(txtPassword.getText());
                if (txtUsername==null){
                    CommonMsg.alert(panel1,  "enter username");
                }
                if (txtPassword==null){
                    CommonMsg.alert(panel1,  "enter password");
                }
                try {
                    CommonResponse cRes = ok.create("http://localhost:8080/api/auth/login",user);
                    if (cRes.getStatus()==0){
                        CommonMsg.alert(panel1,cRes.getMessage());
                    }
                    if (cRes.getStatus()==1){
                        CommonMsg.alert(panel1,cRes.getMessage());
                    }
                    if (cRes.getStatus()==2){
                        CommonMsg.alert(panel1,cRes.getMessage());
                            System.out.println("user1");
                            JFrame frame = new JFrame("MessageGUI");
                            frame.setContentPane(new HomeClientGUI().panelMain);
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frame.pack();
                            frame.setVisible(true);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        btnSignIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignInDialog signIn = new SignInDialog();
                signIn.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new AuthClientGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
