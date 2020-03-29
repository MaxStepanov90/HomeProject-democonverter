package com.stepanov.democonverter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    @Size(min = 3,max = 20, message = "длина должна быть от 3 до 15 символов")
    private String login;
    @Size(min = 5, message = "длина должна быть не меньше 5 символов")
    private String password;
    private String verifyPassword;
    @Email(regexp = "^(.+)@(.+)$",message = "неправильный адрес почты")
    private String email;
}
