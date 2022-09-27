package com.vmo.chatapp2.relationship.form;

import com.vmo.chatapp2.account.bo.AccountBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipForm {
    private Long id;

    private AccountBO sender;

    private AccountBO receiver;

    private String note;
}
