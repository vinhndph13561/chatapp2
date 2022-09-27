package com.vmo.chatapp2.message.service;

import com.vmo.chatapp2.message.bean.MessageBean;
import com.vmo.chatapp2.message.bo.MessageBO;
import com.vmo.chatapp2.message.form.MessageForm;
import com.vmo.chatapp2.utils.ICommonService;

import java.util.List;

public interface IMessageService extends ICommonService<MessageBO, MessageBean, MessageForm> {
    List<MessageBO> FindMessByConversationId(Long id);
}
