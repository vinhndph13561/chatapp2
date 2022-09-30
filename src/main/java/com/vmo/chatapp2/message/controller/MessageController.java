package com.vmo.chatapp2.message.controller;

import com.vmo.chatapp2.message.bo.MessageBO;
import com.vmo.chatapp2.message.form.MessDeleteForm;
import com.vmo.chatapp2.message.form.MessageForm;
import com.vmo.chatapp2.message.service.imp.MessageServiceImp;
import com.vmo.chatapp2.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageServiceImp messageServiceImp;

    @GetMapping("")
    @ResponseBody
    public List<MessageBO> findAll(){
        return messageServiceImp.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public List<MessageBO> findMessByConversation(@PathVariable Long id){

        return messageServiceImp.FindMessByConversationId(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public CommonResponse deleteMess(@RequestBody MessDeleteForm mess, @PathVariable Long id){
        if (messageServiceImp.deleteMessage(mess,id)){
            return new CommonResponse(1,"success");
        }else {
            return new CommonResponse(0,"failed");
        }
    }

    @PostMapping("")
    @ResponseBody
    public CommonResponse saveMess(@RequestBody MessageForm messageForm){
        if (messageServiceImp.saveOrUpdate(messageForm)){
            return new CommonResponse(1,"success");
        }else {
            return new CommonResponse(0,"failed");
        }
    }
}
