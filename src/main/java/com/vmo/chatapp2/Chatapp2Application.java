package com.vmo.chatapp2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmo.chatapp2.Auth.gui.AuthGUI;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.utils.OkHttpClientCommon;
import okhttp3.ResponseBody;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.swing.*;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class Chatapp2Application {

    public static void main(String[] args) throws IOException {

//        AuthGUI auth = new AuthGUI();
//        auth.setVisible(true);

        SpringApplication.run(Chatapp2Application.class, args);

    }

}
