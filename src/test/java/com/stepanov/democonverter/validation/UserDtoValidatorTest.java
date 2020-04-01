package com.stepanov.democonverter.validation;

import com.stepanov.democonverter.dto.UserDto;
import com.stepanov.democonverter.entities.User;
import com.stepanov.democonverter.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class UserDtoValidatorTest {

    @InjectMocks
    private UserDtoValidator userDtoValidator;
    @Mock
    private UserServiceImpl userService;
    @Mock
    Errors errors;

    public User newUserMax() {
        return User.builder()
                .login("Max")
                .email("mu5cool@yandex.ru")
                .password("11111")
                .build();
    }

    @Test
    public void validate_Should_Not_Accept_User_With_Empty_Credentials() {
        UserDto userDto = new UserDto();
        userDto.setPassword("");
        userDto.setVerifyPassword("");
        userDto.setLogin("");
        userDto.setEmail("");

        userDtoValidator.validate(userDto, errors);
        verify(errors, never()).rejectValue(eq("verifyPassword"), any(), any());
        verify(errors, never()).rejectValue(eq("login"), any(), any());
        verify(errors, never()).rejectValue(eq("email"), any(), any());
    }

    @Test
    public void validate_Should_Not_Accept_User_With_Bad_Credentials() {
        UserDto userDto = new UserDto();
        userDto.setPassword("");
        userDto.setVerifyPassword("11111");
        userDto.setLogin("Max");
        userDto.setEmail("mu5cool@yandex.ru");

        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        when(userService.findByEmail("mu5cool@yandex.ru")).thenReturn(newUserMax());
        userDtoValidator.validate(userDto, errors);
        verify(errors, times(1)).rejectValue(eq("verifyPassword"), any(), any());
        verify(errors, times(1)).rejectValue(eq("login"), any(), any());
        verify(errors, times(1)).rejectValue(eq("email"), any(), any());
    }

    @Test
    void emailRepeat_Should_Return_True() {
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("mu5cool@yandex.ru");
        when(userService.findByEmail("mu5cool@yandex.ru")).thenReturn(newUserMax());
        assertTrue(userDtoValidator.emailRepeat(newUserDto));
    }

    @Test
    void emailRepeat_Should_Return_False() {
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("Alex");
        when(userService.findByEmail("mu5cool@yandex.ru")).thenReturn(newUserMax());
        assertFalse(userDtoValidator.emailRepeat(newUserDto));
    }

    @Test
    void emailRepeat_With_Empty_Email_Should_Return_False() {
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("");
        when(userService.findByEmail("mu5cool@yandex.ru")).thenReturn(newUserMax());
        assertFalse(userDtoValidator.emailRepeat(newUserDto));
    }

    @Test
    void loginRepeat_Should_Return_True() {
        UserDto newUserDto = new UserDto();
        newUserDto.setLogin("Max");
        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        assertTrue(userDtoValidator.loginRepeat(newUserDto));
    }

    @Test
    void loginRepeat_Should_Return_False() {
        UserDto newUserDto = new UserDto();
        newUserDto.setLogin("Alex");
        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        assertFalse(userDtoValidator.loginRepeat(newUserDto));
    }

    @Test
    void loginRepeat_With_Empty_Login_Should_Return_False() {
        UserDto newUserDto = new UserDto();
        newUserDto.setLogin("");
        when(userService.findByLogin("Max")).thenReturn(newUserMax());
        assertFalse(userDtoValidator.loginRepeat(newUserDto));
    }

    @Test
    public void passwordMismatch_Should_Return_False() {
        UserDto userDto = new UserDto();
        userDto.setPassword("11111");
        userDto.setVerifyPassword("11111");
        assertFalse(userDtoValidator.passwordMismatch(userDto));
    }

    @Test
    public void passwordMismatch_Should_Return_True() {
        UserDto userDto = new UserDto();
        userDto.setPassword("11111");
        userDto.setVerifyPassword("22222");
        assertTrue(userDtoValidator.passwordMismatch(userDto));
    }
}