package com.gmail.vladbaransky.webmodule.controller;

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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private UserService userService;

    //-----------------------getUsers--------------------------------
    @Test
    void getUsers_returnStatusOk() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getUsersWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/users")
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getUsersWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.TEXT_HTML)
                .param("page", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUsers_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/users")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUserByPage(eq(1));
    }

    @Test
    void getUsers_returnUsers() throws Exception {
        List<UserDTO> users = getAddedUserListDTO();
        when(userService.getUserByPage(1)).thenReturn(users);
        MvcResult result = mockMvc.perform(get("/users")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("name");
        Assertions.assertThat(expectedReturnedContent).contains("surname");
        Assertions.assertThat(expectedReturnedContent).contains("patronymic");
        Assertions.assertThat(expectedReturnedContent).contains("username");
        Assertions.assertThat(expectedReturnedContent).contains("SALE_USER");
    }

    //-----------------------getUsersBuId--------------------------------
    @Test
    void getUsersById_returnStatusOk() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getUsersByIdWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/users/inv")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUsersById_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/users/1")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        verify(userService, times(1)).findUsersById(eq(1L));
    }

    @Test
    void getUsersById_returnUsers() throws Exception {
        UserDTO users = getAddedUserDTO();
        when(userService.findUsersById(1L)).thenReturn(users);
        MvcResult result = mockMvc.perform(get("/users/1")
                .contentType(MediaType.TEXT_HTML)).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("name");
        Assertions.assertThat(expectedReturnedContent).contains("surname");
        Assertions.assertThat(expectedReturnedContent).contains("patronymic");
        Assertions.assertThat(expectedReturnedContent).contains("username");
    }

    //-----------------------addUserPage-----------------------------------------
    @Test
    void addUsersPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/users/add"))
                .andExpect(status().isOk());
    }

    //--------------------------deleteUser------------------------------------------
    @Test
    void deleteUsersWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(post("/users/delete")
                .param("ids", String.valueOf(1L)))
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void deleteUsersWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/users/delete")
                .contentType(MediaType.TEXT_HTML)
                .param("ids", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUsers_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/users/delete")
                .contentType(MediaType.TEXT_HTML)
                .param("ids", String.valueOf(1L)))
                .andExpect(redirectedUrl("/users"));
        verify(userService, times(1)).deleteById(eq(idList));
    }


    //-----------------------refactorPassword--------------------------------
    @Test
    void refactorPasswordWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(post("/users/refactor/password")
                .param("ids", String.valueOf(1L)))
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void refactorPasswordWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/users/refactor/password")
                .contentType(MediaType.TEXT_HTML)
                .param("ids", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refactorPassword_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/users/refactor/password")
                .contentType(MediaType.TEXT_HTML)
                .param("ids", String.valueOf(1L)))
                .andExpect(redirectedUrl("/users"));
        verify(userService, times(1)).updatePasswordById(eq(idList));
    }

    //-----------------------refactorPage-----------------------------------------
    @Test
    void refactorPage_returnStatusOk() throws Exception {
        mockMvc.perform(post("/users/refactor")
                .param("ids", String.valueOf(1L)))
                .andExpect(status().isOk());
    }

    //-----------------------refactorRole--------------------------------
    @Test
    void refactorRoleWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(post("/users/refactor/role")
                .param("role", String.valueOf(RoleEnum.ADMINISTRATION))
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    void refactorRoleWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/users/refactor/role")
                .contentType(MediaType.TEXT_HTML)
                .param("role", "inv")
                .param("id", String.valueOf(1L)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refactorRole_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/users/refactor/role")
                .contentType(MediaType.TEXT_HTML)
                .param("role", String.valueOf(RoleEnum.ADMINISTRATION))
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/users"));
        verify(userService, times(1)).updateRoleById(eq(idList), eq(RoleEnum.ADMINISTRATION));
    }

    //-----------------------getUserProfile--------------------------------
    @Test
    void getUserProfile_returnStatusOk() throws Exception {
        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserProfile_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getProfileCurrentUser();
    }

    @Test
    void getUserProfile_returnUsers() throws Exception {
        UserDTO user = getAddedUserDTO();
        when(userService.getProfileCurrentUser()).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/users/profile")
                .contentType(MediaType.TEXT_HTML))
                .andReturn();
        verify(userService, times(1)).getProfileCurrentUser();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("name");
        Assertions.assertThat(expectedReturnedContent).contains("surname");
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

    private List<UserDTO> getAddedUserListDTO() {
        List<UserDTO> users = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            UserDTO user = getUserDTO();
            user.setId(i);
            users.add(user);
        }
        return users;
    }
}

