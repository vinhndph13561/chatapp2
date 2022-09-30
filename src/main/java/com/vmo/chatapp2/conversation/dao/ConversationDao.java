package com.vmo.chatapp2.conversation.dao;

import com.vmo.chatapp2.conversation.bo.ConversationBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationDao extends JpaRepository<ConversationBO,Long> {
    @Query("select c from ConversationBO c where c.accountBO.id=?1")
    List<ConversationBO> findByAccountBO(Long id);
}
