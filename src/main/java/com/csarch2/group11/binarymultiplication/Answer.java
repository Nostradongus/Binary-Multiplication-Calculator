package com.csarch2.group11.binarymultiplication;

import javax.persistence.Entity;

@Entity
public class Answer {
    String solution;
    String answer;

    public Answer (String solution, String answer) {
        this.solution = solution;
        this.answer = answer;
    }

    public String getSolution() {
        return this.solution;
    }

    public String getAnswer() {
        return this.answer;
    }
}
