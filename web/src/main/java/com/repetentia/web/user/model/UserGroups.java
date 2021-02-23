package com.repetentia.web.user.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

@Data
public class UserGroups {
    @Id
    @Column(name = "usergroup")
    private String usergroup;
    @Column(name = "usergroupNm")
    private String usergroupNm;
    @Column(name = "usergroupDesc")
    private String usergroupDesc;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "crdt")
    private LocalDateTime crdt;
    @Column(name = "crid")
    private String crid;
    @Column(name = "updt")
    private LocalDateTime updt;
    @Column(name = "upid")
    private String upid;
}
