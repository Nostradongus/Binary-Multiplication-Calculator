package com.csarch2.group11.binarymultiplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class WebController {
    @RequestMapping(value = "/calculator")
    public String calculator(HttpServletResponse httpServletResponse, Model model) {
        model.addAttribute("input", new Input());
        return "calculator";
    }

    @RequestMapping(value = "/calculator-results")
    public String calculatorResults(@ModelAttribute Input input, Model model) {
        BinaryMultiplicationController controller = new BinaryMultiplicationController();
        controller.performCalculation(input);


        model.addAttribute("answer", controller.showResults());

        switch(input.getMethod()) {
            case 0:
                return "calculator-results-pencil-and-paper";
            case 1:
                return "calculator-results-pencil-and-paper";
            case 2:
                return "calculator-results-pencil-and-paper";
            default:
                return "calculator-results-pencil-and-paper";
        }

    }
}