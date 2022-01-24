package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Calculator {

    private Input input;

    public Calculator (Input input) {
        this.input = input;
    }

    Answer performPenAndPencil () {
        String multiplicand = input.getMultiplicand();
        String multiplier = input.getMultiplier();

        // initialize string ArrayList for intermediates
        ArrayList<String> intermediates = new ArrayList<>();
        for (int i = 0; i < multiplier.length(); i++) {
            intermediates.add("");
        }

        // get intermediates, multiplying per each bit of the multiplier to the multiplicand
        // starting from the right
        int factor = 0;
        for (int i = multiplier.length() - 1; i >= 0; i--) {
            String intermediate = intermediates.get(multiplier.length() - (i + 1));
            // 1) shift to left based on current intermediate's position (factor)
            for (int j = 0; j < factor; j++) {
                // add trailing spaces
                intermediate += " ";
            }
            // 2) multiply:
            // if current bit is 1, copy multiplicand to current intermediate
            // else, add zeroes according to number of bits (either multiplicand or multiplier)
            if (multiplier.charAt(i) == '1') {
                intermediate = multiplicand + intermediate;
            } else {
                intermediate = addLeadingZeroes(intermediate, multiplier.length());
            }
            // 3) sign-extend
            char signBit = intermediate.charAt(0);
            for (int j = 1; j <= i + 1; j++) {
                intermediate = signBit + intermediate;
            }
            // update current intermediate in ArrayList
            intermediates.set(multiplier.length() - (i + 1), intermediate);
            // increment factor for next intermediate position
            factor++;
        }

        // check if multiplier is negative, add additional 2's complement multiplicand intermediate
        // to ArrayList as needed to calculate correct result
        if (multiplier.charAt(0) == '1') {
            String twosComplement = convertToTwosComplement(multiplicand);
            for (int i = 0; i < factor; i++) {
                // add trailing spaces
                twosComplement += " ";
            }
            intermediates.add(twosComplement);
        }

        // calculate sum of intermediates to get final result
        String answer = "";
        int bitCarry = 0;
        for (int i = intermediates.get(0).length() - 1; i >= 0; i--) { // traverse through each bit
            int bitSum = bitCarry;
            for (int j = 0; j < intermediates.size(); j++) { // traverse through each intermediate with current bit position
                if (intermediates.get(j).charAt(i) == '1') {
                    bitSum++;
                }
            }
            if (bitSum > 1) {
                int quotient = bitSum / 2;
                int remainder = bitSum % 2;
                bitCarry = quotient;
                answer = remainder + answer;
            } else {
                bitCarry = 0;
                answer = bitSum + answer;
            }
        }

        return new Answer(multiplicand, multiplier, intermediates, answer);
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
        return new Answer (temp, temp, new ArrayList<String>(), temp);
    }
}
