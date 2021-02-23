package com.repetentia.web.user.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

@Data
public class UserAuthsUrl {
    @Id
    @Column(name = "sqno")
    private int sqno;
    @Column(name = "pqno")
    private int pqno;
    @Column(name = "depth")
    private int depth;
    @Column(name = "site")
    private String site;
    @Column(name = "menu_se")
    private String menuSe;
    @Column(name = "menu_nm")
    private String menuNm;
    @Column(name = "url")
    private String url;
    @Column(name = "method")
    private String method;
    @Column(name = "crdt")
    private LocalDateTime crdt;
    @Column(name = "crid")
    private String crid;
    @Column(name = "updt")
    private LocalDateTime updt;
    @Column(name = "upid")
    private String upid;
}