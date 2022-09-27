package com.vmo.chatapp2.participant.dao;

import com.vmo.chatapp2.participant.bo.ParticipantBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantDao extends JpaRepository<ParticipantBO,Long> {
}
