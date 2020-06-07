package com.gmail.vladbaransky.servicemodule.util.converter;

import com.gmail.vladbaransky.repositorymodule.model.Review;
import com.gmail.vladbaransky.repositorymodule.model.User;
import com.gmail.vladbaransky.servicemodule.model.ReviewDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;

public class ReviewConverter {
    public static Review getObjectFromDTO(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setReview(reviewDTO.getReview());
        review.setDate(reviewDTO.getDate());
        review.setShowStatus(reviewDTO.getShowStatus());
        if (reviewDTO.getUserDTO() != null) {
            User user = new User();
            user.setId(reviewDTO.getUserDTO().getId());
            user.setName(reviewDTO.getUserDTO().getName());
            user.setSurname(reviewDTO.getUserDTO().getSurname());
            user.setPatronymic(reviewDTO.getUserDTO().getPatronymic());
            user.setUsername(reviewDTO.getUserDTO().getUsername());
            user.setPassword(reviewDTO.getUserDTO().getPassword());
            user.setRole(reviewDTO.getUserDTO().getRole());
            user.setExist(reviewDTO.getUserDTO().getExist());
            review.setUser(user);
        }
        return review;
    }

    public static ReviewDTO getDTOFromObject(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setReview(review.getReview());
        reviewDTO.setDate(review.getDate());
        reviewDTO.setShowStatus(review.getShowStatus());
        if (review.getUser() != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(review.getUser().getId());
            userDTO.setName(review.getUser().getName());
            userDTO.setSurname(review.getUser().getSurname());
            userDTO.setPatronymic(review.getUser().getPatronymic());
            userDTO.setUsername(review.getUser().getUsername());
            userDTO.setPassword(review.getUser().getPassword());
            userDTO.setRole(review.getUser().getRole());
            userDTO.setTelephone(review.getUser().getTelephone());
            userDTO.setAddress(review.getUser().getAddress());
            reviewDTO.setUserDTO(userDTO);
        }
        return reviewDTO;
    }
}
