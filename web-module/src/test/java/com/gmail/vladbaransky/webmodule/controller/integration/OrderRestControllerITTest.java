package com.gmail.vladbaransky.webmodule.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(roles = "SECURE_API_USER")
@TestPropertySource("/application-integration.properties")
public class OrderRestControllerITTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/data.sql")
    public void getAllOrders_returnAllOrders() throws Exception {
        mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("NEW")))
                .andExpect(content().string(containsString("25.5")))
                .andExpect(content().string(containsString("2012-12-12")));
    }

    @Test
    public void getOrdersById_returnOrders() throws Exception {
        mockMvc.perform(get("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("NEW")))
                .andExpect(content().string(containsString("25.5")))
                .andExpect(content().string(containsString("2012-12-12")));
    }
}
