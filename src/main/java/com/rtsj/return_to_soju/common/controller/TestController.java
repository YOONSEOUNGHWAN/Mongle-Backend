package com.rtsj.return_to_soju.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/join")
    public String Join(){
        return "join";
    }
}
