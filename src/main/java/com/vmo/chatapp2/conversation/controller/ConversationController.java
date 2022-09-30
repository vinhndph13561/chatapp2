package com.vmo.chatapp2.conversation.controller;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.conversation.form.ConversationForm;
import com.vmo.chatapp2.conversation.service.Imp.ConversationServiceImp;
import com.vmo.chatapp2.participant.bo.ParticipantBO;
import com.vmo.chatapp2.participant.service.imp.ParticipantServiceImp;
import com.vmo.chatapp2.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/conversation")
public class ConversationController {
    @Autowired
    private ConversationServiceImp conversationServiceImp;

    @Autowired
    private ParticipantServiceImp participantServiceImp;

    @GetMapping("")
    @ResponseBody
    public List<ConversationBO> findAll(){
        return conversationServiceImp.findAll();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public List<ConversationBO> findByUser(@PathVariable Long id){
        List<ParticipantBO> participantBOS = participantServiceImp.findByUser(id);
        List<ConversationBO> list = new ArrayList<>();
        for (ParticipantBO participantBO :participantBOS) {
            list.add(participantBO.getConversation());
        }
        if (list.isEmpty()){
            return null;
        }
        return list;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public List<AccountBO> findParticipant(@PathVariable Long id){
        List<AccountBO> accountBOList = new ArrayList<>();
        List<ParticipantBO> participantBOS = participantServiceImp.findByConversationId(id);
        System.out.println("parti"+participantBOS);
        for (ParticipantBO participantBO: participantBOS) {
            accountBOList.add(participantBO.getUser());
        }
        return accountBOList;
    }

    @PostMapping("")
    @ResponseBody
    public CommonResponse addConversation(@RequestBody ConversationForm conversationForm){
        return new CommonResponse();
    }
}
