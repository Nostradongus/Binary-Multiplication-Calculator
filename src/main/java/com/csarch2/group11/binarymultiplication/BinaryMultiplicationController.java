package com.csarch2.group11.binarymultiplication;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class BinaryMultiplicationController {

    Answer answer;

    @GetMapping(value="/calculator")
    public String getCalculator () {
        return "This is the calculator page";
    }

    @PostMapping(value="/calculator")
    public void performCalculation (HttpServletResponse httpServletResponse, @RequestBody Input input) {
        // Reset answer
        this.answer = null;


        // Create Calculator instance
        Calculator calculator = new Calculator (input);

        // If input is in DECIMAL, then convert to BINARY
        if (input.getInputType().equalsIgnoreCase(Input.DECIMAL)) {
            calculator.decimalToBinary ();
        }

        // Proceed to solving based on method chosen
        if (input.getMethod() == Input.PEN_AND_PAPER) {
            this.answer = calculator.performPenAndPaper();
        } else if (input.getMethod() == Input.BOOTHS) {
            this.answer = calculator.performBooths();
        } else if (input.getMethod() == Input.EXTENDED_BOOTHS) {
            this.answer = calculator.performExtendedBooths();
        }

        // Redirect to results page
        try {
            httpServletResponse.sendRedirect("/calculator-results");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value="/calculator-results")
    public Answer showResults (HttpServletResponse httpServletResponse) {
        // If no answer, redirect back to calculator page
        if (this.answer == null) {
            try {
                httpServletResponse.sendRedirect("/calculator");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        // else, return answer
        return this.answer;
    }
}
