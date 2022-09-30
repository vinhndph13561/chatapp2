package com.vmo.chatapp2.relationship.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import com.vmo.chatapp2.relationship.form.RelationshipForm;
import com.vmo.chatapp2.relationship.form.UpdateRelaForm;
import com.vmo.chatapp2.utils.CommonMsg;
import com.vmo.chatapp2.utils.CommonResponse;
import com.vmo.chatapp2.utils.OkHttpClientCommon;
import org.springframework.jdbc.object.UpdatableSqlQuery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RelationshipGUI extends JPanel {
    public JPanel pnRela;
    public  JPanel pnabc;
    private JPanel pnSearch;
    public  JPanel pnList;
    private JPanel pnDetail;
    private JTextField txtSeacrh;
    private JButton searchButton;
    private JButton friendRequestButton;
    private JLabel lblAvatar;
    private JLabel lblName;
    private JTextField txtNote;
    private JButton btnAdd;
    private JButton btnCancel;
    ArrayList<JLabel> listLabel = new ArrayList<>();
    ArrayList<JLabel> listLabel3 = new ArrayList<>();
    ArrayList<JLabel> listLabel2 = new ArrayList<>();
    ArrayList<JPanel> listPanel = new ArrayList<>();
    ArrayList<JPanel> listPanel2 = new ArrayList<>();
    ArrayList<JButton> listBtnAc = new ArrayList<>();
    ArrayList<JButton> listBtnCa = new ArrayList<>();
    AccountBO accountBO = new AccountBO();

    public RelationshipGUI(){
        lblAvatar = new JLabel();
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setVerticalAlignment(SwingConstants.CENTER);
        lblAvatar.setPreferredSize(new Dimension(800,250));
        lblName = new JLabel();
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setVerticalAlignment(SwingConstants.CENTER);
        lblName.setPreferredSize(new Dimension(800,80));
        lblName.setFont(new Font("Arial",Font.BOLD,30));
        lblName.setForeground(Color.BLACK);
        txtNote = new JTextField();
        txtNote.setPreferredSize(new Dimension(750,200));

        btnAdd = new JButton();
        btnAdd.setPreferredSize(new Dimension(100,33));
        btnAdd.setText("Add friend");
        btnCancel = new JButton();
        btnCancel.setText("Unfriend");
        btnCancel.setPreferredSize(new Dimension(100,33));
        List<AccountBO> list = getListAccount();
        List<AccountBO> listFriend = getFriend();
        for (AccountBO ac:listFriend
             ) {
            System.out.println(ac.getChatName());
        }
        for (AccountBO bo: list){
            listLabel.add(new JLabel(bo.getChatName()));
            listPanel.add(new JPanel());
            listLabel3.add(new JLabel());
        }
        for (JLabel label:listLabel3){
            label.setPreferredSize(new Dimension(80,33));
            Font font = new Font("Arial",Font.ITALIC,15);
            label.setFont(font);
        }
        for (JLabel label: listLabel) {
            label.setPreferredSize(new Dimension(180,33));
            Font font = new Font("Arial",Font.PLAIN,18);
            label.setFont(font);
        }
        for (AccountBO bo: list){
            String id = String.valueOf(bo.getId());
            listPanel.get(list.indexOf(bo)).add(listLabel.get(list.indexOf(bo)));
            listPanel.get(list.indexOf(bo)).setName(id);
            listPanel.get(list.indexOf(bo)).setPreferredSize(new Dimension(280,35));
            for (AccountBO accountBO: listFriend) {
                if (accountBO.getUsername().equals(bo.getUsername())){
                    System.out.println("friend");
                    listLabel3.get(list.indexOf(bo)).setText("friend");
                    listPanel.get(list.indexOf(bo)).add(listLabel3.get(list.indexOf(bo)));
                }
            }
        }
        for (JPanel panel: listPanel){
            panel.setLayout(new FlowLayout());
            panel.setAlignmentX(SwingConstants.LEFT);
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    pnDetail.removeAll();
                    pnDetail.validate();
                    accountBO = getAccount(Long.valueOf(panel.getName()));
                    ImageIcon icon = new ImageIcon("src\\main\\resources\\assets\\images\\avatars\\"+accountBO.getPath());
                    lblAvatar.setIcon(icon);
                    lblName.setText(accountBO.getChatName());
                    pnDetail.add(lblAvatar);
                    pnDetail.add(lblName);
                    pnDetail.add(txtNote);
                    if (listLabel3.get(listPanel.indexOf(panel)).getText().isEmpty()){
                        pnDetail.add(btnAdd);
                    }else {
                        pnDetail.add(btnCancel);
                    }
                    pnDetail.validate();
                }
            });
            pnDetail.validate();
            pnList.add(panel);

        }
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OkHttpClientCommon ok = new OkHttpClientCommon();
                RelationshipForm form = new RelationshipForm();
                form.setSender(getUser());
                System.out.println(getUser());
                form.setReceiver(accountBO);
                System.out.println(accountBO);
                form.setNote(txtNote.getText());
                try {
                    CommonResponse cm =ok.create("http://localhost:8080/api/relationship",form);
                    CommonMsg.alert(pnRela,cm.getMessage());
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        friendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnDetail.removeAll();
                List<RelationshipBO> relationshipBOList = getAllRela();
                for (RelationshipBO bo: relationshipBOList){
                    listLabel2.add(new JLabel(bo.getSender().getChatName()));
                    listPanel2.add(new JPanel());
                    listBtnAc.add(new JButton("Accept"));
                    listBtnCa.add(new JButton("Cancel"));
                }
                for (JLabel label: listLabel2) {
                    Font font = new Font("Arial",Font.PLAIN,18);
                    label.setFont(font);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.CENTER);
                    label.setPreferredSize(new Dimension(400,95));
                }
                for (RelationshipBO bo: relationshipBOList){
                    String id = String.valueOf(bo.getId());
                    listPanel2.get(relationshipBOList.indexOf(bo)).add(listLabel2.get(relationshipBOList.indexOf(bo)));
                    listPanel2.get(relationshipBOList.indexOf(bo)).setName(id);
                    listPanel2.get(relationshipBOList.indexOf(bo)).setPreferredSize(new Dimension(800,100));
                    listBtnAc.get(relationshipBOList.indexOf(bo)).addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            OkHttpClientCommon ok = new OkHttpClientCommon();
                            UpdateRelaForm form = new UpdateRelaForm();
                            form.setId(bo.getId());
                            try {
                                CommonResponse cm =ok.update("http://localhost:8080/api/relationship",form,1L);
                                CommonMsg.alert(pnRela,cm.getMessage());
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                    listBtnCa.get(relationshipBOList.indexOf(bo)).addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            OkHttpClientCommon ok = new OkHttpClientCommon();
                            UpdateRelaForm form = new UpdateRelaForm();
                            form.setId(bo.getId());
                            try {
                                CommonResponse cm =ok.update("http://localhost:8080/api/relationship",form,2L);
                                CommonMsg.alert(pnRela,cm.getMessage());
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                }
                for (JPanel panel: listPanel2){
                    panel.setLayout(new FlowLayout());
                    panel.setAlignmentX(SwingConstants.CENTER);
                    panel.add(listBtnAc.get(listPanel2.indexOf(panel)));
                    panel.add(listBtnCa.get(listPanel2.indexOf(panel)));
                    pnDetail.add(panel);

                }
                pnDetail.validate();
            }
        });
    }

    public List<RelationshipBO> getAllRela(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.retrieve("http://localhost:8080/api/relationship/friendrequest",getUser().getId());
            System.out.println(data);
            ObjectMapper mapper = new ObjectMapper();
            List<RelationshipBO> messBOList =new ArrayList<>();
            try {
                messBOList = mapper.readValue(data, new TypeReference<List<RelationshipBO>>() {
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

    public AccountBO getUser(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.access("http://localhost:8080/api/auth/principal");
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

    public List<RelationshipBO> getRela(Long id){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.retrieve("http://localhost:8080/api/relationship",id);
            System.out.println(data);
            ObjectMapper mapper = new ObjectMapper();
            List<RelationshipBO> messBOList =new ArrayList<>();
            try {
                messBOList = mapper.readValue(data, new TypeReference<List<RelationshipBO>>() {
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

    public List<AccountBO> getFriend(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.retrieve("http://localhost:8080/api/relationship/friend",getUser().getId());
            System.out.println(data);
            ObjectMapper mapper = new ObjectMapper();
            List<AccountBO> messBOList =new ArrayList<>();
            try {
                messBOList = mapper.readValue(data, new TypeReference<List<AccountBO>>() {
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

    public AccountBO getAccount(Long id){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.retrieve("http://localhost:8080/api/account",id);
            System.out.println(data);
            ObjectMapper mapper = new ObjectMapper();
            AccountBO messBOList =new AccountBO();
            try {
                messBOList = mapper.readValue(data, new TypeReference<AccountBO>() {
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

    public List<AccountBO> getListAccount(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.access("http://localhost:8080/api/account");
            System.out.println(data);
            ObjectMapper mapper = new ObjectMapper();
            List<AccountBO> messBOList =new ArrayList<>();
            try {
                messBOList = mapper.readValue(data, new TypeReference<List<AccountBO>>() {
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
}
