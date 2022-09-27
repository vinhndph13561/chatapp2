package com.vmo.chatapp2.message.dao;

import com.vmo.chatapp2.message.bo.MessageBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageBO,Long> {
    @Query("select m from MessageBO m where m.conversationBO.id=?1")
    List<MessageBO> FindByConversationId(Long id);
}
