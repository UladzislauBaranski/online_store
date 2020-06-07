package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.ReviewRepository;
import com.gmail.vladbaransky.repositorymodule.model.*;
import com.gmail.vladbaransky.servicemodule.impl.ReviewServiceImpl;
import com.gmail.vladbaransky.servicemodule.model.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.vladbaransky.servicemodule.util.TestValue.getIdList;
import static com.gmail.vladbaransky.servicemodule.util.TestValue.getStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setup() {
        this.reviewService = new ReviewServiceImpl(reviewRepository);
    }

    @Test
    public void getReviewsByPage_returnArticles() {
        List<Review> reviews = getAddedReviewList();
        when(reviewRepository.getObjectByPage(0, 10)).thenReturn(reviews);
        List<ReviewDTO> returnedReviews = reviewService.getReviewByPage(1);
        verify(reviewRepository, times(1)).getObjectByPage(0, 10);
        assertThat(returnedReviews).isNotNull();
        for (int i = 0; i < returnedReviews.size(); i++) {
            getAssertion(returnedReviews.get(i), reviews.get(i));
        }
    }

    @Test
    public void deleteById_returnStatus() {
        List<Long> ids = getIdList();
        List<Integer> status = getStatus();
        when(reviewRepository.deleteObjectByIdList(ids)).thenReturn(status);
        List<Integer> returnedStatus = reviewService.deleteByIds(ids);
        verify(reviewRepository, times(1)).deleteObjectByIdList(ids);
        assertThat(returnedStatus).isNotNull();
        for (int i = 0; i < status.size(); i++) {
            assertThat(returnedStatus.get(i)).isEqualTo(status.get(i));
        }
    }

    private Review getReview() {
        Review review = new Review();
        review.setReview("review");
        review.setDate(LocalDate.now());
        review.setShowStatus(true);

        return review;
    }

    private List<Review> getAddedReviewList() {
        List<Review> reviews = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Review review = getReview();
            review.setId(i);
            reviews.add(review);
        }
        return reviews;
    }

    private void getAssertion(ReviewDTO returnedReview, Review review) {
        assertThat(returnedReview).isNotNull();
        assertThat(returnedReview.getId()).isEqualTo(review.getId());
        assertThat(returnedReview.getDate()).isEqualTo(review.getDate());
        assertThat(returnedReview.getReview()).isEqualTo(review.getReview());
        assertThat(returnedReview.getShowStatus()).isEqualTo(review.getShowStatus());
    }
}
