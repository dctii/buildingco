package com.solvd.buildingco.utilities;

public final class StringConstants {

    public static final String EMPTY_STRING = "";
    public static final String SINGLE_WHITESPACE = " ";
    public static final String EM_DASH = "--";
    public static final String SINGLE_QUOTATION = "'";
    public static final String CARRIAGE_RETURN = "\r";
    public static final String NEWLINE = "\n";
    public static final String PADDED_EQUALS_OPERATOR = " = ";
    public static final String EQUALS_OPERATOR = "=";
    public static final String COMMA_DELIMITER = ", ";
    public static final String COLON_DELIMITER = ": ";


    private StringConstants() {
        final String NO_CONSTANTS_INSTANTIATION_MESSAGE =
                "This is a constants class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_CONSTANTS_INSTANTIATION_MESSAGE);
    }
}
