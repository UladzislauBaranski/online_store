package com.gmail.vladbaransky.webmodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.servicemodule.ArticleService;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.ArticleDTO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleRestController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ArticleRestControllerTest {
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
    private ArticleService articleService;

    @MockBean
    private UserService userService;

    //---------------------------getAllArticles------------------------------------
    @Test
    void getAllArticles_returnStatusOk() throws Exception {
        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllArticles_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk());
        verify(articleService, times(1)).getAllArticles();
    }

    @Test
    void getAllArticles_returnArticles() throws Exception {
        List<ArticleDTO> articles = getAddedArticleListDTO();
        when(articleService.getAllArticles()).thenReturn(articles);
        MvcResult result = mockMvc.perform(get("/api/articles")).andReturn();
        verify(articleService, times(1)).getAllArticles();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String content = objectMapper.writeValueAsString(articles);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(content);
    }

    //---------------------------getArticleById------------------------------------
    @Test
    void getArticles_returnStatusOk() throws Exception {
        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getArticlesWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/api/articles/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getArticles_callBusinessLogic() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk());
        verify(articleService, times(1)).getArticleById(eq(id));
    }

    @Test
    void getArticles_returnArticles() throws Exception {
        Long id = 1L;
        ArticleDTO articles = getAddedArticleDTO();
        when(articleService.getArticleById(id)).thenReturn(articles);
        MvcResult result = mockMvc.perform(get("/api/articles/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String content = objectMapper.writeValueAsString(articles);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(content);
    }

    //---------------------------addArticle------------------------------------
    @Test
    void addArticles_returnStatusOk() throws Exception {
        ArticleDTO article = getArticleDTO();
        String content = objectMapper.writeValueAsString(article);
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void addArticles_callBusinessLogic() throws Exception {
        ArticleDTO article = getArticleDTO();
        String content = objectMapper.writeValueAsString(article);
        mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void addArticles_returnArticles() throws Exception {
        ArticleDTO article = getArticleWithoutUserDTO();
        ArticleDTO articleWithoutId = getArticleDTO();
        articleWithoutId.setUserDTO(getAddedUserDTO());
        String content = objectMapper.writeValueAsString(article);
        ArticleDTO returnedArticle = getAddedArticleDTO();
        UserDTO user = getAddedUserDTO();

        when(userService.getProfileCurrentUser()).thenReturn(user);
        when(articleService.updateArticle(articleWithoutId)).thenReturn(returnedArticle);
        MvcResult result = mockMvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content)).andReturn();
        verify(userService, times(1)).getProfileCurrentUser();
        verify(articleService, times(1)).updateArticle(eq(articleWithoutId));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String returnedContent = objectMapper.writeValueAsString(returnedArticle);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(returnedContent);
    }

    //-----------------------------deleteArticle-----------------------------------------------------------
    @Test
    void deleteArticles_returnStatusOk() throws Exception {
        mockMvc.perform(delete("/api/articles/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteArticlesWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(delete("/api/articles/inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteArticles_callBusinessLogic() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/api/articles/1"))
                .andExpect(status().isOk());
        verify(articleService, times(1)).deleteArticleById(eq(id));
    }

    @Test
    void deleteArticles_returnArticles() throws Exception {
        Long id = 1L;
        Integer status = 1;
        when(articleService.deleteArticleById(id)).thenReturn(status);
        MvcResult result = mockMvc.perform(delete("/api/articles/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        verify(articleService, times(1)).deleteArticleById(eq(id));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        String expectedStatus = objectMapper.writeValueAsString(status);
        Assertions.assertThat(expectedReturnedContent).isEqualTo(expectedStatus);
    }

    //----------------------------------------------------------------------------------
    private ArticleDTO getArticleWithoutUserDTO() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setDate(LocalDate.now());
        articleDTO.setTitle("title");
        articleDTO.setSummary("summary");
        articleDTO.setContent("content");
        return articleDTO;
    }

    private ArticleDTO getArticleDTO() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setDate(LocalDate.now());
        articleDTO.setTitle("title");
        articleDTO.setSummary("summary");
        articleDTO.setContent("content");
        articleDTO.setUserDTO(getAddedUserDTO());
        return articleDTO;
    }

    private ArticleDTO getAddedArticleDTO() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setDate(LocalDate.now());
        articleDTO.setTitle("title");
        articleDTO.setSummary("summary");
        articleDTO.setContent("content");
        articleDTO.setUserDTO(getAddedUserDTO());
        return articleDTO;
    }

    private List<ArticleDTO> getAddedArticleListDTO() {
        List<ArticleDTO> articles = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            ArticleDTO article = getArticleDTO();
            article.setId(i);
            articles.add(article);
        }
        return articles;
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

