package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;

@Entity
public class Calculator {
    private Answer answer;
    private Input input;

    public Calculator (Input input) {
        this.input = input;
    }

    Answer performPenAndPencil () {
        // TODO: remove this
        createTemporaryAnswer();
        return this.answer;
    }

    Answer performBooths () {
        // TODO: remove this
        createTemporaryAnswer();
        return this.answer;
    }

    Answer performExtendedBooths () {
        // TODO: remove this
        createTemporaryAnswer();
        return this.answer;
    }

    void decimalToBinary () {
        // get decimals from input
        String decMultiplicand = input.getMultiplicand();
        String decMultiplier = input.getMultiplier();
        // convert to integers
        int nMultiplicand = convertDecimalStringToInteger(decMultiplicand);
        int nMultiplier = convertDecimalStringToInteger(decMultiplier);
        // convert decimals to unsigned binary forms first
        String binMultiplicand = convertToBinaryString(nMultiplicand);
        String binMultiplier = convertToBinaryString(nMultiplier);
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

    private String convertToBinaryString(int decimal) {
        String binaryString = "";
        while (decimal > 0) {
            int remainder = decimal % 2;
            binaryString = remainder + binaryString;
            decimal /= 2;
        }
        // add sign bit
        return 0 + binaryString;
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
    private void createTemporaryAnswer () {
        StringBuilder temp = new StringBuilder();
        temp.append ("101");
        this.answer = new Answer (temp, temp);
    }
}
