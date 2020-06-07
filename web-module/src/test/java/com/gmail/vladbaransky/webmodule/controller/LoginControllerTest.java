package com.gmail.vladbaransky.webmodule.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //-----------------------getLoginPage-----------------------------------------
    @Test
    void getLoginPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(redirectedUrl("/home"));
    }

    //-----------------------getHomePage-----------------------------------------
    @Test
    void getHomePage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    //-----------------------login-----------------------------------------
    @Test
    void getAccessPageLogin_returnStatusOk() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    //-----------------------logout-----------------------------------------
    @Test
    void getAccessPageLogout_returnStatusOk() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(redirectedUrl("/login"));
    }

    //-----------------------getAccessDeniedPage-----------------------------------------
    @Test
    void getAccessDeniedPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(status().isOk());
    }
}
