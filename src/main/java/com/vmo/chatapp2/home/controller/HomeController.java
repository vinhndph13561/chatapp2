package com.vmo.chatapp2.home.controller;

import com.vmo.chatapp2.Auth.gui.AuthGUI;
import com.vmo.chatapp2.utils.CommonResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public void home(){
        JFrame frame = new JFrame("AuthGUI");
        frame.setContentPane(new AuthGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
