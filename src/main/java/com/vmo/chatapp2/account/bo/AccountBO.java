package com.vmo.chatapp2.account.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmo.chatapp2.relationship.bo.RelationshipBO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountBO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "chat_name",unique = true, nullable = false)
    private String chatName;

    @Column(name = "role")
    private boolean role;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private Date createAt;

    @Column(name = "updated_at")
    private Date updateAt;

    @Column(name = "status")
    private boolean status;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "path", nullable = false)
    private String path;

}
