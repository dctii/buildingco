package com.solvd.buildingco.utilities;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringFormatters {

    public static String removeEdges(String string) {
        int stringLength = string.length();
        int firstCharPosition = 1;
        int lastCharPosition = stringLength - 1;

        if (!BooleanUtils.isBlankOrEmptyString(string) && stringLength > 1) {

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
    public static String nestInCurlyBraces(String string) {
        return "{" + string + "}";
    }

    public static String stateEquivalence(Object leftOperand, Object rightOperand) {
        String leftOperandString = String.valueOf(leftOperand);
        String rightOperandString = String.valueOf(rightOperand);

        return leftOperandString + StringConstants.PADDED_EQUALS_OPERATOR + rightOperandString;
    }

    public static String listToString(List<?> list) {
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));
    }

    public static String mapToString(Map<?, List<?>> targetMap) {
        return targetMap.entrySet().stream()
                .map(entry -> {
                    return entry.getKey()
                            + StringConstants.COLON_DELIMITER
                            + listToString(entry.getValue());
                })
                .collect(Collectors.joining(StringConstants.COMMA_DELIMITER));
    }

    private StringFormatters() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
