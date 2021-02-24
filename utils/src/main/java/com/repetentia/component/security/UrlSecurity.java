package com.repetentia.component.security;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class UrlSecurity {
      private String usergroup;
      private String url;
      private HttpMethod method;
      private String usergroupNm;
      private String usergroupDesc;
      private Boolean enabled;
      private int sqno;
      private int pqno;
      private int depth;
      private String site;
//      @JsonSerialize(using = UrlSeSerializer.class)
//      @JsonSerialize(using = UrlSeConverter.class)
      private UrlSe menuSe;
      private String menuNm;

}