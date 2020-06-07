package com.gmail.vladbaransky.webmodule.controller.integration;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
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
@WithMockUser(roles = "ADMINISTRATION")
@TestPropertySource("/application-integration.properties")
public class UserControllerITTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/data.sql")
    public void getUsersByPage_returnUsers() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(content().string(containsString("testName")))
                .andExpect(content().string(containsString("testSurname")))
                .andExpect(content().string(containsString("testPatronymic")))
                .andExpect(content().string(containsString("ADMINISTRATION")));
    }

    @Test
    public void getUsersPage_returnUsersPage() throws Exception {
        mockMvc.perform(get("/users/add")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void deleteUsers_returnUsers() throws Exception {
        mockMvc.perform(post("/users/delete")
                .contentType(MediaType.TEXT_HTML)
                .param("id", "1"))
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    public void refactorUsersRolePage_returnUsers() throws Exception {
        mockMvc.perform(post("/users/refactor")
                .contentType(MediaType.TEXT_HTML)
                .param("ids", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    public void refactorUsersRole_returnUsers() throws Exception {
        mockMvc.perform(post("/users/refactor/role")
                .contentType(MediaType.TEXT_HTML)
                .param("role", String.valueOf(RoleEnum.ADMINISTRATION))
                .param("id", "1"))
                .andExpect(redirectedUrl("/users"));
    }
}
