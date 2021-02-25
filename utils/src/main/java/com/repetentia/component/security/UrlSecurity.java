package com.repetentia.component.security;


import com.repetentia.component.code.HttpMethod;
import com.repetentia.component.code.UrlSe;

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
      private UrlSe menuSe;
      private String menuNm;
}