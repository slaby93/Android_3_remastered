package com.example.slaby.android_3_remastered;

import java.math.BigDecimal;

class Calculator {

    BigDecimal number0;
    Boolean doesNumber0HasComa;
    Boolean can0HasMoreComas;
    Boolean doesNumber1HasComa;
    Boolean can1HasMoreComas;
    BigDecimal number1;

    String operation;

    Calculator() {
        number0 = null;
        number1 = null;
        operation = null;
        doesNumber0HasComa = false;
        can0HasMoreComas = true;
        doesNumber1HasComa = false;
        can1HasMoreComas = true;
    }

    void insertNumber(String number) {
        BigDecimal result;
        if (number0 == null || operation == null) {

            BigDecimal resultTMP = insertNumber(number0, number);

            if (resultTMP.compareTo(new BigDecimal(Integer.MAX_VALUE)) > 0) {
                return;
            }

            if (doesNumber0HasComa && can0HasMoreComas) {
                doesNumber0HasComa = false;
                can0HasMoreComas = false;
                number0 = insertNumber(number0, "." + number);
                return;
            }

            number0 = insertNumber(number0, number);

            return;
        }
        if (number0 != null && operation != null) {
            BigDecimal resultTMP = insertNumber(number1, number);

            if (resultTMP.compareTo(new BigDecimal(Integer.MAX_VALUE)) > 0) {
                return;
            }
            if (doesNumber1HasComa && can1HasMoreComas) {
                doesNumber1HasComa = false;
                can1HasMoreComas = false;
                number1 = insertNumber(number1, "." + number);
                return;
            }

            number1 = insertNumber(number1, number);
            return;
        }

    }

    public void addComa() {
        if (number0 != null && operation == null) {
            doesNumber0HasComa = true;
        }
        if (number0 != null && operation != null) {
            doesNumber1HasComa = true;
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
