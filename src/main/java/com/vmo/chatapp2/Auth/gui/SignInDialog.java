package com.vmo.chatapp2.Auth.gui;

import com.vmo.chatapp2.utils.CommonMsg;
import com.vmo.chatapp2.utils.CommonResponse;
import com.vmo.chatapp2.utils.OkHttpClientCommon;
import com.vmo.chatapp2.account.form.AccountForm;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SignInDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JTextField txtEmail;
    private JTextField txtChatName;
    private JLabel lblAvatar;
    File fileAnhSP;


    public SignInDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OkHttpClientCommon okHttpClientExample = new OkHttpClientCommon();
                AccountForm accountForm = new AccountForm();
                accountForm.setUsername(txtUsername.getText());
                accountForm.setPassword(txtPassword.getText());
                accountForm.setEmail(txtEmail.getText());
                accountForm.setChatName(txtChatName.getText());
                accountForm.setPath(lblAvatar.getToolTipText());
                try {
                    CommonResponse cRes = okHttpClientExample.create("http://localhost:8080/api/auth/signup",accountForm);
                    if (cRes.getStatus()==0){
                        CommonMsg.alert(contentPane,cRes.getMessage());

                    }
                    if (cRes.getStatus()==1){
                        CommonMsg.alert(contentPane,cRes.getMessage());
                        onOK();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        lblAvatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                JFileChooser fileChooser = new JFileChooser();
//                int returnVal = fileChooser.showOpenDialog(rootPane);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    java.io.File file = fileChooser.getSelectedFile();
//                    lblAvatar.setIcon(fileChooser.getIcon(file));
//                } else {
//
//                }
                ImageHandle();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
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

    public static void main(String[] args) {
        SignInDialog dialog = new SignInDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
