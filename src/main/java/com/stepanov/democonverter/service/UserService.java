package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.User;

import java.util.List;

public interface UserService {

    User findByLogin(String login);


}
