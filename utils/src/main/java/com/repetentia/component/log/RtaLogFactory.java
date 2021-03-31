package com.repetentia.component.log;

import org.slf4j.Logger;

public class RtaLogFactory {
    public static Logger getLogger(String name) {
        return org.slf4j.LoggerFactory.getLogger("[" + name + "]");
    }

    public static Logger getLogger(Class<?> clazz) {
        String name = clazz.getSimpleName();
        return getLogger(name);
    }
}
