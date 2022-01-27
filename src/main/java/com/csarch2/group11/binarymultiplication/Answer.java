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

    public Answer (String multiplicand, String multiplier, String boothsEquivalent, ArrayList<String> intermediates, String answer) {
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
        this.boothsEquivalent = boothsEquivalent;
        this.intermediates = intermediates;
        this.answer = answer;
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
}
