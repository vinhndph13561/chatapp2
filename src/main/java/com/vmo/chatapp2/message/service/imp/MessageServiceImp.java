package com.vmo.chatapp2.message.service.imp;

import com.vmo.chatapp2.message.bo.MessageBO;
import com.vmo.chatapp2.message.dao.MessageRepository;
import com.vmo.chatapp2.message.form.MessDeleteForm;
import com.vmo.chatapp2.message.form.MessageForm;
import com.vmo.chatapp2.message.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        MessageBO messageBO = new MessageBO();
        Date date = new Date();
        messageBO.setSender(entity.getSender());
        messageBO.setConversationBO(entity.getConversationBO());
        messageBO.setMessage(entity.getMessage());
        messageBO.setStatus(1);
        messageRepository.save(messageBO);
        return true;
    }

    @Override
    public boolean deleteMessage(MessDeleteForm Mess, Long id) {
        Optional<MessageBO> mess =messageRepository.findById(Mess.getId());
        if (mess.isPresent()){
            mess.get().setDeleteBy(id);
            mess.get().setDeleteAt(new Date());
            mess.get().setStatus(0);
            return true;
        }
        return false;
    }

    @Override
    public List<MessageBO> FindMessByConversationId(Long id) {
        return messageRepository.FindByConversationId(id);
    }
}
