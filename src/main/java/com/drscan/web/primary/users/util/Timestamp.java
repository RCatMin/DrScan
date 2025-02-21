package com.drscan.web.primary.users.util;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Timestamp {

    @CreatedDate
    @Column(name = "reg_date")
    private java.sql.Timestamp regDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private java.sql.Timestamp modDate;

}