package com.vmo.chatapp2.Auth.controller;

import com.vmo.chatapp2.Auth.form.AuthForm;
import com.vmo.chatapp2.Auth.gui.SignInDialog;
import com.vmo.chatapp2.Auth.service.Imp.AuthServiceImp;
import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.dao.AccountDao;
import com.vmo.chatapp2.account.form.AccountForm;
import com.vmo.chatapp2.account.service.Imp.AccountServiceImp;
import com.vmo.chatapp2.security.JwtResponse;
import com.vmo.chatapp2.security.JwtUtils;
import com.vmo.chatapp2.utils.CommonMsg;
import com.vmo.chatapp2.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.security.Principal;


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
    public String getUsername(Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
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
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        accountService.setToken(userDetails.getUsername(),jwt);
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());

        return new CommonResponse(2,"Login successfully!");
    }

    @PostMapping(value = "/signup",produces = MediaType.APPLICATION_JSON_VALUE)
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
