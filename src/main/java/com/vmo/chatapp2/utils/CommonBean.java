package com.vmo.chatapp2.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonBean {
    private Date createdAt;

    private Date deleteAt;

    private BigInteger modifiedBy;

    private Date updateAt;

    private BigInteger updateBy;

    private Integer status;
}
