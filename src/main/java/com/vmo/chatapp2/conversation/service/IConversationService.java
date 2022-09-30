package com.vmo.chatapp2.conversation.service;

import com.vmo.chatapp2.conversation.bean.ConversationBean;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.conversation.form.ConversationForm;
import com.vmo.chatapp2.utils.ICommonService;

import java.util.List;

public interface IConversationService extends ICommonService<ConversationBO, ConversationBean, ConversationForm> {
    List<ConversationBO> findByUserId(Long id);
}
