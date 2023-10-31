package com.solvd.buildingco.utilities;

import java.lang.reflect.Field;

public class FieldUtils {
    public static Object getField(Object obj, String fieldName) {
        Class<?> current = obj.getClass();

        while (current != null) {
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
}
