package com.solvd.buildingco.utilities;

public class RegExpPatternConstants {
    /*
        https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
        Contains reference for regexp constructs

        Matches roman numerals `i` to `x`, 1 to 10, enclosed in parentheses. For example, "(iii)", "
        (iv)", "
        (X)", etc.

        `\s*` - match any whitespace char
        `(?i)` - case-insensitivity to deal with "III" and "iii" for roman numeral 3 for example
        `v?i{0,3}`
            - `v`'s appearance is optional
            - `i` can occur 0 or up to 3 times
            - if `v` does not appear, then `i` accounts for `i`, `ii`, `iii`
            - if `i` does not appear, but `v` does, then it accounts for `v`
            - if `i` and `v` appear, then it accounts for `vi`, `vii`, and `viii`

     */
    public static final String ROMAN_NUMERAL_IN_PARENTHESES_PATTERN =
            "\\(\\s*(?i)(ix|iv|v?i{0,3}|x)\\s*\\)";

    /*
        Matches digits enclosed in parentheses. For example, "(1)", "(2)", "(100)" etc.
     */
    public static final String NUMBERS_IN_PARENTHESES_PATTERN = "\\(\\d+\\)";

    private RegExpPatternConstants() {
    }
}
