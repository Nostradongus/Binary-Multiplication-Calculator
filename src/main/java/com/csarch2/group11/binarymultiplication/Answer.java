package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Answer {
    String multiplicand;
    String multiplier;
    ArrayList<String> intermediates;
    String answer;
    String boothsEquivalent;
    String multiplicandTwosComplement;

    public Answer (String multiplicand, String multiplier, String boothsEquivalent, ArrayList<String> intermediates, String answer, String multiplicandTwosComplement) {
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
        this.boothsEquivalent = boothsEquivalent;
        this.intermediates = intermediates;
        this.answer = answer;
        this.multiplicandTwosComplement = multiplicandTwosComplement;
    }

    public String getAnswer () {
        return this.answer;
    }

    public String getMultiplicand() {
        return this.multiplicand;
    }

    public String getBoothsEquivalent() {
        return this.boothsEquivalent;
    }

    public String getMultiplier() {
        return this.multiplier;
    }

    public ArrayList<String> getIntermediates() {
        return this.intermediates;
    }

    public String getMultiplicandTwosComplement() {
        return this.multiplicandTwosComplement;
    }
}
