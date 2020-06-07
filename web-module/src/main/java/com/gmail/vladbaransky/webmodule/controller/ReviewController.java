package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.servicemodule.ReviewService;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.ReviewDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.DEFAULT_PAGE;
import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.VALUE_IF_DO_NOT_SELECTED;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping
    public String getReview(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page, Model model) {
        List<ReviewDTO> reviews = reviewService.getReviewByPage(page);
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/add")
    public String createReviewPage(Model model) {
        model.addAttribute("review", new ReviewDTO());
        return "create_review_page";
    }

    @PostMapping
    public String addReview(@ModelAttribute(name = "review") ReviewDTO review) {
        review.setDate(LocalDate.now());
        UserDTO user = userService.getProfileCurrentUser();
        review.setUserDTO(user);
        review.setShowStatus(true);
        reviewService.addReview(review);
        return "redirect:/reviews";
    }

    @PostMapping("/delete")
    public String deleteReviews(@RequestParam(defaultValue = VALUE_IF_DO_NOT_SELECTED) List<Long> ids) {
        reviewService.deleteByIds(ids);
        return "redirect:/reviews";
    }
}
