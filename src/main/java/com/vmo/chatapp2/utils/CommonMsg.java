package com.vmo.chatapp2.utils;

import javax.swing.*;
import java.awt.*;

public class CommonMsg {
    public static void alert(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message,"Internal Chat App",JOptionPane.INFORMATION_MESSAGE);
    }
    public static boolean confirm(Component parent, String message){
        int result = JOptionPane.showConfirmDialog(parent, message,"Internal Chat App",JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public static String promt(Component parent, String message){
        return JOptionPane.showInputDialog(parent, message,"Internal Chat App",JOptionPane.INFORMATION_MESSAGE );
    }
}
