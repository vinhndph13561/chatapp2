package com.vmo.chatapp2.account.service.Imp;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.dao.AccountDao;
import com.vmo.chatapp2.account.form.AccountForm;
import com.vmo.chatapp2.account.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImp implements IAccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public List<AccountBO> findAll() {
        return accountDao.findAll();
    }

    @Override
    public AccountBO findById(Long id) {
        Optional<AccountBO> findAccount = accountDao.findById(id);
        if (findAccount.isPresent()){
            return findAccount.get();
        }
        return null;
    }

    @Override
    public boolean saveOrUpdate(AccountForm entity) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(AccountForm entity, Long id) {
        AccountBO accountBO = new AccountBO();
        Date date = new Date();
        AccountBO findAccount = accountDao.findById(id).get();
        if (findAccount == null){
            accountBO.setUsername(entity.getUsername());
            accountBO.setPassword(entity.getPassword());
            accountBO.setChatName(entity.getChatName());
            accountBO.setEmail(entity.getEmail());
            accountBO.setPath(entity.getPath());
            accountBO.setCreateAt(date);
            accountBO.setRole(false);
            accountBO.setStatus(true);
            accountDao.save(accountBO);
            return true;
        }if (findAccount != null){
            findAccount.setUsername(entity.getUsername());
            findAccount.setPassword(entity.getPassword());
            findAccount.setChatName(entity.getChatName());
            findAccount.setEmail(entity.getEmail());
            findAccount.setPath(entity.getPath());
            findAccount.setUpdateAt(date);
            accountDao.save(findAccount);
            return true;
        }
        return false;
    }

    @Override
    public Boolean setToken(String username,String token) {
        AccountBO findAccount = accountDao.findByUsername(username);
        if (findAccount == null){
            return false;
        }
        findAccount.setToken(token);
        findAccount.setActive(true);
        accountDao.save(findAccount);
        return true;
    }
}
