package com.repetentia.utils;

public class StringUtils {
    public static boolean isEmpty(String text) {
        return !hasText(text);
    }

    public static boolean hasText(Object text) {
        if (text instanceof String) {
            return org.springframework.util.StringUtils.hasText((String)text);
        } else {
            return false;
        }
    }

    public static boolean hasText(String text) {
        return org.springframework.util.StringUtils.hasText(text);
    }

    public static String camelToUnderscore(String text) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return text.replaceAll(regex, replacement).toLowerCase();
    }

    public static String underscoreToCamel(String text) {

        StringBuilder builder
            = new StringBuilder(text);

        for (int i = 0; i < builder.length(); i++) {

            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }

        return builder.toString();
    }
}
