package com.vmo.chatapp2.relationship.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import com.vmo.chatapp2.utils.OkHttpClientCommon;

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
    ArrayList<JLabel> listLabel2 = new ArrayList<>();
    ArrayList<JPanel> listPanel = new ArrayList<>();
    ArrayList<JPanel> listPanel2 = new ArrayList<>();

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
        List<AccountBO> list = getListAccount();
        btnAdd = new JButton();
        btnAdd.setPreferredSize(new Dimension(100,33));
        btnAdd.setText("Add friend");
        btnCancel = new JButton();
        btnCancel.setText("Unfriend");
        btnCancel.setPreferredSize(new Dimension(100,33));
        for (AccountBO bo: list){
            listLabel.add(new JLabel(bo.getChatName()));
            listPanel.add(new JPanel());
        }
        for (JLabel label: listLabel) {
            Font font = new Font("Arial",Font.PLAIN,18);
            label.setFont(font);
        }
        for (AccountBO bo: list){
            String id = String.valueOf(bo.getId());
            listPanel.get(list.indexOf(bo)).add(listLabel.get(list.indexOf(bo)));
            listPanel.get(list.indexOf(bo)).setName(id);
            listPanel.get(list.indexOf(bo)).setPreferredSize(new Dimension(280,40));

        }
        for (JPanel panel: listPanel){
            panel.setLayout(new FlowLayout());
            panel.setAlignmentX(SwingConstants.CENTER);
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    pnDetail.removeAll();
                    AccountBO accountBO = getAccount(Long.valueOf(panel.getName()));
                    ImageIcon icon = new ImageIcon("src\\main\\resources\\assets\\images\\avatars\\"+accountBO.getPath());
                    lblAvatar.setIcon(icon);
                    lblName.setText(accountBO.getChatName());
                    pnDetail.add(lblAvatar);
                    pnDetail.add(lblName);
                    pnDetail.add(txtNote);
                    pnDetail.add(btnAdd);
                    pnDetail.add(btnCancel);
                    pnDetail.validate();
                }
            });
            pnList.add(panel);
        }

        friendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnDetail.removeAll();
                List<RelationshipBO> relationshipBOList = getAllRela();
                for (RelationshipBO bo: relationshipBOList){
                    listLabel2.add(new JLabel(bo.getSender().getChatName()));
                    listPanel2.add(new JPanel());
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

                }
                for (JPanel panel: listPanel2){
                    panel.setLayout(new FlowLayout());
                    panel.setAlignmentX(SwingConstants.CENTER);
                    panel.add(new JButton("Accept"));
                    panel.add(new JButton("Cancel"));
                    pnDetail.add(panel);

                }
                pnDetail.validate();
            }
        });
    }

    public List<RelationshipBO> getAllRela(){
        OkHttpClientCommon ok = new OkHttpClientCommon();
        try {
            String data = ok.access("http://localhost:8080/api/relationship/request");
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
