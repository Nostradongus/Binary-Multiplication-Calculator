package com.csarch2.group11.binarymultiplication;

import com.csarch2.group11.binarymultiplication.service.FileExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class BinaryMultiplicationController {
    @Autowired
    private FileExporter fileExporter;

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

    @RequestMapping("/download-answer")
    public void downloadAnswerTextFile(HttpServletResponse response) throws IOException {
        String fileName = "solution.txt";
        StringBuilder fileContent = generateTextFileContent();

        // Create text file
        Path exportedPath = fileExporter.export(fileContent.toString (), fileName);

        // Download file with HttpServletResponse
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
        response.setContentLength((int)exportedPath.toFile().length());

        // Copy file content to response output stream
        Files.copy(exportedPath, response.getOutputStream());
        response.getOutputStream().flush();
    }

    private StringBuilder generateTextFileContent () {
        StringBuilder fileContent = new StringBuilder();

        // Append Given
        fileContent.append ("Given: \n");
        fileContent.append ("Multiplicand: " + answer.getMultiplicand() + "\n");
        fileContent.append ("Multiplier: " + answer.getMultiplier() + "\n\n");

        // if DECIMAL input
        if (input.getInputType().equalsIgnoreCase("DECIMAL")) {
            // Append Conversion of Decimal to Binary
            fileContent.append ("Convert the Decimal operands to Binary:\n");
            fileContent.append (input.getMultiplicand() + " --> " + answer.getMultiplicand() + "\n");
            fileContent.append (input.getMultiplier() + " --> " + answer.getMultiplier() + "\n\n");
        }

        // if NOT Pen-And-Paper
        if (input.getMethod() != 0) {
            // Append Booth's Equivalent Conversion
            fileContent.append ("Convert the Multiplier operand to its corresponding Booth's equivalent:\n");
            fileContent.append (input.getMultiplier() + " --> " + answer.getBoothsEquivalent() + "\n\n");
        }

        // Append Solution Proper
        fileContent.append ("Perform binary multiplication and acquire the product:\n");
        fileContent.append ("  " + answer.getMultiplicand() + "\n");

        // If Pen-And-Paper, append multiplier as is
        if (input.getMethod() == 0)
            fileContent.append ("x " + answer.getMultiplier() + "\n");
            // Else, append Booth's Equivalent
        else
            fileContent.append ("x " + answer.getBoothsEquivalent() + "\n");

        // Append bar
        for (int i = 0; i <= answer.getMultiplicand().length() * 2 + 2; i++)
            fileContent.append ("-");
        fileContent.append ("\n");

        // Append each Intermediate
        for (String intermediate : answer.getIntermediates()) {
            fileContent.append (intermediate + "\n");
        }

        // Append bar
        for (int i = 0; i <= answer.getMultiplicand().length() * 2 + 2; i++)
            fileContent.append ("-");
        fileContent.append ("\n");

        // Append Answer
        fileContent.append (answer.getAnswer());
        fileContent.append ("\n\n");

        // Append Final Answer
        fileContent.append ("And the answer is: \n");
        fileContent.append (answer.getMultiplicand() + " * " + answer.getBoothsEquivalent() + " = " + answer.getAnswer());

        return fileContent;
    }

    public Answer showResults () {
        return this.answer;
    }
}
