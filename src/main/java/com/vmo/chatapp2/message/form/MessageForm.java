package com.vmo.chatapp2.message.form;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageForm {
    private AccountBO sender;

    private ConversationBO conversationBO;

    private String message;
}
