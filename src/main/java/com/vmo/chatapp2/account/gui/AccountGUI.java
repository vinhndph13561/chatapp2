package com.vmo.chatapp2.account.gui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.form.AccountForm;
import com.vmo.chatapp2.utils.CommonMsg;
import com.vmo.chatapp2.utils.CommonResponse;
import com.vmo.chatapp2.utils.OkHttpClientCommon;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AccountGUI {

    public JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JTextField textField5;
    private JButton updateButton;
    private JLabel lblAvatar;
    private JButton logOutButton;
    File fileAnhSP;

    public AccountGUI() {
        AccountBO bo = getAccount(Long.valueOf(1));
        textField1.setText(bo.getUsername());
        textField2.setText(bo.getPassword());
        textField4.setText(bo.getChatName());
        textField5.setText(bo.getEmail());
        lblAvatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ImageHandle();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OkHttpClientCommon okHttpClientExample = new OkHttpClientCommon();
                AccountForm accountForm = new AccountForm();
                accountForm.setUsername(textField1.getText());
                accountForm.setPassword(textField2.getText());
                accountForm.setEmail(textField4.getText());
                accountForm.setChatName(textField5.getText());
                accountForm.setPath(lblAvatar.getToolTipText());
                try {
                    CommonResponse cRes = okHttpClientExample.update("http://localhost:8080/api/auth/signup",accountForm, Long.valueOf(1));
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
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1.setVisible(false);
            }
        });
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

    private void ImageHandle() {
        JFileChooser fileChooser = new JFileChooser("src\\main\\resources\\assets\\images\\avatars");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tệp hình ảnh", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileAnhSP = fileChooser.getSelectedFile();
            lblAvatar.setIcon(getImage(fileAnhSP.getPath()));
            lblAvatar.setToolTipText(fileAnhSP.getName());
            lblAvatar.setText("");
        }
    }

    private ImageIcon getImage(String src) {
        src = src.trim().equals("") ? "1.jpeg" : src;
        //xử lý ảnh
        BufferedImage img = null;
        File fileImg = new File(src);

        if (!fileImg.exists()) {
            src = "1.jpeg";
            fileImg = new File("src\\main\\resources\\assets\\images\\avatars" + src);
        }
        try {
            img = ImageIO.read(fileImg);
            fileAnhSP = new File(src);

        } catch (IOException e) {
            fileAnhSP = new File("src\\main\\resources\\assets\\images\\avatars\\1.jpeg");
        }
        if (img != null) {
            Image dimg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }

        return null;
    }
}
