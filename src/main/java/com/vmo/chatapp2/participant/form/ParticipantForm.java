package com.vmo.chatapp2.participant.form;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantForm {
    private Long id;

    private AccountBO user;

    private ConversationBO conversation;
}
