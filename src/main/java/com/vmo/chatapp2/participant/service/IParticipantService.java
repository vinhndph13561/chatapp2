package com.vmo.chatapp2.participant.service;

import com.vmo.chatapp2.participant.bean.ParticipantBean;
import com.vmo.chatapp2.participant.bo.ParticipantBO;
import com.vmo.chatapp2.participant.form.ParticipantForm;
import com.vmo.chatapp2.utils.ICommonService;

import java.util.List;

public interface IParticipantService extends ICommonService<ParticipantBO, ParticipantBean, ParticipantForm> {

    List<ParticipantBO> findByConversationId(Long id);

    List<ParticipantBO> findByUser(Long id);
}
