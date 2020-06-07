package com.gmail.vladbaransky.webmodule.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(roles = "SECURE_API_USER")
@TestPropertySource("/application-integration.properties")
public class ArticleRestControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/data.sql")
    public void getArticles_returnArticles() throws Exception {
        mockMvc.perform(get("/api/articles")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
               /* .andExpect(content().json())*/
                .andExpect(jsonPath("$[0].date", is("2012-12-12")).exists())
                .andExpect(jsonPath("$[0].title", is("testTitle")).exists())
                .andExpect(jsonPath("$[0].summary", is("testSummary")).exists())
                .andExpect(jsonPath("$[0].content", is("testContent")).exists());
    }

    @Test
    public void deleteArticles_returnArticles() throws Exception {
        mockMvc.perform(delete("/api/articles/1")
                .contentType(MediaType.ALL_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.ALL_VALUE));
    }
}
