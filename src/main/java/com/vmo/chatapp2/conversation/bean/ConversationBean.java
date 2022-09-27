package com.vmo.chatapp2.conversation.bean;


import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.utils.CommonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationBean extends CommonBean {
    private Long id;

    private AccountBO accountBO;
}
