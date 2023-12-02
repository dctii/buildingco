package com.solvd.buildingco.utilities;

public final class RegExpPatternConstants {
    /*
        https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
        Contains reference for regexp constructs

        Matches roman numerals `i` to `x`, 1 to 10, enclosed in parentheses. For example, "(iii)",
        "(iv)", "(X)", etc.

        `X*` - zero or more times
        `X{n, m} - at least n, but not more than m times
        `X|Y` - either X or Y
        `\s` - any whitespace char
        `(?i)` - case-insensitivity to deal with "III" and "iii" for roman numeral 3 for example
        `v?i{0,3}`
            - `v`'s appearance is optional
            - `i` can occur 0 or up to 3 times
            - if `v` does not appear, then `i` accounts for `i`, `ii`, `iii`
            - if `i` does not appear, but `v` does, then it accounts for `v`
            - if `i` and `v` appear, then it accounts for `vi`, `vii`, and `viii`

     */
    public static final String PARENTHESIZED_ROMAN_NUMERALS =
            "\\(\\s*(?i)(ix|iv|v?i{0,3}|x)\\s*\\)";

    /*
        Matches digits enclosed in parentheses. For example, "(1)", "(2)", "(100)" etc.

        `\d` - digit character [0-9]
        `X+` - postfix to denote one or more times
     */
    public static final String PARENTHESIZED_NUMBERS = "\\(\\d+\\)";

    /*
            Characters to split words by:
                whitespace, tab, and newline
                comma, semicolon, colon
                sentence terminating signs
                double quotes
                parentheses, brackets, braces

            `\t` - tab
            `\n` - newline
     */
    public static final String SEPARATOR_CHARS = "[ ,.!?;:\"()\\[\\]{}<>\\t\\n]+";

    private RegExpPatternConstants() {
        final String NO_CONSTANTS_INSTANTIATION_MESSAGE =
                "This is a constants class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_CONSTANTS_INSTANTIATION_MESSAGE);
    }
}
