package com.solvd.buildingco.utilities;

public final class StringConstants {

    public static final String EMPTY_STRING = "";
    public static final String SINGLE_WHITESPACE_CHAR_STRING = " ";
    public static final String MANUAL_EM_DASH_STRING = "--";
    public static final String SINGLE_QUOTATION = "'";
    public static final String CARRIAGE_RETURN_CHAR_STRING = "\r";
    public static final String NEWLINE_CHAR_STRING = "\n";

    private StringConstants() {
        final String NO_CONSTANTS_INSTANTIATION_MESSAGE =
                "This is a constants class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_CONSTANTS_INSTANTIATION_MESSAGE);
    }
}
