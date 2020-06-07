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

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(roles = "SECURE_API_USER")
@TestPropertySource("/application-integration.properties")
public class ItemRestControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/data.sql")
    public void getAllItems_returnAllItems() throws Exception {
        mockMvc.perform(get("/api/items")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].title", is("testTitle")).exists())
                .andExpect(jsonPath("$[0].price", is("25.5")).exists())
                .andExpect(jsonPath("$[0].summary", is("testSummary")).exists());
    }

    @Test
    public void deleteItems_returnItems() throws Exception {
        mockMvc.perform(delete("/api/items/1")
                .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.ALL_VALUE));
    }
}
