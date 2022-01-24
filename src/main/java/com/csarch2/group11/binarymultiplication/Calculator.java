package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;

@Entity
public class Calculator {

    private StringBuilder solution;
    private StringBuilder answer;
    private Input input;

    public Calculator (Input input) {
        this.input = input;
    }

    Answer performPenAndPencil () {


        // TODO: remove this
        return createTemporaryAnswer();
        // proposed: return new Answer(solution.toString(), answer.toString());
    }

    Answer performBooths () {
        // TODO: remove this
        return createTemporaryAnswer();
    }

    Answer performExtendedBooths () {
        // TODO: remove this
        return createTemporaryAnswer();
    }

    void decimalToBinary () {
        // get decimals from input
        String decMultiplicand = input.getMultiplicand();
        String decMultiplier = input.getMultiplier();
        // convert to integers
        int nMultiplicand = convertDecimalStringToInteger(decMultiplicand);
        int nMultiplier = convertDecimalStringToInteger(decMultiplier);
        // convert decimals to unsigned binary forms first
        String binMultiplicand = convertIntegerToBinaryString(nMultiplicand);
        String binMultiplier = convertIntegerToBinaryString(nMultiplier);
        // check if number of bits for both binary strings are not the same
        // add leading zeroes to binary string with the lowest number of bits
        // based on the number of bits the longest binary string has
        if (binMultiplicand.length() > binMultiplier.length()) {
            binMultiplier = addLeadingZeroes(binMultiplier, binMultiplicand.length() - binMultiplier.length());
        } else if (binMultiplicand.length() < binMultiplier.length()) {
            binMultiplicand = addLeadingZeroes(binMultiplicand, binMultiplier.length() - binMultiplicand.length());
        }
        // check for negative inputs, convert to 2's complement as needed
        if (decMultiplicand.charAt(0) == '-') {
            binMultiplicand = convertToTwosComplement(binMultiplicand);
        }
        if (decMultiplier.charAt(0) == '-') {
            binMultiplier = convertToTwosComplement(binMultiplier);
        }
        // update input with converted results
        input.setMultiplicand(binMultiplicand);
        input.setMultiplier(binMultiplier);
    }

    private int convertDecimalStringToInteger(String decimal) {
        // remove sign if there is before converting
        return decimal.charAt(0) == '+' ? Integer.parseInt(decimal.substring(1))
                : decimal.charAt(0) == '-' ? Integer.parseInt(decimal.substring(1))
                : Integer.parseInt(decimal);
    }

    private String convertIntegerToBinaryString(int decimal) {
        String binaryString = "";
        while (decimal > 0) {
            int remainder = decimal % 2;
            binaryString = remainder + binaryString;
            decimal /= 2;
        }
        // add sign bit
        return 0 + binaryString;
    }

    private String addLeadingZeroes(String binary, int numOfZeroes) {
        for (int i = 1; i <= numOfZeroes; i++) {
            binary = 0 + binary;
        }
        return binary;
    }

    private String convertToTwosComplement(String binary) {
        String twosComplement = "";
        boolean flip = false;
        for (int i = binary.length() - 1; i >= 0; i--) {
            if (flip) {
                twosComplement = (binary.charAt(i) == '0' ? '1' : '0') + twosComplement;
            } else {
                if (binary.charAt(i) == '1') {
                    flip = true;
                }
                twosComplement = binary.charAt(i) + twosComplement;
            }
        }
        return twosComplement;
    }

    // TODO: remove this
    private Answer createTemporaryAnswer () {
        String temp = "101";
        return new Answer (temp, temp);
    }
}
