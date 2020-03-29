package com.stepanov.democonverter.service;

import com.stepanov.democonverter.dto.UserDto;
import com.stepanov.democonverter.entities.User;

public interface UserService {

    User findByLogin(String login);

    User findByEmail(String email);

    void saveUser(UserDto userDto);

}
