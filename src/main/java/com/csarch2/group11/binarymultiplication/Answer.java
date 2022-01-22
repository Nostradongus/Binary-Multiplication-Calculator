package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;

@Entity
public class Answer {
    private StringBuilder solution;
    private StringBuilder answer;

    public Answer (StringBuilder solution, StringBuilder answer) {
        this.solution = solution;
        this.answer = answer;
    }

    StringBuilder getSolution () {
        return this.solution;
    }

    StringBuilder getAnswer () {
        return this.answer;
    }
}
