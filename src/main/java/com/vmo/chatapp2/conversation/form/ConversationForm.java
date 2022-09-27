package com.vmo.chatapp2.conversation.form;

import com.vmo.chatapp2.account.bo.AccountBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationForm {
    private Long id;

    private AccountBO accountBO;

    private int status;

    private Long userId;

    private Integer page;

    private Integer recordPage;
}
