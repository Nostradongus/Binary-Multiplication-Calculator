package com.csarch2.group11.binarymultiplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    @RequestMapping(value = "/calculator")
    public String index() {
        return "calculator";
    }
}