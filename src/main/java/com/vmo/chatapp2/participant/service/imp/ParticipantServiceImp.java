package com.vmo.chatapp2.participant.service.imp;

import com.vmo.chatapp2.account.dao.AccountDao;
import com.vmo.chatapp2.conversation.dao.ConversationDao;
import com.vmo.chatapp2.participant.bo.ParticipantBO;
import com.vmo.chatapp2.participant.dao.ParticipantDao;
import com.vmo.chatapp2.participant.form.ParticipantForm;
import com.vmo.chatapp2.participant.service.IParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipantServiceImp implements IParticipantService {
    @Autowired
    private ParticipantDao participantDao;

    private ConversationDao conversationDao;

    private AccountDao accountDao;

    @Override
    public List<ParticipantBO> findAll() {
        return participantDao.findAll();
    }

    @Override
    public ParticipantBO findById(Long id) {
        Optional<ParticipantBO> findParticipant = participantDao.findById(id);
        if (findParticipant.isPresent()){
            return findParticipant.get();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(ParticipantForm entity) {
        ParticipantBO participantBO = new ParticipantBO();
        Date date = new Date();
        ParticipantBO findAccount = participantDao.findById(entity.getId()).map(participant -> {
            participant.setUpdateAt(date);
            return participantDao.save(participant);
        }).orElseGet(() ->{
            participantBO.setCreatedAt(date);
            participantBO.setUser(accountDao.findById(entity.getUser().getId()).get());
            participantBO.setConversation(conversationDao.findById(entity.getConversation().getId()).get());
            return participantDao.save(participantBO);
        });
        return true;
    }
}
