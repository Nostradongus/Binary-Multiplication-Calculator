package com.csarch2.group11.binarymultiplication;

import jdk.internal.org.jline.reader.Buffer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@Controller
public class BinaryMultiplicationController {

    Answer answer;
    Input input;
    String methodUsed;

    @PostMapping(value="/calculator")
    public void performCalculation (HttpServletResponse httpServletResponse, @ModelAttribute("input") Input formInput) {
        // Reset answer
        this.answer = null;
        this.input = new Input(formInput.getMultiplicand(), formInput.getMultiplier(), formInput.getMethod(), formInput.getInputType());

        switch (formInput.getMethod()) {
            case 0:
                methodUsed = "Pen and Paper";
                break;
            case 1:
                methodUsed = "Booth's";
                break;
            case 2:
                methodUsed = "Extended Booth's";
                break;
        }

        // Create Calculator instance
        Calculator calculator = new Calculator (formInput);

        // If input is in DECIMAL, then convert to BINARY
        if (formInput.getInputType().equalsIgnoreCase(Input.DECIMAL)) {
            calculator.decimalToBinary ();
        }

        // Proceed to solving based on method chosen
        if (formInput.getMethod() == Input.PEN_AND_PAPER) {
            this.answer = calculator.performPenAndPaper();
        } else if (formInput.getMethod() == Input.BOOTHS) {
            this.answer = calculator.performBooths();
        } else if (formInput.getMethod() == Input.EXTENDED_BOOTHS) {
            this.answer = calculator.performExtendedBooths();
        }

        // Redirect to results page
        try {
            httpServletResponse.sendRedirect("/calculator-results");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/calculator-results")
    public String showResults (HttpServletResponse httpServletResponse, Model model) {
        model.addAttribute("answer", answer);
        model.addAttribute("input", input);
        model.addAttribute("method", methodUsed);

        // If no answer, redirect back to calculator page
        if (this.answer == null) {
            try {
                httpServletResponse.sendRedirect("/calculator");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        return "calculator-results";
    }

    @RequestMapping(value="/calculator-results-step-by-step")
    public String showStepByStep (HttpServletResponse httpServletResponse, Model model) {
        model.addAttribute("answer", answer);
        model.addAttribute("input", input);
        model.addAttribute("method", methodUsed);

        // If no answer, redirect back to calculator page
        if (this.answer == null) {
            try {
                httpServletResponse.sendRedirect("/calculator");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        return "calculator-step-by-step";
    }

    public Answer showResults () {
        return this.answer;
    }
}
