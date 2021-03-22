package com.repetentia.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

public class BeanUtils {
    public static String getTableName(Class<? extends Object> clazz) {
        Table table = clazz.getDeclaredAnnotation(Table.class);
        return table.name();
    }

    public static List<Field> getAllFields(Class<? extends Object> clazz) {
        List<Field> list = new ArrayList<Field>();
        Field[] fields = clazz.getDeclaredFields();

        if (clazz.getSuperclass() != null) {
            List<Field> superClassFields = getAllFields(clazz.getSuperclass());
            for (Field field : superClassFields) {
                list.add(field);
            }
        }

        for (Field field : fields) {
            list.add(field);
        }

        return list;
    }
}
