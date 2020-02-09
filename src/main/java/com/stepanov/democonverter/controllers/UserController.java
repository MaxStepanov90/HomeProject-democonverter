package com.stepanov.democonverter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String login(ModelMap modelMap) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
}
