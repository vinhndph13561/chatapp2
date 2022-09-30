package com.vmo.chatapp2.home.gui;

import com.vmo.chatapp2.account.gui.AccountClientGUI;
import com.vmo.chatapp2.message.gui.MessClientGUI;
import com.vmo.chatapp2.message.gui.MessGUI;
import com.vmo.chatapp2.relationship.gui.RelationshipGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeClientGUI extends JFrame{
    public JPanel panelMain;
    private JPanel panelAccount;
    private JPanel panelMessage;
    private JPanel panelRelationship;
    public JPanel pnscrene;
    private JPanel childrenpanel;

    public HomeClientGUI(){
        this.setTitle("Chat app");
        this.setSize(1280, 900);
        this.setLocationRelativeTo(null);
        addEvents();

    }

    public void addEvents(){
        panelAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JPanel account = new AccountClientGUI().panel1;
                showPanel(account);
            }
        });
        panelMessage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("oke");
//                showPanel(new MessGUI().panel1);
                JPanel mess = new MessClientGUI().panel1;
                showPanel(mess);
            }
        });
        panelRelationship.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JPanel rela = new RelationshipGUI().pnRela;
                showPanel(rela);
            }
        });
    }

    public void showPanel(JPanel panel){
        childrenpanel = panel;
        pnscrene.setLayout(new CardLayout());
        pnscrene.removeAll();
        pnscrene.add(childrenpanel);
        pnscrene.validate();
    }
}
