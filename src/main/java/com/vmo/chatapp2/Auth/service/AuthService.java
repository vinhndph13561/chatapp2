package com.vmo.chatapp2.Auth.service;

import com.vmo.chatapp2.Auth.form.AuthForm;
import com.vmo.chatapp2.account.bo.AccountBO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    int login(AuthForm user);
}
