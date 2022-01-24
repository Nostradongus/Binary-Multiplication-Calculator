package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
public class Answer {
    String multiplicand;
    String multiplier;
    ArrayList<String> intermediates;
    String answer;

    public Answer (String multiplicand, String multiplier, ArrayList<String> intermediates, String answer) {
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
        this.intermediates = intermediates;
        this.answer = answer;
    }

    public String getAnswer () {
        return this.answer;
    }

    public String getMultiplicand() {
        return multiplicand;
    }

    public String getMultiplier() {
        return multiplier;
    }

    public ArrayList<String> getIntermediates() {
        return intermediates;
    }
}
