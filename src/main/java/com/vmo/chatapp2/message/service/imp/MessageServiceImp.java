package com.vmo.chatapp2.message.service.imp;

import com.vmo.chatapp2.message.bo.MessageBO;
import com.vmo.chatapp2.message.dao.MessageRepository;
import com.vmo.chatapp2.message.form.MessageForm;
import com.vmo.chatapp2.message.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements IMessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<MessageBO> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public MessageBO findById(Long id) {
        return null;
    }

    @Override
    public boolean saveOrUpdate(MessageForm entity) {
        return true;
    }

    @Override
    public List<MessageBO> FindMessByConversationId(Long id) {
        return messageRepository.FindByConversationId(id);
    }
}
