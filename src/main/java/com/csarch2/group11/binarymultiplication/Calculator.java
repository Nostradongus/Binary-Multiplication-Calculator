package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Calculator {

    private Input input;

    public Calculator (Input input) {
        this.input = input;

        // if input type is binary, check if number of bits for both multiplicand and multiplier are not equal
        // equalize by sign-extending the binary input with the lowest number of bits
        String multiplicand = this.input.getMultiplicand();
        String multiplier = this.input.getMultiplier();
        if (input.getInputType().equalsIgnoreCase(Input.BINARY) && multiplicand.length() != multiplier.length()) {
            if (multiplicand.length() > multiplier.length()) {
                input.setMultiplier(extendBinary(multiplier, multiplicand.length() - multiplier.length(), multiplier.charAt (0)));
            } else if (multiplicand.length() < multiplier.length()) {
                input.setMultiplicand(extendBinary(multiplicand, multiplier.length() - multiplicand.length(), multiplicand.charAt (0)));
            }
        }
    }

    Answer performPenAndPaper() {
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
                intermediate = extendBinary(intermediate, multiplier.length(), '0');
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
        String multiplicand = input.getMultiplicand ();
        String multiplier = getBoothsEquivalent (input.getMultiplier ());
        int multiplierLength = multiplier.length ();

        // initialize string ArrayList for intermediates
        ArrayList<String> intermediates = new ArrayList<>();
        for (int i = 0; i < multiplierLength / 2; i++) {
            intermediates.add("");
        }

        int factor = 0;
        // (1) Multiply each bit of the multiplier to the multiplicand (Right to Left)
        for (int i = multiplierLength - 1; i > 0; i-=2) {
            // Get current intermediate
            String intermediate = intermediates.get((multiplierLength - (i + 1)) / 2);

            // (2) Shift immediate to left based on current intermediate's position (factor)
            for (int j = 0; j < factor; j++) {
                intermediate += " ";
            }

            // (3) Multiply
            // If multiplier is '1'
            if (multiplier.charAt (i) == '1') {
                // if '+'
                if (multiplier.charAt (i - 1) == '+') {
                    intermediate = multiplicand + intermediate;
                // if '-'
                } else {
                    intermediate = convertToTwosComplement(multiplicand) + intermediate;
                }
            // if multiplier is '0'
            } else {
                intermediate = extendBinary (intermediate, multiplier.length () / 2, '0');
            }

            // (4) Sign-extension
            char signBit = intermediate.charAt(0);
            for (int j = 1; j <= i + 1; j+=2) {
                intermediate = signBit + intermediate;
            }

            // (5) Update current intermediate in ArrayList
            intermediates.set((multiplierLength - (i + 1)) / 2, intermediate);

            // (6) increment factor every 2nd iteration (due to x2 length of multiplier)
            if (i % 2 == 1)
                factor++;
        }

        // (7) Calculate sum of intermediates to get final result
        String answer = "";
        int bitCarry = 0;
        // traverse through each bit
        for (int i = intermediates.get(0).length() - 1; i >= 0; i--) {
            int bitSum = bitCarry;
            // traverse through each intermediate with current bit position
            for (int j = 0; j < intermediates.size(); j++) {
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

    Answer performExtendedBooths () {
        String multiplicand = input.getMultiplicand ();
        String multiplier = getExtendedBoothsEquivalent (input.getMultiplier ());
        int multiplierLength = multiplier.length ();

        // initialize string ArrayList for intermediates
        ArrayList<String> intermediates = new ArrayList<>();
        for (int i = 0; i < multiplierLength / 2; i++) {
            intermediates.add("");
        }

        int factor = 0;
        // (1) Multiply each bit of the multiplier to the multiplicand (Right to Left)
        for (int i = multiplier.length () - 1; i > 0; i-=2) {
            // Get current intermediate
            String intermediate = intermediates.get ((multiplier.length () - (i + 1)) / 2);

            // (2) Shift immediate to left based on current intermediate's position (factor)
            for (int j = 0; j < factor; j++) {
                intermediate += "  ";
            }

            // (3) Multiply
            if (multiplier.charAt (i) == '1') {
                // if +1
                if (multiplier.charAt (i - 1) == '+') {
                    intermediate = multiplicand + intermediate;
                // if -1
                } else {
                    intermediate = convertToTwosComplement(multiplicand) + intermediate;
                }
            } else if (multiplier.charAt (i) == '2') {
                // if +2
                if (multiplier.charAt (i - 1) == '+') {
                    intermediate = multiplicand + '0' + intermediate;
                // if -2
                } else {
                    intermediate = convertToTwosComplement(multiplicand) + '0' + intermediate;
                }
            // if 0
            } else {
                intermediate = extendBinary (intermediate, multiplier.length(), '0');
            }

            // (4) Sign-extension
            char signBit = intermediate.charAt (0);
            for (int j = 1; j <= i + 1; j++) {
                intermediate = signBit + intermediate;
            }

            // if '2' bit multiplier, perform 'shift left'
            if (multiplier.charAt (i) == '2') {
                intermediate = intermediate.substring (1);
            }

            // (5) Update current intermediate in ArrayList
            intermediates.set((multiplier.length() - (i + 1)) / 2, intermediate);

            // (6) increment factor every 2nd iteration (due to x2 length of multiplier)
            if (i % 2 == 1)
                factor++;
        }

        // (7) Calculate sum of intermediates to get final result
        String answer = "";
        int bitCarry = 0;
        // traverse through each bit
        for (int i = intermediates.get(0).length() - 1; i >= 0; i--) {
            int bitSum = bitCarry;
            // traverse through each intermediate with current bit position
            for (int j = 0; j < intermediates.size(); j++) {
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
            binMultiplier = extendBinary(binMultiplier, binMultiplicand.length() - binMultiplier.length(), '0');
        } else if (binMultiplicand.length() < binMultiplier.length()) {
            binMultiplicand = extendBinary(binMultiplicand, binMultiplier.length() - binMultiplicand.length(), '0');
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

    private String extendBinary(String binary, int numOfBitsToExtend, char bit) {
        for (int i = 1; i <= numOfBitsToExtend; i++) {
            binary = bit + binary;
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

    private String getBoothsEquivalent (String binary) {
        // (1) Append 0 at the end of given multiplier
        String temp = binary + "0";
        StringBuilder boothsEquivalent = new StringBuilder();

        // (2) Generate Booth's equivalent
        for (int i = 0; i < temp.length () - 1; i++) {
            // 0 to 0; 1 to 1
            if (temp.charAt (i) == temp.charAt (i + 1)) {
                boothsEquivalent.insert(boothsEquivalent.length(), " 0");
            // 0 to 1
            } else if (temp.charAt (i) == '0' && temp.charAt (i + 1) == '1') {
                boothsEquivalent.insert(boothsEquivalent.length(), "+1");
            // 1 to 0
            } else if (temp.charAt (i) == '1' && temp.charAt (i + 1) == '0') {
                boothsEquivalent.insert(boothsEquivalent.length(), "-1");
            }
        }

        return boothsEquivalent.toString ();
    }

    private String getExtendedBoothsEquivalent (String binary) {
        // (1) Append 0 at the end of given multiplier
        String temp = binary + "0";

        // (2) If the length of the original multiplier is ODD,
        //     then sign-extend it.
        if (binary.length () % 2 == 1) {
            char signBit = binary.charAt (0);
            temp = signBit + temp;
        }

        StringBuilder extendedBoothsEquivalent = new StringBuilder();

        // (3) Generate Booth's equivalent
        for (int i = 0; i < temp.length () - 2; i+=2) {
            String currTrio = temp.substring (i, i+3);

            // if '000' or '111'
            if (currTrio.equalsIgnoreCase ("000") || currTrio.equalsIgnoreCase ("111")) {
                extendedBoothsEquivalent.insert(extendedBoothsEquivalent.length(), " 0");
            // if '001' or '010'
            } else if (currTrio.equalsIgnoreCase ("001") || currTrio.equalsIgnoreCase ("010")) {
                extendedBoothsEquivalent.insert(extendedBoothsEquivalent.length(), "+1");
            // if '101' or '110'
            } else if (currTrio.equalsIgnoreCase ("101") || currTrio.equalsIgnoreCase ("110")) {
                extendedBoothsEquivalent.insert(extendedBoothsEquivalent.length(), "-1");
            // if '011'
            } else if (currTrio.equalsIgnoreCase ("011")) {
                extendedBoothsEquivalent.insert(extendedBoothsEquivalent.length(), "+2");
            // if '100'
            } else if (currTrio.equalsIgnoreCase ("100")) {
                extendedBoothsEquivalent.insert(extendedBoothsEquivalent.length(), "-2");
            }
        }

        return extendedBoothsEquivalent.toString ();
    }

}
