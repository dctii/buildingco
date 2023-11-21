package com.solvd.buildingco.utilities;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalUtils {

    private static final MathContext mathContext = new MathContext(10);


    public static Number sqrt(Number value) {
    /*
        "BigDecimal sqrt() Method in Java with Examples"
        https://www.geeksforgeeks.org/bigdecimal-sqrt-method-in-java-with-examples/
    */
        BigDecimal sqrtValue =
                new BigDecimal(value.toString())
                        .sqrt(mathContext);

        if (value instanceof BigDecimal) {
            return sqrtValue;
        } else if (value instanceof Double) {
            return sqrtValue.floatValue();
        } else if (value instanceof Integer) {
            return sqrtValue.intValue();
        } else if (value instanceof Long) {
            return sqrtValue.longValue();
        } else {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "Number type not supported";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
    }

    public static int roundToInt(Number number) {
        return NumberUtils.roundToInt(number);
    }
    public static BigDecimal divide(Number... values) {
        if (values.length != 2) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "divide() requires exactly two values. Use divideAll for more than two values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        return BigDecimal
                .valueOf(values[0].doubleValue())
                .divide(BigDecimal.valueOf(values[1].doubleValue()), mathContext);
    }

    public static BigDecimal divideAll(Number... values) {
        if (values.length < 3) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "divideAll() requires at least three values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        boolean isFirst = true;
        BigDecimal result = null;

        for (Number value : values) {
            if (!isFirst) {
                result =
                        result.divide(
                                BigDecimal.valueOf(value.doubleValue()),
                                mathContext
                        );
            } else {
                result = BigDecimal.valueOf(value.doubleValue());
                isFirst = false;
            }
        }

        return result;
    }

    public static BigDecimal multiply(Number... values) {
        if (values.length != 2) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "multiply requires exactly two values. Use multiplyAll for more than two values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        return BigDecimal
                .valueOf(values[0].doubleValue())
                .multiply(BigDecimal.valueOf(values[1].doubleValue()));
    }

    public static BigDecimal multiplyAll(Number... values) {
        if (values.length < 3) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "multiplyAll() requires at least three values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        BigDecimal result = BigDecimal.ONE;

        for (Number value : values) {
            result =
                    result.multiply(
                            BigDecimal.valueOf(value.doubleValue())
                    );
        }

        return result;
    }

    public static BigDecimal add(Number... values) {
        if (values.length != 2) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "add() requires exactly two values. Use addAll() for more than two values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        return BigDecimal
                .valueOf(values[0].doubleValue())
                .add(BigDecimal.valueOf(values[1].doubleValue()));
    }

    public static BigDecimal addAll(Number... values) {
        if (values.length < 3) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "addAll() requires at least three values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        BigDecimal result = BigDecimal.ONE;

        for (Number value : values) {
            result =
                    result.add(
                            BigDecimal.valueOf(value.doubleValue())
                    );
        }

        return result;
    }

    public static BigDecimal subtract(Number... values) {
        if (values.length != 2) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "subtract requires exactly two values. Use subtractAll() for more than two values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }
        return BigDecimal
                .valueOf(values[0].doubleValue())
                .subtract(BigDecimal.valueOf(values[1].doubleValue()));
    }

    public static BigDecimal subtractAll(Number... values) {
        if (values.length < 3) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "subtractAll requires at least three values.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        BigDecimal result = BigDecimal.ONE;

        for (Number value : values) {
            result =
                    result.subtract(
                            BigDecimal.valueOf(value.doubleValue())
                    );
        }

        return result;
    }
}
