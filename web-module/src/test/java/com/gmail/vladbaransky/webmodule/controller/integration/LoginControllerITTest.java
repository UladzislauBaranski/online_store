package com.gmail.vladbaransky.webmodule.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-integration.properties")
public class LoginControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLoginPage_returnRedirectHomePage() throws Exception {
        mockMvc.perform(get("/")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void getHomePage_returnHomePage() throws Exception {
        mockMvc.perform(get("/home")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void login_returnAccessPage() throws Exception {
        mockMvc.perform(get("/login")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void getAccessDeniedPage_returnAccessDeniedPage() throws Exception {
        mockMvc.perform(get("/403")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }
}
