package com.solvd.buildingco.utilities;

import java.lang.reflect.Field;

public class FieldUtils {
    public static Object getField(Object obj, String fieldName) {
        // get various Object's particular class
        Class<?> current = obj.getClass();

        // iterate until fieldName is found in the object
        while (current != null) {
            try {
                // get field from class
                Field field = obj.getClass().getDeclaredField(fieldName);
                // override private, protected, etc
                field.setAccessible(true);
                // return the value of the field
                return field.get(obj);

                // Either no field or not accessible
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // if nothing found, then move to super class
                current = current.getSuperclass();
            }
        }
        // if nothing found, return null
        return null;
    }

    private FieldUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
