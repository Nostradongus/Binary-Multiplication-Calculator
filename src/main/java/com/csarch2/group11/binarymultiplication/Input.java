package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;

@Entity
public class Input {
    private String multiplicand;
    private String multiplier;
    private int method;
    private String inputType;

    public static final String DECIMAL = "DECIMAL";
    public static final String BINARY = "BINARY";

    public static final int PEN_AND_PAPER = 0;
    public static final int BOOTHS = 1;
    public static final int EXTENDED_BOOTHS = 2;

    public Input(String multiplicand, String multiplier, int method, String inputType) {
        super ();
        this.multiplicand = multiplicand;
        this.multiplier = multiplier;
        this.method = method;
        this.inputType = inputType;
    }

    public Input() {
        super();
    }

    public String getMultiplicand() {
        return this.multiplicand;
    }

    public void setMultiplicand(String multiplicand) {
        this.multiplicand = multiplicand;
    }

    public String getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(String multiplier) {
        this.multiplier = multiplier;
    }

    public int getMethod() {
        return this.method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getInputType() {
        return this.inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}
