package com.vmo.chatapp2.conversation.controller;

import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.conversation.service.Imp.ConversationServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/conversation")
public class ConversationController {
    @Autowired
    private ConversationServiceImp conversationServiceImp;

    @GetMapping("")
    @ResponseBody
    public List<ConversationBO> findAll(){
        return conversationServiceImp.findAll();
    }
}
