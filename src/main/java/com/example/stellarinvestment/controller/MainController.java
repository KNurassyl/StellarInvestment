package com.example.stellarinvestment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String unAuthMainPage() {
        return "Unauth_Main";
    }
}
