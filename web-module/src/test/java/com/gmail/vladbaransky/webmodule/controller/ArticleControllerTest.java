package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.servicemodule.ArticleService;
import com.gmail.vladbaransky.servicemodule.CommentService;
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
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ArticleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private ArticleService articleService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private UserService userService;

    //---------------------------getArticleByPage------------------------------------
    @Test
    void getArticles_returnStatusOk() throws Exception {
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk());
    }

    @Test
    void getArticlesWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/articles")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1L)))
                .andExpect(status().isOk());
    }

    @Test
    void getArticlesWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/articles")
                .contentType(MediaType.TEXT_HTML)
                .param("page", "inv")).andExpect(status().isBadRequest());
    }

    @Test
    void getArticles_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/articles")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andExpect(status().isOk());
        verify(articleService, times(1)).getArticlesByPage(eq(1));
    }

    @Test
    void getArticles_returnArticles() throws Exception {
        List<ArticleDTO> articles = getAddedArticleListDTO();
        when(articleService.getArticlesByPage(1)).thenReturn(articles);
        MvcResult result = mockMvc.perform(get("/articles")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("title");
        Assertions.assertThat(expectedReturnedContent).contains("summary");
        Assertions.assertThat(expectedReturnedContent).contains("content");
    }

    @Test
    void getAddArticlesPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/articles/add"))
                .andExpect(status().isOk());
    }

    //-------------------getArticleById---------------------------------

    @Test
    void getArticleById_returnStatusOk() throws Exception {
        mockMvc.perform(get("/articles/1")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    void getArticleWithInvalidId_returnBadRequest() throws Exception {
        mockMvc.perform(get("/articles/inv")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getArticleById_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/articles/1")
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        verify(articleService, times(1)).getArticleById(eq(1L));
    }

    @Test
    void getArticleById_returnArticles() throws Exception {
        ArticleDTO article = getAddedArticleDTO();
        when(articleService.getArticleById(1L)).thenReturn(article);
        MvcResult result = mockMvc.perform(get("/articles/1")
                .contentType(MediaType.TEXT_HTML)).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("title");
        Assertions.assertThat(expectedReturnedContent).contains("content");
    }

    //---------------------------addArticle-------------------------------------------------

    @Test
    void addArticlesWithParam_redirectOnUrl() throws Exception {
        ArticleDTO article = getArticleDTO();
        mockMvc.perform(post("/articles")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                // .param("date", String.valueOf(article.getDate()))
                .param("title", String.valueOf(article.getTitle()))
                .param("summary", String.valueOf(article.getSummary()))
                .param("content", String.valueOf(article.getContent()))
        )
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    void addArticles_callBusinessLogic() throws Exception {
        UserDTO user = getAddedUserDTO();
        ArticleDTO article = getArticleDTO();
        when(userService.getProfileCurrentUser()).thenReturn(user);
        mockMvc.perform(post("/articles")
                //  .param("date", String.valueOf(article.getDate()))
                .param("title", String.valueOf(article.getTitle()))
                .param("summary", String.valueOf(article.getSummary()))
                .param("content", String.valueOf(article.getContent())))
                .andExpect(redirectedUrl("/articles"));
        verify(userService, times(1)).getProfileCurrentUser();
    }

//-----------------------------deleteArticle-----------------------------------------------------------

    @Test
    void deleteArticles_returnStatusOk() throws Exception {
        mockMvc.perform(post("/articles/delete")
                .param("id", "1"))
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    void deleteArticlesWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/articles/delete")
                .param("id", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteArticles_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/articles/delete")
                .contentType(MediaType.TEXT_HTML)
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/articles"));
        verify(articleService, times(1)).deleteArticlesByIdList(eq(idList));
    }

    @Test
    void deleteArticles_returnArticles() throws Exception {
        List<ArticleDTO> articles = getAddedArticleListDTO();
        when(articleService.getArticlesByPage(1)).thenReturn(articles);
        MvcResult result = mockMvc.perform(get("/articles")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andReturn();

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("title");
        Assertions.assertThat(expectedReturnedContent).contains("summary");
        Assertions.assertThat(expectedReturnedContent).contains("content");
    }

    //------------------------deleteComments-------------------------------

    @Test
    void deleteComments_returnStatusOk() throws Exception {
        mockMvc.perform(post("/articles/delete/comments")
                .param("id", "1"))
                .andExpect(redirectedUrl("/articles"));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void deleteCommentsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/articles/delete/comments")
                .param("id", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteComments_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/articles/delete/comments")
                .contentType(MediaType.TEXT_HTML)
                .param("id", String.valueOf(1L)))
                .andExpect(redirectedUrl("/articles"));
        verify(commentService, times(1)).deleteCommentsByIdList(eq(idList));
    }

    //---------------------------------------------------------------------------

    private ArticleDTO getArticleDTO() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setDate(LocalDate.now());
        articleDTO.setTitle("title");
        articleDTO.setSummary("summary");
        articleDTO.setContent("content");
        return articleDTO;
    }

    private ArticleDTO getAddedArticleDTO() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setDate(LocalDate.now());
        articleDTO.setTitle("title");
        articleDTO.setSummary("summary");
        articleDTO.setContent("content");
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
