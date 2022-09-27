package com.vmo.chatapp2.account.dao;

import com.vmo.chatapp2.account.bo.AccountBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<AccountBO,Long> {
    AccountBO findByUsername(String username);

    @Query("update AccountBO set isActive=true where username =?1")
    Boolean activeLogin(String username);

    @Query("update AccountBO set isActive=false where username =?1")
    Boolean activeLogout(String username);

    Boolean existsAccountBOByChatName(String chatName);

    Boolean existsAccountBOByEmail(String email);

    Boolean existsAccountBOByUsername(String username);


}
