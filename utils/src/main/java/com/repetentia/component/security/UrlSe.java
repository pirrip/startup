package com.repetentia.component.security;

import com.repetentia.support.json.RtaEnumType;

public enum UrlSe implements RtaEnumType {
    PAGE("P", "PAGE", "페이지", ""),
    MENU("M", "MENU", "구분메뉴", ""),
    SERVICE("S", "SERVICE", "서비스", ""),;

    String code;
    String value;
    String codeName;
    String codeDesc;

    UrlSe(String code, String value, String codeName, String codeDesc) {
        this.code = code;
        this.value = value;
        this.codeName = codeName;
        this.codeDesc = codeDesc;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String codeName() {
        return codeName;
    }

    @Override
    public String codeDesc() {
        return codeDesc;
    }

}