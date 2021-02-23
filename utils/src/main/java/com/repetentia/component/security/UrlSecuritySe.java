package com.repetentia.component.security;

import java.util.Arrays;

public enum UrlSecuritySe {
    PAGE("P"),
    MENU("M"),
    SERVICE("S"),;
    String se;
    UrlSecuritySe(String se) {
        this.se = se;
    }

    public UrlSecuritySe get(String se) {
        UrlSecuritySe.PAGE.name();
        return UrlSecuritySe.valueOf(se);
    }
    public static void main(String[] args) {
        System.out.println(UrlSecuritySe.PAGE.name());;
        UrlSecuritySe se = UrlSecuritySe.valueOf("PAGE");
System.out.println(se);
        System.out.println(Arrays.toString(UrlSecuritySe.values()));
    }
}