package com.gmail.vladbaransky.webmodule.controller.integration;

import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(roles = "SALE_USER")
@TestPropertySource("/application-integration.properties")
public class OrderControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/data.sql")
    public void getOrdersByPage_returnOrders() throws Exception {
        mockMvc.perform(get("/orders")
                .contentType(MediaType.ALL_VALUE)
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("NEW")))
                .andExpect(content().string(containsString("25.5")));
    }

    @Test
    public void getOrderById_returnOrder() throws Exception {
        mockMvc.perform(get("/orders/1")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("NEW")))
                .andExpect(content().string(containsString("25.5")));
    }

    @Test
    public void refactorOrdersPage_returnOrders() throws Exception {
        mockMvc.perform(post("/orders/refactor")
                .contentType(MediaType.TEXT_HTML)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void refactorOrders_returnOrders() throws Exception {
        mockMvc.perform(post("/orders/refactor/status")
                .contentType(MediaType.TEXT_HTML)
                .param("status", String.valueOf(StatusEnum.NEW))
                .param("id", "1"))
                .andExpect(redirectedUrl("/orders"));
    }
}
