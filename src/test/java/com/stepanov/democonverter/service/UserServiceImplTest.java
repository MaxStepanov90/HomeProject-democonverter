package com.stepanov.democonverter.service;

import com.stepanov.democonverter.entities.User;
import com.stepanov.democonverter.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void findByLogin_Should_Return_User() {
        User newUser = new User();
        newUser.setLogin("Max");
        when(userRepository.findByLogin("Max")).thenReturn(newUser);
        assertNotNull(userService.findByLogin("Max"));
        verify(userRepository, times(1)).findByLogin("Max");
    }

    @Test
    public void findByLogin_TestCaptor() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        userService.findByLogin("Max");
        verify(userRepository).findByLogin(captor.capture());
        String argument = captor.getValue();
        assertEquals(argument, "Max");
    }

    @Test
    public void findByLogin_Should_Return_Null_Currency() {
        when(userRepository.findByLogin("xxx")).thenReturn(null);
        assertNull(userService.findByLogin("xxx"));
        verify(userRepository, times(1)).findByLogin("xxx");
    }

    @Test
    public void findByEmail_Should_Return_User() {
        User newUser = new User();
        newUser.setEmail("mu5cool@yandex.ru");
        when(userRepository.findByEmail("mu5cool@yandex.ru")).thenReturn(newUser);
        assertNotNull(userService.findByEmail("mu5cool@yandex.ru"));
        verify(userRepository, times(1)).findByEmail("mu5cool@yandex.ru");
    }

    @Test
    public void findByEmail_TestCaptor() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        userService.findByEmail("mu5cool@yandex.ru");
        verify(userRepository).findByEmail(captor.capture());
        String argument = captor.getValue();
        assertEquals(argument, "mu5cool@yandex.ru");
    }

    @Test
    public void findByEmail_Should_Return_Null_Currency() {
        when(userRepository.findByEmail("m@yandex.ru")).thenReturn(null);
        assertNull(userService.findByEmail("m@yandex.ru"));
        verify(userRepository, times(1)).findByEmail("m@yandex.ru");
    }
}