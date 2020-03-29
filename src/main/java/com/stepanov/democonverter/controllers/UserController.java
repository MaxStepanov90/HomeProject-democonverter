package com.stepanov.democonverter.controllers;

import com.stepanov.democonverter.dto.UserDto;
import com.stepanov.democonverter.service.UserServiceImpl;
import com.stepanov.democonverter.validation.UserDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserServiceImpl userService;
    private UserDtoValidator userDtoValidator;

    @Autowired
    public UserController(UserServiceImpl userService, UserDtoValidator userDtoValidator) {
        this.userService = userService;
        this.userDtoValidator = userDtoValidator;
    }

    @GetMapping("/registration")
    public String getUserFormRegistration(UserDto userDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid UserDto userDto, BindingResult result) {
        userDtoValidator.validate(userDto, result);
        if (result.hasErrors()) {
            return "registration";
        }
        userService.saveUser(userDto);
        return "/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
}
