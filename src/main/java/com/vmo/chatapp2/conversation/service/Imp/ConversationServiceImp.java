package com.vmo.chatapp2.conversation.service.Imp;

import com.vmo.chatapp2.account.dao.AccountDao;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.conversation.dao.ConversationDao;
import com.vmo.chatapp2.conversation.form.ConversationForm;
import com.vmo.chatapp2.conversation.service.IConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationServiceImp implements IConversationService {
    @Autowired
    private ConversationDao conversationDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<ConversationBO> findAll() {
        return conversationDao.findAll();
    }

    @Override
    public ConversationBO findById(Long id) {
        Optional<ConversationBO> findConversation = conversationDao.findById(id);
        if (findConversation.isPresent()){
            return findConversation.get();
        }
        return null;
    }

    @Override
    public List<ConversationBO> findByUserId(Long id) {
        List<ConversationBO> findConversation = conversationDao.findByAccountBO(id);
        if (findConversation!=null){
            return findConversation;
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(ConversationForm entity) {
        ConversationBO conversationBO = new ConversationBO();
        Date date = new Date();
        ConversationBO findAccount = conversationDao.findById(entity.getId()).map(conversation -> {
            conversation.setStatus(entity.getStatus());
            if (entity.getStatus()==1){
                conversation.setUpdateAt(date);
                conversation.setUpdateBy(entity.getUserId());
                return conversationDao.save(conversation);
            }else if (entity.getStatus()==2){
                conversation.setDeleteAt(date);
                conversation.setDeleteBy(entity.getUserId());
                return conversationDao.save(conversation);
            }else{
                return null;
            }
        }).orElseGet(() ->{
            conversationBO.setCreatedAt(date);
            conversationBO.setAccountBO(accountDao.findById(entity.getAccountBO().getId()).get());
            return conversationDao.save(conversationBO);
        });

        return true;
    }
}
