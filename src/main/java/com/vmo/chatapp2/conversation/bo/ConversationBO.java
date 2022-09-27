package com.vmo.chatapp2.conversation.bo;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.utils.CommonBO;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "conversation")
public class ConversationBO extends CommonBO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private AccountBO accountBO;

}
