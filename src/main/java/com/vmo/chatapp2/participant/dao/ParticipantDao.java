package com.vmo.chatapp2.participant.dao;

import com.vmo.chatapp2.participant.bo.ParticipantBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantDao extends JpaRepository<ParticipantBO,Long> {
    @Query("select p from ParticipantBO p where p.conversation.id=?1")
    List<ParticipantBO> findByConversation(Long id);

    @Query("select p from ParticipantBO p where p.user.id=?1")
    List<ParticipantBO> findByUser(Long id);
}
