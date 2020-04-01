package com.stepanov.democonverter.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationIntegrationTest {
    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(userController).isNotNull();
    }

    @Test
    public void getUserFormRegistrationPageTest() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUser_With_ValidCredentials_Successful_And_Return_LoginPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Andrew")
                .param("email", "andrew@mail.ru")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(0))
                .andExpect(view().name("login"));
    }

    @Test
    public void registrationUserFail_With_Login_LessThan3Word_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "M")
                .param("email", "mu5cool@mail.ru")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_EmptyLogin_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "")
                .param("email", "mu5cool@mail.ru")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_IncorrectEmail_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Andrew")
                .param("email", "m")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_ExistingLogin_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Max")
                .param("email", "max@mail.ru")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_ExistingEmail_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Andrew")
                .param("email", "mu5cool@yandex.ru")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_ExistingLogin_And_ExistingEmail_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Max")
                .param("email", "mu5cool@yandex.ru")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_EmptyEmail_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Andrew")
                .param("email", "")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_EmptyLogin_And_EmptyEmail_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "")
                .param("email", "")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_MismatchedPasswords_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Andrew")
                .param("email", "Andrew@yandex.ru")
                .param("password", "111111")
                .param("verifyPassword", "22222"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "verifyPassword"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_EmptyVerifyPassword_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Andrew")
                .param("email", "andrew@yandex.ru")
                .param("password", "111111")
                .param("verifyPassword", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "verifyPassword"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_EmptyPassword_And_EmptyVerifyPassword_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "Andrew")
                .param("email", "andrew@yandex.ru")
                .param("password", "")
                .param("verifyPassword", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrors("userDto", "password"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_LoginLessThan3Word_And_IncorrectEmail_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "M")
                .param("email", "m")
                .param("password", "111111")
                .param("verifyPassword", "111111"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(2))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_All_IncorrectData_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "M")
                .param("email", "m")
                .param("password", "1")
                .param("verifyPassword", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(4))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(model().attributeHasFieldErrors("userDto", "password"))
                .andExpect(model().attributeHasFieldErrors("userDto", "verifyPassword"))
                .andExpect(view().name("registration"));
    }

    @Test
    public void registrationUserFail_With_AllEmptyData_And_Return_RegistrationPage_Test() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("login", "")
                .param("email", "")
                .param("password", "")
                .param("verifyPassword", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(model().errorCount(3))
                .andExpect(model().attributeHasFieldErrors("userDto", "login"))
                .andExpect(model().attributeHasFieldErrors("userDto", "email"))
                .andExpect(model().attributeHasFieldErrors("userDto", "password"))
                .andExpect(view().name("registration"));
    }
}

