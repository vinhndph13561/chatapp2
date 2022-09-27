package com.vmo.chatapp2.Auth.service.Imp;

import com.vmo.chatapp2.account.bo.AccountBO;
import com.vmo.chatapp2.account.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user =  userRepository.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        Set<UserRole> roles = (Set<UserRole>) user.getListUserRole();
//        for (Role role : roles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), grantedAuthorities);
        AccountBO appUser = this.accountDao.findByUsername(username);
        if(appUser == null) {
            System.out.println("User not found! "+username);
            throw new UsernameNotFoundException("User " + username + " was not found");
        }
        else {
            System.out.println("User found! "+username);
            System.out.println("Password: " + appUser.getPassword());
        }
        String role = appUser.isRole()?"ROLE_ADMIN":"ROLE_STAFF";
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantedAuthorities.add(authority);

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(), appUser.getPassword(), grantedAuthorities);
    }
}
