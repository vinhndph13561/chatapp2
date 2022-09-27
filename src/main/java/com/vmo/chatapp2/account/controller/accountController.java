package com.vmo.chatapp2.account.controller;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.form.AccountForm;
import com.vmo.chatapp2.account.service.Imp.AccountServiceImp;
import com.vmo.chatapp2.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/account")
public class accountController {
    @Autowired
    private AccountServiceImp accountServiceImp;

    @GetMapping("/{id}")
    @ResponseBody
    public AccountBO findAccount(@PathVariable Long id){

        return accountServiceImp.findById(id);
    }

    @GetMapping("")
    @ResponseBody
    public List<AccountBO> findAll(){

        return accountServiceImp.findAll();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public CommonResponse updateAccount(@RequestBody AccountForm accountForm, @PathVariable Long id){
        if (accountServiceImp.saveOrUpdate(accountForm)){
            return new CommonResponse(0,"success!");
        }
        return new CommonResponse(1,"failed!");
    }
}
