package com.csarch2.group11.binarymultiplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebController {

    @GetMapping(value="/")
    public void home(HttpServletResponse httpServletResponse){
        try {
            httpServletResponse.sendRedirect("/calculator");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping (value = "/calculator")
    public String calculator(Model model) {
        return "calculator";
    }
}