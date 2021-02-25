package com.repetentia.component.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UrlSe implements RtaEnumType {
    P("P", "PAGE", "페이지", ""), M("M", "MENU", "구분메뉴", ""), S("S", "SERVICE", "서비스", ""),;

    private String code;
    private String full;
    private String codename;
    private String detail;

    UrlSe(String code, String full, String codename, String detail) {
        this.code = code;
        this.full = full;
        this.codename = codename;
        this.detail = detail;
    }

    @JsonProperty
    @Override
    public String code() {
        return code;
    }

    @JsonProperty
    @Override
    public String full() {
        return full;
    }

    @JsonProperty
    @Override
    public String codename() {
        return codename;
    }

    @JsonProperty
    @Override
    public String detail() {
        return detail;
    }

    @JsonCreator
    public static UrlSe from(String name) {
        return valueOf(name);
    }
}