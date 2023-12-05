package com.solvd.buildingco.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.function.BinaryOperator;

public class BigDecimalUtils {

    private static final MathContext mathContext = new MathContext(10);

    public static final BinaryOperator<BigDecimal> ADD_OPERATION =
            (currentSum, currentValue) -> currentSum.add(currentValue);
    public static final BinaryOperator<BigDecimal> SUBTRACT_OPERATION =
            (currentDifference, currentValue) -> currentDifference.subtract(currentValue);
    public static final BinaryOperator<BigDecimal> MULTIPLY_OPERATION =
            (currentProduct, currentValue) -> currentProduct.multiply(currentValue);
    public static final BinaryOperator<BigDecimal> DIVIDE_OPERATION =
            (currentQuotient, currentValue) -> currentQuotient.divide(currentValue, mathContext);


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
            final String ILLEGAL_ARGUMENT_MESSAGE = "Number type not supported";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
    }

    public static int roundToInt(Number number) {
        return NumberUtils.roundToInt(number);
    }

    public static BigDecimal divide(Number... values) {
        return arithmetize(values, StringConstants.DIVIDE_STRING);
    }

    public static BigDecimal divideAll(Number... values) {
        return arithmetizeAll(values, StringConstants.DIVIDE_STRING);
    }


    public static BigDecimal multiply(Number... values) {
        return arithmetize(values, StringConstants.MULTIPLY_STRING);
    }

    public static BigDecimal multiplyAll(Number... values) {
        return arithmetizeAll(values, StringConstants.MULTIPLY_STRING);
    }


    public static BigDecimal add(Number... values) {
        return arithmetize(values, StringConstants.ADD_STRING);
    }

    public static BigDecimal addAll(Number... values) {
        return arithmetizeAll(values, StringConstants.ADD_STRING);
    }


    public static BigDecimal subtract(Number... values) {
        return arithmetize(values, StringConstants.SUBTRACT_STRING);
    }

    public static BigDecimal subtractAll(Number... values) {
        return arithmetizeAll(values, StringConstants.SUBTRACT_STRING);
    }

    public static BigDecimal arithmetize(Number[] values, String operationType) {
        if (values.length != 2) {
            final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE =
                    "Exactly two values required.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        BigDecimal leftOperand = BigDecimal.valueOf(values[0].doubleValue());
        BigDecimal rightOperand = BigDecimal.valueOf(values[1].doubleValue());


        switch (operationType.toLowerCase()) {
            case StringConstants.ADD_STRING:
                return leftOperand.add(rightOperand);
            case StringConstants.SUBTRACT_STRING:
                return leftOperand.subtract(rightOperand);
            case StringConstants.MULTIPLY_STRING:
                return leftOperand.multiply(rightOperand);
            case StringConstants.DIVIDE_STRING:
                return leftOperand.divide(rightOperand, mathContext);
            default:
                final String ILLEGAL_ARGUMENT_MESSAGE = "Invalid operation type.";
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }
    }

    public static BigDecimal arithmetizeAll(Number[] values, String operationType) {
        if (values == null || values.length < 2) {
            final String ILLEGAL_ARGUMENT_MESSAGE = "Invalid operation type.";
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        } else if (values.length == 2) {
            return arithmetize(values, operationType);
        }

        BinaryOperator<BigDecimal> arithmeticOperation;
        BigDecimal initialValue;

        switch (operationType) {
            case StringConstants.ADD_STRING:
                arithmeticOperation = ADD_OPERATION;
                initialValue = BigDecimal.ZERO;
                break;
            case StringConstants.SUBTRACT_STRING:
                arithmeticOperation = SUBTRACT_OPERATION;
                initialValue = BigDecimal.valueOf(values[0].doubleValue());
                break;
            case StringConstants.MULTIPLY_STRING:
                arithmeticOperation = MULTIPLY_OPERATION;
                initialValue = BigDecimal.ONE;
                break;
            case StringConstants.DIVIDE_STRING:
                arithmeticOperation = DIVIDE_OPERATION;
                initialValue = BigDecimal.valueOf(values[0].doubleValue());
                break;
            default:
                final String ILLEGAL_ARGUMENT_MESSAGE = "Invalid operation type.";
                throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }

        return Arrays.stream(values)
                .skip(isCommutative(operationType) ? 0 : 1)
                .map(value -> BigDecimal.valueOf(value.doubleValue()))
                .reduce(initialValue, arithmeticOperation);
    }

    private static boolean isCommutative(String operationType) {
        return Arrays.stream(StringConstants.COMMUTATIVE_OPERATIONS)
                .anyMatch(operation -> operation.equalsIgnoreCase(operationType));
    }


    private BigDecimalUtils() {
        final String NO_UTILITY_CLASS_INSTANTIATION_MESSAGE =
                "This is a utility class and instances cannot be made of it.";

        throw new UnsupportedOperationException(NO_UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }
}
