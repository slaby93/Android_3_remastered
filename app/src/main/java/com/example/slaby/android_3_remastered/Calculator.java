package com.example.slaby.android_3_remastered;

import java.math.BigDecimal;

class Calculator {

    BigDecimal number0;
    BigDecimal number1;
    String operation;

    Calculator() {
        number0 = null;
        number1 = null;
        operation = null;
    }

    void insertNumber(String number) {
        BigDecimal result;
        if (number0 == null || operation == null) {
            result = insertNumber(number0, number);
            if (result.compareTo(new BigDecimal(Integer.MAX_VALUE)) > 0) {
                return;
            }
            number0 = result;
            return;
        }
        if (number0 != null && operation != null) {
            result = insertNumber(number1, number);
            if (result.compareTo(new BigDecimal(Integer.MAX_VALUE)) > 0) {
                return;
            }
            number1 = result;
            return;
        }

    }

    public void changeSign() {
        if (number0 != null && operation == null) {
            number0 = number0.negate();
        } else {
            number1 = number1.negate();
        }
    }

    void setOperation(String operation) {
        this.operation = operation;
    }

    private BigDecimal insertNumber(BigDecimal where, String number) {
        System.out.println(where + " " + number);
        if (where == null) {
            return new BigDecimal(number);

        }
        return new BigDecimal(where.toString() + number);
    }

    public Number computeOutput() {
        BigDecimal result = null;
        switch (operation) {
            case "+":
                result = number0.add(number1);
                break;
            case "-":
                result = number0.subtract(number1);
                break;
            case "*":
                result = number0.multiply(number1);
                break;
            case "/":
                result = number0.setScale(25).divide(number1, BigDecimal.ROUND_HALF_EVEN);
                break;
        }
        if (result.compareTo(new BigDecimal(Integer.MAX_VALUE)) > 0) {
            throw new ArithmeticException("Result is too big!");
        }
        Number trueResult = result.doubleValue();
        System.out.println("RESULT " + result);
        if (result.doubleValue() % 1 == 0) {
            trueResult = result.intValue();
        }
        return trueResult;
    }

    public void backSpace() {
        if (number1 != null) {
            number1 = backspaceNumber(number1);
        } else if (number0 != null && number1 == null && operation != null) {
            operation = null;
        } else if (number0 != null && operation == null) {
            number0 = backspaceNumber(number0);
        }
    }

    /**
     * -3 - -5 // backspace
     *
     * @param number
     * @return
     */
    private BigDecimal backspaceNumber(BigDecimal number) {
        String newNumber = number.toString().substring(0, number.toString().length() - 1);
        System.out.println(newNumber);
        if (newNumber.length() > 0 && !newNumber.equals("-")) {
            return new BigDecimal(newNumber);
        }
        return null;
    }

}
