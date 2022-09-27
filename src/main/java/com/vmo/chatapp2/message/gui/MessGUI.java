package com.vmo.chatapp2.message.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.message.bo.MessageBO;
import com.vmo.chatapp2.utils.OkHttpClientCommon;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessGUI extends JPanel {
    public JPanel panel1;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel searchPanel;
    public JPanel converPanel;
    private JTextArea txtMessArea;
    private JTextField txtMessToSend;
    private JButton btnSend;
    private JScrollPane pnMessscn;
    ArrayList<JLabel> listLabel = new ArrayList<>();
    ArrayList<JLabel> listLabel2 = new ArrayList<>();
    ArrayList<JPanel> listPanel = new ArrayList<>();

    public MessGUI(){
        addComponent();

    }

    public void addComponent(){
        List<ConversationBO> list = getListConver();
        for (ConversationBO bo: list){
            listLabel.add(new JLabel());
            listPanel.add(new JPanel());
        }
        for (JLabel label: listLabel) {
            Font font = new Font("Arial",Font.PLAIN,20);
            label.setFont(font);
        }
        for (ConversationBO bo: list){
            String id = String.valueOf(bo.getId());
            listPanel.get(list.indexOf(bo)).add(new JLabel(bo.getAccountBO().getChatName()));
            listPanel.get(list.indexOf(bo)).setName(id);
            listPanel.get(list.indexOf(bo)).setPreferredSize(new Dimension(250,40));
        }

        for (JPanel panel: listPanel) {
//            panel.setPreferredSize();
            panel.setLayout(new FlowLayout());
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    System.out.println("oke2");
                    List<MessageBO> listMess = getListMess(Long.valueOf(panel.getName()));
                    for (MessageBO mess: listMess) {
                        listLabel2.add(new JLabel(mess.getSender().getChatName()+": \n"+mess.getMessage()));
//                        txtMessArea.setText(txtMessArea.getText().trim()+"\n"+ mess.getAccountBO().getChatName()+": "+mess.getMessage());
                    }
                    for (MessageBO mess: listMess) {
                        listLabel2.get(listMess.indexOf(mess)).setFont(new Font("Arial",Font.PLAIN,18));
                        listLabel2.get(listMess.indexOf(mess)).setPreferredSize(new Dimension(865,30));
                        if (mess.getSender().getUsername()==getUsername()){
                            listLabel2.get(listMess.indexOf(mess)).setHorizontalAlignment(SwingConstants.RIGHT);
                        }else{
                            listLabel2.get(listMess.indexOf(mess)).setHorizontalAlignment(SwingConstants.LEFT);
                        }
                    }
                    for (JLabel label: listLabel2) {
                        pnMessscn.add(label);
                    }
                }
            });
            converPanel.add(panel);
        }
    }

    public void addMessage(){

    }

    public String getUsername(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String name = ok.access("http://localhost:8080/api/auth/principal");
            System.out.println(name);
            return name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MessageBO> getListMess(Long id){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.retrieve("http://localhost:8080/api/message",id);
            System.out.println(data);
            ObjectMapper mapper = new ObjectMapper();
            List<MessageBO> messBOList =new ArrayList<>();
            try {
                messBOList = mapper.readValue(data, new TypeReference<List<MessageBO>>() {
                });
                System.out.println(data);
                return messBOList;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(messBOList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
