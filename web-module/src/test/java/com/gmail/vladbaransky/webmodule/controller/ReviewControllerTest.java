package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.servicemodule.ReviewService;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReviewController.class)
@ActiveProfiles("test")
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    //-----------------------getReviewByPage---------------------------------
    @Test
    void getReviewsByPage_returnStatusOk() throws Exception {
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    void getReviewsWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(get("/reviews")
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getReviewsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(get("/reviews")
                .param("page", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReviews_callBusinessLogic() throws Exception {
        mockMvc.perform(get("/reviews")
                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
        verify(reviewService, times(1)).getReviewByPage(eq(1));
    }

    @Test
    void getReviews_returnReviews() throws Exception {
        List<ReviewDTO> reviews = getAddedReviewListDTO();
        when(reviewService.getReviewByPage(1)).thenReturn(reviews);
        MvcResult result = mockMvc.perform(get("/reviews")
                .contentType(MediaType.TEXT_HTML)
                .param("page", String.valueOf(1))).andReturn();
        verify(reviewService, times(1)).getReviewByPage(eq(1));

        String expectedReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedReturnedContent).contains("1");
        Assertions.assertThat(expectedReturnedContent).contains("review");
    }

    //-----------------------getReviewPage---------------------------------
    @Test
    void getReviews_returnStatusOk() throws Exception {
        mockMvc.perform(get("/reviews/add"))
                .andExpect(status().isOk());
    }

    //-----------------------addReviews---------------------------------

    @Test
    void addReviewsWithParam_returnStatusOk() throws Exception {
        mockMvc.perform(post("/reviews")
                .param("review", "review")
                .param("status", String.valueOf(true)))
                .andExpect(redirectedUrl("/reviews"));
    }

//-----------------------------deleteReviews-----------------------------------------------------------

    @Test
    void deleteReviews_returnStatusOk() throws Exception {
        mockMvc.perform(post("/reviews/delete")
                .param("ids", String.valueOf(1L)))
                .andExpect(redirectedUrl("/reviews"));
    }

    @Test
    void deleteReviewsWithInvalidParam_returnBadRequest() throws Exception {
        mockMvc.perform(post("/reviews/delete")
                .param("ids", "inv"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteArticles_callBusinessLogic() throws Exception {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        mockMvc.perform(post("/reviews/delete")
                .param("ids", String.valueOf(1L)))
                .andExpect(redirectedUrl("/reviews"));
        verify(reviewService, times(1)).deleteByIds(eq(idList));
    }

    private ReviewDTO getReviewDTO() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReview("review");
        reviewDTO.setDate(LocalDate.now());
        reviewDTO.setShowStatus(true);
        reviewDTO.setUserDTO(getAddedUserDTO());
        return reviewDTO;
    }

    private List<ReviewDTO> getAddedReviewListDTO() {
        List<ReviewDTO> reviews = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            ReviewDTO review = getReviewDTO();
            review.setId(i);
            reviews.add(review);
        }
        return reviews;
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

