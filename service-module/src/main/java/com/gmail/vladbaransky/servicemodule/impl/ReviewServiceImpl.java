package com.gmail.vladbaransky.servicemodule.impl;

import com.gmail.vladbaransky.repositorymodule.ReviewRepository;
import com.gmail.vladbaransky.repositorymodule.model.Review;
import com.gmail.vladbaransky.servicemodule.ReviewService;
import com.gmail.vladbaransky.servicemodule.model.ReviewDTO;
import com.gmail.vladbaransky.servicemodule.util.converter.ReviewConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final int OBJECT_BY_PAGE = 10;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public List<ReviewDTO> getReviewByPage(int page) {
        int startPosition = (page - 1) * OBJECT_BY_PAGE;
        List<Review> reviewList = reviewRepository.getObjectByPage(startPosition, OBJECT_BY_PAGE);
        return reviewList.stream()
                .map(ReviewConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Integer> deleteByIds(List<Long> ids) {
        return reviewRepository.deleteObjectByIdList(ids);
    }

    @Transactional
    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        Review review = ReviewConverter.getObjectFromDTO(reviewDTO);
        Review returnedReview = reviewRepository.addObject(review);
        return ReviewConverter.getDTOFromObject(returnedReview);
    }
}
