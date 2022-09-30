package com.vmo.chatapp2.Auth.controller;

import com.vmo.chatapp2.Auth.bean.LoginBean2;
import com.vmo.chatapp2.Auth.form.AuthForm;
import com.vmo.chatapp2.Auth.bean.LoginBean;
import com.vmo.chatapp2.Auth.service.Imp.AuthServiceImp;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.dao.AccountDao;
import com.vmo.chatapp2.account.form.AccountForm;
import com.vmo.chatapp2.account.service.Imp.AccountServiceImp;
import com.vmo.chatapp2.security.JwtUtils;
import com.vmo.chatapp2.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
@RequestMapping(path = "/api/auth")
public class AuthController {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountServiceImp accountService;

    @Autowired
    private AuthServiceImp authServiceImp;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/principal")
    @ResponseBody
    public AccountBO getUser(){
        return LoginBean.account;
    }

    @GetMapping("/clientprincipal")
    @ResponseBody
    public AccountBO getClient(){
        return LoginBean2.account;
    }

    @PostMapping("/logout/client")
    @ResponseBody
    public void logoutClient(){
        LoginBean2.account = null;
    }

    @PostMapping("/logout/user")
    @ResponseBody
    public void logoutUser(){
        LoginBean.account = null;
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonResponse login(@Valid @RequestBody AuthForm user) {
        if (authServiceImp.login(user)==1){
            return new CommonResponse(0,"User name is incorrect!");
        }
        if (authServiceImp.login(user)==2) {
            return new CommonResponse(1,"Password is incorrect!");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (LoginBean.account==null) {
            LoginBean.account = accountDao.findByUsername(user.getUsername());
        }else {
            LoginBean2.account = accountDao.findByUsername(user.getUsername());
        }
        System.out.println(LoginBean.account.getUsername()+"abc");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        accountService.setToken(userDetails.getUsername(),jwt);
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());

        return new CommonResponse(2,"Login successfully!");
    }

    @PostMapping( "/signup")
    @ResponseBody
    public CommonResponse register(@Valid @RequestBody AccountForm signUpRequest) {
        if (accountDao.existsAccountBOByChatName(signUpRequest.getChatName())) {

            return new CommonResponse(0,"Chat name has already existed!");
        }
        if (accountDao.existsAccountBOByEmail(signUpRequest.getEmail())) {

            return new CommonResponse(0,"Email has already existed!");
        }
        if (accountDao.existsAccountBOByUsername(signUpRequest.getUsername())) {

            return new CommonResponse(0,"Username has already existed!");
        }
        AccountForm user = new AccountForm(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getChatName(),
                signUpRequest.getPath(),
                signUpRequest.getEmail());

        accountService.saveOrUpdate(user,null);
        System.out.println(signUpRequest.getUsername());
        return new CommonResponse(1,"Register successfully!");
    }

}
