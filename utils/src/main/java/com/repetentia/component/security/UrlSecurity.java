package com.repetentia.component.security;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import lombok.Data;

@Data
public class UrlSecurity {
      private String usergroup;
      private String url;
      private HttpMethod method;

      private String usergroupNm;
      private String usergroupDesc;
      private String enabled;
      private int sqno;
      private int pqno;
      private int depth;
      private String site;
      private UrlSecuritySe menuSe;
      private String menuNm;
}
