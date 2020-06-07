package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.servicemodule.model.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewByPage(int page);

    List<Integer> deleteByIds(List<Long> ids);

    ReviewDTO addReview(ReviewDTO reviewDTO);
}
