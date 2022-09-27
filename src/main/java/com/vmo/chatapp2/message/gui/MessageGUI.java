package com.vmo.chatapp2.message.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.utils.OkHttpClientCommon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageGUI extends Container {
    public JTabbedPane tabbedPane1;
    public JPanel panelMenu;
    private JTextField txtMessToSend;
    private JButton btnSend;
    private JTextArea textArea1;
    public JPanel panelConver;
    public JPanel pnscrene;
    public JTable table1;
    public JPanel panelMess;
    private JList list1;
    public JPanel chidrenpanel;

    public MessageGUI(){
        createUIComponents();
    }

    public void createUIComponents() {

    }

    public void showPanel(JPanel panel){
        chidrenpanel = panel;
        pnscrene.removeAll();
        pnscrene.add(chidrenpanel);
        pnscrene.validate();
    }

    public List<ConversationBO> getListConver(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.access("http://localhost:8080/api/conversation");
            ObjectMapper mapper = new ObjectMapper();
            List<ConversationBO> conversationBOList =new ArrayList<>();
            try {
                conversationBOList = mapper.readValue(data, new TypeReference<List<ConversationBO>>() {
                });
                System.out.println(data);
                return conversationBOList;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(conversationBOList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
