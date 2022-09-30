package com.vmo.chatapp2.message.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.message.bo.MessageBO;
import com.vmo.chatapp2.message.form.MessDeleteForm;
import com.vmo.chatapp2.message.form.MessageForm;
import com.vmo.chatapp2.utils.CommonMsg;
import com.vmo.chatapp2.utils.CommonResponse;
import com.vmo.chatapp2.utils.OkHttpClientCommon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessClientGUI extends JPanel {
    public JPanel panel1;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel searchPanel;
    public JPanel converPanel;
    private JTextArea txtMessArea;
    private JTextField txtMessToSend;
    private JButton btnSend;
    private JPanel pnMessscn;

    String name;

    ConversationBO conversationBO = new ConversationBO();

    ArrayList<JLabel> listLabel = new ArrayList<>();
    ArrayList<JLabel> listLabel2 = new ArrayList<>();
    ArrayList<JPanel> listPanel = new ArrayList<>();
    ArrayList<JDialog> listDialogs = new ArrayList<>();
    ArrayList<JLabel> listLabelDelete = new ArrayList<>();
    ArrayList<JLabel> listLabelUpdate = new ArrayList<>();

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public MessClientGUI(){
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
            listLabel.get(list.indexOf(bo)).setText(getChatName(bo.getId()));
            listPanel.get(list.indexOf(bo)).add(listLabel.get(list.indexOf(bo)));
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
                    pnMessscn.removeAll();
                    pnMessscn.validate();
                    System.out.println("oke2");
                    name = listLabel.get(listPanel.indexOf(panel)).getText();
                    conversationBO=list.get(listPanel.indexOf(panel));
                    List<MessageBO> listMess = getListMess(Long.valueOf(panel.getName()));
                    for (MessageBO mess: listMess) {
                        listDialogs.add(new JDialog());
                        listLabelDelete.add(new JLabel("Delete"));
                        listLabel2.add(new JLabel(mess.getSender().getChatName()+": \n"+mess.getMessage()));
//                        txtMessArea.setText(txtMessArea.getText().trim()+"\n"+ mess.getAccountBO().getChatName()+": "+mess.getMessage());
                    }
                    for (MessageBO mess: listMess) {
                        listLabelDelete.get(listMess.indexOf(mess)).setPreferredSize(new Dimension(60,30));
                        listLabelDelete.get(listMess.indexOf(mess)).setHorizontalAlignment(SwingConstants.CENTER);
                        listLabelDelete.get(listMess.indexOf(mess)).addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                OkHttpClientCommon ok = new OkHttpClientCommon();
                                MessDeleteForm deleteForm = new MessDeleteForm();
                                deleteForm.setId(mess.getId());
                                try {
                                    CommonResponse cRes = ok.update("http://localhost:8080/api/message",deleteForm, getAccount().getId());
                                    if (cRes.getStatus()==0){
                                        CommonMsg.alert(panel1,cRes.getMessage());
                                    }
                                    if (cRes.getStatus()==1){
                                        CommonMsg.alert(panel1,cRes.getMessage());
                                    }
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        listDialogs.get(listMess.indexOf(mess)).add(listLabelDelete.get(listMess.indexOf(mess)));
                        listDialogs.get(listMess.indexOf(mess)).setPreferredSize(new Dimension(865,30));
                        listLabel2.get(listMess.indexOf(mess)).setFont(new Font("Arial",Font.PLAIN,18));
                        listLabel2.get(listMess.indexOf(mess)).setPreferredSize(new Dimension(865,30));
                        System.out.println(mess.getSender().getUsername());
                        if (mess.getSender().getUsername().equals(getAccount().getUsername())){
                            listLabel2.get(listMess.indexOf(mess)).setHorizontalAlignment(SwingConstants.RIGHT);
                            listLabel2.get(listMess.indexOf(mess)).setForeground(Color.BLACK);
                        }else{
                            listLabel2.get(listMess.indexOf(mess)).setHorizontalAlignment(SwingConstants.LEFT);
                            listLabel2.get(listMess.indexOf(mess)).setForeground(Color.DARK_GRAY);
                        }

                    }
                    for (JLabel label: listLabel2) {
                        label.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                listDialogs.get(listLabel2.indexOf(label)).setLocationRelativeTo(null);
                                listDialogs.get(listLabel2.indexOf(label)).setPreferredSize(new Dimension(80,30));
                                listDialogs.get(listLabel2.indexOf(label)).setVisible(true);

                            }
                        });
                        pnMessscn.add(label);
                    }
                    pnMessscn.validate();
                    try {
                        System.out.println("Sending request to server!");
                        socket = new Socket("127.0.0.1",7777);
                        System.out.println("Connection done!");
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream());
                        startReading(name);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
            converPanel.add(panel);
        }
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String contentToSend = txtMessToSend.getText();
                JLabel label = new JLabel(getAccount().getChatName()+": \n"+contentToSend);
                label.setFont(new Font("Arial",Font.PLAIN,18));
                label.setPreferredSize(new Dimension(865,30));
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                label.setForeground(Color.BLACK);
                pnMessscn.add(label);
                pnMessscn.validate();
                out.println(contentToSend);
                out.flush();
                txtMessToSend.setText("");
                txtMessToSend.requestFocus();
                OkHttpClientCommon ok = new OkHttpClientCommon();
                MessageForm form = new MessageForm();
                form.setConversationBO(conversationBO);
                form.setSender(getAccount());
                form.setMessage(contentToSend);
                try {
                    CommonResponse cRes = ok.create("http://localhost:8080/api/message",form);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void addMessage(){

    }

    public AccountBO getAccount(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.access("http://localhost:8080/api/auth/clientprincipal");
            ObjectMapper mapper = new ObjectMapper();
            AccountBO accountBO =new AccountBO();
            try {
                accountBO = mapper.readValue(data, new TypeReference<AccountBO>() {
                });
                System.out.println(data);
                return accountBO;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(accountBO.toString());
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

    public String getChatName(Long id){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.retrieve("http://localhost:8080/api/conversation",id);
            ObjectMapper mapper = new ObjectMapper();
            List<AccountBO> conversationBOList =new ArrayList<>();
            try {
                conversationBOList = mapper.readValue(data, new TypeReference<List<AccountBO>>() {
                });
                System.out.println("data: "+data);
                for (AccountBO accountBO : conversationBOList) {
                    if (!accountBO.getUsername().equals((getAccount().getUsername()))){
                        System.out.println(accountBO.getChatName());
                        return accountBO.getChatName();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(conversationBOList.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ConversationBO> getListConver(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.retrieve("http://localhost:8080/api/conversation/user",getAccount().getId());
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

    //start reading method
    private void startReading(String name2) {
        Runnable r1 = () -> {
            System.out.println("reader started...");
            try {
                while (true){
                    String msg = br.readLine();
                    JLabel label = new JLabel(name2+": \n"+msg);
                    label.setFont(new Font("Arial",Font.PLAIN,18));
                    label.setPreferredSize(new Dimension(865,30));
                    label.setHorizontalAlignment(SwingConstants.LEFT);
                    label.setForeground(Color.BLACK);
                    pnMessscn.add(label);
                    pnMessscn.validate();
                    txtMessToSend.setText("");
                    txtMessToSend.requestFocus();
                    System.out.println("Server: "+msg);
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Connection closed!");
            }
        };
        new Thread(r1).start();
    }
}
