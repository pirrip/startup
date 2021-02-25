package com.repetentia.component.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum HttpMethod implements RtaEnumType {

    GET("GET", "GET", "GET", "HTTP GET"),
    HEAD("HEAD", "HEAD", "HEAD", "HTTP HEAD"),
    POST("POST", "POST", "POST", "HTTP POST"),
    PUT("PUT", "PUT", "PUT", "HTTP PUT"),
    PATCH("PATCH", "PATCH", "PATCH", "HTTP PATCH"),
    DELETE("DELETE", "DELETE", "DELETE", "HTTP DELETE"),
    OPTIONS("OPTIONS", "OPTIONS", "OPTIONS", "HTTP OPTIONS"),
    TRACE("TRACE", "TRACE", "TRACE", "HTTP TRACE");

    private String code;
    private String full;
    private String codename;
    private String detail;

    HttpMethod(String code, String full, String codename, String detail) {
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
    public static HttpMethod from(String name) {
        return valueOf(name);
    }

}
