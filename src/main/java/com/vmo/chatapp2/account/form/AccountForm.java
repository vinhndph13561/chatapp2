package com.vmo.chatapp2.account.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountForm implements Serializable {
    private String username;

    private String password;

    private String chatName;

    private String path;

    private String email;
}
