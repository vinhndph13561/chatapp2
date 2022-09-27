package com.vmo.chatapp2.Auth.service.Imp;

import com.vmo.chatapp2.Auth.form.AuthForm;
import com.vmo.chatapp2.Auth.service.AuthService;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AccountDao accountDao;

    @Override
    public int login(AuthForm user) {
        AccountBO user2 = accountDao.findByUsername(user.getUsername());

        // response
        if (user2 == null) {
            return 1;
        }
        if (passwordEncoder.matches(user.getPassword(),user2.getPassword())) {
            return 3;
        }else {
            return 2;
        }
    }
}
