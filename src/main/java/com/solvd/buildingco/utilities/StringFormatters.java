package com.solvd.buildingco.utilities;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringFormatters {

    public static String removeEdges(String string) {
        int stringLength = string.length();
        int firstCharPosition = 1;
        int lastCharPosition = stringLength - 1;

        if (BooleanUtils.isNotBlankOrEmptyString(string) && stringLength > 1) {

            // returns the string without its first and last character
            return StringUtils
                    .substring(
                            string,
                            firstCharPosition,
                            lastCharPosition
                    );
        }
        return string;
    }

    public static String nestInSingleQuotations(String string) {
        return StringConstants.SINGLE_QUOTATION + string + StringConstants.SINGLE_QUOTATION;
    }

    public static String nestInCurlyBraces(String string) {
        return StringConstants.OPENING_CURLY_BRACE + string + StringConstants.CLOSING_CURLY_BRACE;
    }

    public static String stateEquivalence(Object leftOperand, Object rightOperand) {
        String leftOperandString = leftOperand.toString();
        String rightOperandString = rightOperand.toString();

        return leftOperandString + StringConstants.EQUALS_OPERATOR + rightOperandString;
    }

    public static String stateEquivalence(Object leftOperand, Object[] rightOperand) {
        String leftOperandString = leftOperand.toString();
        String rightOperandString = Arrays.toString(rightOperand);

        return leftOperandString + StringConstants.EQUALS_OPERATOR + rightOperandString;
    }


    public static String listToString(List<?> list) {
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));
    }

    public static String mapToString(Map<?, List<?>> targetMap) {
        return targetMap.entrySet().stream()
                .map(entry -> entry.getKey()
                        + StringConstants.COLON_DELIMITER
                        + listToString(entry.getValue()))
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));
    }

    public static String buildFieldsString(Object object, String[] fieldNames) {
        return Arrays.stream(fieldNames)
                .map(fieldName -> {
                    Object fieldValue = ReflectionUtils.getField(object, fieldName);

                    if (fieldValue == null) {
                        return stateEquivalence(
                                fieldName,
                                StringConstants.NULL_STRING
                        );
                    } else {
                        if (fieldValue.getClass().isArray()) {
                            return stateEquivalence(
                                    fieldName,
                                    (Object[]) fieldValue
                            );
                        } else if (fieldValue instanceof Map) {
                            return stateEquivalence(
                                    fieldName,
                                    mapToString((Map<?, List<?>>) fieldValue)
                            );
                        } else {
                            return stateEquivalence(
                                    fieldName,
                                    fieldValue
                            );
                        }
                    }
                })
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));

    }

    public static String buildToString(Class<?> currClass, String[] fieldNames,
                                       String parentToString,
                                       String fieldsString) {
        String className = currClass.getSimpleName();

        // removes the parent classname as header and removes curly braces wrapping body
        parentToString = StringFormatters.cleanParentToString(currClass, parentToString);

        if (fieldNames.length != 0) {
            // check if parentToString is empty after cleaning, if so, do not use delimiter
            String finalToStringBody;
            if (BooleanUtils.isBlankOrEmptyString(parentToString)) {
                finalToStringBody = fieldsString;
            } else {
                finalToStringBody = parentToString
                        + StringConstants.COMMA_DELIMITER
                        + fieldsString;
            }

            return className + StringFormatters.nestInCurlyBraces(finalToStringBody);

        } else {

            if (BooleanUtils.isBlankOrEmptyString(parentToString)) {
                return buildToString(currClass);
            } else {
                return buildToString(currClass, parentToString);
            }

        }
    }

    public static String buildToString(Class<?> currClass, String[] fieldNames,
                                       String fieldsString) {
        String className = currClass.getSimpleName();

        if (BooleanUtils.isEmptyOrNullArray(fieldNames)) {
            fieldsString = StringConstants.EMPTY_STRING;
        }
        return className + StringFormatters.nestInCurlyBraces(fieldsString);
    }

    public static String buildToString(Class<?> currClass, String parentToString) {
        String className = currClass.getSimpleName();
        parentToString = StringFormatters.cleanParentToString(currClass, parentToString);

        return className
                + StringFormatters.nestInCurlyBraces(parentToString);
    }

    public static String buildToString(Class<?> currClass) {
        String className = currClass.getSimpleName();

        return className
                + StringFormatters.nestInCurlyBraces(StringConstants.EMPTY_STRING);
    }


    public static String cleanParentToString(Class<?> currClass, String parentToString) {
        String parentName = currClass.getSuperclass().getSimpleName();

        parentToString = StringUtils.replace(
                parentToString,
                parentName,
                StringConstants.EMPTY_STRING
        );

        parentToString = removeEdges(parentToString);

        return parentToString;
    }

    public static String toReadableDateString(LocalDate datetime) {
        return datetime.getMonth()
                + StringConstants.DASH_STRING + datetime.getDayOfMonth()
                + StringConstants.DASH_STRING + datetime.getYear();
    }

    public static String toReadableDateString(ZonedDateTime datetime) {
        return datetime.getMonth()
                + StringConstants.DASH_STRING + datetime.getDayOfMonth()
                + StringConstants.DASH_STRING + datetime.getYear();

    }

    private StringFormatters() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
