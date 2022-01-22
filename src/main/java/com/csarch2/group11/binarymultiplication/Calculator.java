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

    void convertToBinary () {
        // TODO: implement DECIMAL to BINARY conversion
    }

    // TODO: remove this
    private void createTemporaryAnswer () {
        StringBuilder temp = new StringBuilder();
        temp.append ("101");
        this.answer = new Answer (temp, temp);
    }
}
