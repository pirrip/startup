package com.repetentia.component.security;

import com.repetentia.component.code.HttpMethod;
import com.repetentia.component.code.UrlSe;

import lombok.Data;

@Data
public class UrlSecurity {
    private String url;
    private HttpMethod method;
    private UrlSe menuSe;
    private String menuNm;
    private String auth;
}