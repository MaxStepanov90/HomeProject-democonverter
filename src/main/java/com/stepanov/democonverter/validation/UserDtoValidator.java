package com.stepanov.democonverter.validation;

import com.stepanov.democonverter.dto.UserDto;
import com.stepanov.democonverter.mapper.UserMapper;
import com.stepanov.democonverter.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDtoValidator implements Validator {

    private final UserServiceImpl userService;

    @Autowired
    public UserDtoValidator(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto userDto = (UserDto) o;

        if (passwordMismatch(userDto)) {
            errors.rejectValue("verifyPassword", "", "пароли не совпадают");
        }
        if (loginRepeat(userDto)) {
            errors.rejectValue("login", "", "пользователь с таким логином уже существует");
        }
        if (emailRepeat(userDto)) {
            errors.rejectValue("email", "", "адрес почты уже используется");
        }
    }

    public boolean emailRepeat(UserDto userDto) {
        String userEmail = userDto.getEmail();
        if (!(userEmail.isEmpty())) {
            UserDto foundUserByEmail = UserMapper.USER_MAPPER.fromUser(userService.findByEmail(userEmail));
            return foundUserByEmail != null && userEmail.equals(foundUserByEmail.getEmail());
        }
        return false;
    }

    public boolean loginRepeat(UserDto userDto) {
        String userLogin = userDto.getLogin();
        if (!(userLogin.isEmpty())) {
            UserDto foundUserByLogin = UserMapper.USER_MAPPER.fromUser(userService.findByLogin(userLogin));
            return foundUserByLogin != null && userLogin.equals(foundUserByLogin.getLogin());
        }
        return false;
    }

    public boolean passwordMismatch(UserDto userDto) {
        return !(userDto.getPassword().equals(userDto.getVerifyPassword()));
    }
}


