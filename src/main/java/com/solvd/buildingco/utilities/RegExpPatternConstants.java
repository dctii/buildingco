package com.solvd.buildingco.utilities;

public class RegExpConstants {
    final String POSSESSIVE_FORM_PATTERN = ".*'s$";
    final String PREFIX_SUFFIX_PATTERN = "\\b(-\\w+|\\w+-)\\b";
    final String SINGLE_QUOTE_PATTERN = "^'|'$";
    final String ROMAN_NUMERAL_IN_PARENTHESIS_PATTERN =
            "\\(\\s*(?i)(m{0,4}(cm|cd|d?c{0,3})(xc|xl|l?x{0,3})(ix|iv|v?i{0,3}))\\s*\\)";
    private RegExpConstants() {}
}
