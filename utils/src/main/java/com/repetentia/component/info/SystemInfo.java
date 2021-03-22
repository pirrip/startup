package com.repetentia.component.info;

public class SystemInfo {
    private static String springActiveProfiles;

    public static String getSpringActiveProfiles() {
        return springActiveProfiles;
    }

    public static void setSpringActiveProfiles(String springActiveProfiles) {
        SystemInfo.springActiveProfiles = springActiveProfiles;
    }
}
