package com.repetentia.web.user.model;

import lombok.Data;

@Data
public class UserDetails {
	private String un;
    private String pw;
    private String accountNonExpired;
    private String accountNonLocked;
    private String credentials_non_expired;
    private String enabled;
    private String usergroup;
    private String createDt;
    private String createUn;
    private String updateDt;
    private String updateUn;
}
