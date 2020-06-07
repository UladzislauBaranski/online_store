package com.gmail.vladbaransky.webmodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserRestController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    //---------------------------addUsers------------------------------------
    @Test
    void addUsers_returnStatusOk() throws Exception {
        UserDTO user = getUserDTO();
        String content = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void addUsers_callBusinessLogic() throws Exception {
        UserDTO user = getUserDTO();
        String content = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void addUsers_returnUsers() throws Exception {
        UserDTO user = getUserDTO();
        String content = objectMapper.writeValueAsString(user);
        UserDTO returnedUser = getAddedUserDTO();

        when(userService.addUser(user)).thenReturn(returnedUser);
        MvcResult result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();
        verify(userService, times(1)).addUser(eq(user));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String returnedContent = objectMapper.writeValueAsString(returnedUser);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    private UserDTO getUserDTO() {
        UserDTO user = new UserDTO();
        user.setName("name");
        user.setSurname("surname");
        user.setPatronymic("patronymic");
        user.setUsername("username");
        user.setRole(RoleEnum.SALE_USER);
        return user;
    }

    private UserDTO getAddedUserDTO() {
        UserDTO user = new UserDTO();
        user.setId(1L);
        user.setName("name");
        user.setSurname("surname");
        user.setPatronymic("patronymic");
        user.setUsername("username");
        user.setRole(RoleEnum.SALE_USER);
        return user;
    }
}
