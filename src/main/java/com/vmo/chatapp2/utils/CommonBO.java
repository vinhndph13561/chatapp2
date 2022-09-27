package com.vmo.chatapp2.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigInteger;
import java.util.Date;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
public class CommonBO {
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "delete_at")
    private Date deleteAt;

    @Column(name = "delete_by")
    private Long deleteBy;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "status")
    private Integer status;

    @Override
    public String toString() {
        return "CommonBO{" +
                "createdAt=" + createdAt +
                ", deleteAt=" + deleteAt +
                ", modifiedBy=" + deleteBy +
                ", updateAt=" + updateAt +
                ", updateBy=" + updateBy +
                ", status=" + status +
                '}';
    }
}
