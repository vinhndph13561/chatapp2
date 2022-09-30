package com.vmo.chatapp2.participant.bo;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.conversation.bo.ConversationBO;
import com.vmo.chatapp2.utils.CommonBO;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "participant")
public class ParticipantBO extends CommonBO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AccountBO user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private ConversationBO conversation;

}
