package com.vmo.chatapp2.account.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBean {

    private Long id;

    private String username;

    private String password;

    private String chatName;

    private String role;

    private Date createAt;

    private boolean status;

    private boolean isActive;

    private String path;
}
