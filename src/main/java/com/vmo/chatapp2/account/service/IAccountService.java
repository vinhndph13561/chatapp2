package com.vmo.chatapp2.account.service;

import com.vmo.chatapp2.account.bean.AccountBean;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.form.AccountForm;
import com.vmo.chatapp2.utils.ICommonService;

public interface IAccountService extends ICommonService<AccountBO, AccountBean, AccountForm> {
    boolean saveOrUpdate(AccountForm entity, Long id);

    public Boolean setToken(String username, String token);
}
