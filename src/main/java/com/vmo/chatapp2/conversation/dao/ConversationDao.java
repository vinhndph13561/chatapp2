package com.vmo.chatapp2.conversation.dao;

import com.vmo.chatapp2.conversation.bo.ConversationBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationDao extends JpaRepository<ConversationBO,Long> {

}
