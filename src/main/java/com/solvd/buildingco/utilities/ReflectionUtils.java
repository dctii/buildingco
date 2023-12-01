package com.solvd.buildingco.utilities;

import com.solvd.buildingco.exception.UnableToCreateWithReflectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionUtils {
    private static final Logger LOGGER = LogManager.getLogger(ReflectionUtils.class);

    public static <T> T createObject(Class<T> targetClass) {
        try {

            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            return constructor.newInstance();

        } catch (ReflectiveOperationException e) {
            final String CREATE_WITH_REFLECTION_EXCEPTION_MESSAGE_PREFIX =
                    "Unable to create a new Object instantiation via reflect: ";

            LOGGER.warn(CREATE_WITH_REFLECTION_EXCEPTION_MESSAGE_PREFIX + e);
            throw new UnableToCreateWithReflectionException(CREATE_WITH_REFLECTION_EXCEPTION_MESSAGE_PREFIX + e);
        }
    }

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

    private ReflectionUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
