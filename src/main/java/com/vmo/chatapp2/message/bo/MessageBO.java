package com.vmo.chatapp2.message.bo;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "message")
public class MessageBO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private AccountBO sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private ConversationBO conversationBO;

    @Column(name = "message")
    private String message;

}
