package com.gmail.vladbaransky.servicemodule.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Objects;

import static com.gmail.vladbaransky.servicemodule.constant.validation.ReviewValidationMessages.DATE_PATTERN;
import static com.gmail.vladbaransky.servicemodule.constant.validation.ReviewValidationMessages.NOT_EMPTY_REVIEW_TEXT_MESSAGE;

public class ReviewDTO {
    private Long id;

    @NotEmpty(message = NOT_EMPTY_REVIEW_TEXT_MESSAGE)
    private String review;

    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate date;
    private Boolean showStatus;
    private UserDTO userDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDTO reviewDTO = (ReviewDTO) o;
        return Objects.equals(id, reviewDTO.id) &&
                Objects.equals(review, reviewDTO.review) &&
                Objects.equals(date, reviewDTO.date) &&
                Objects.equals(showStatus, reviewDTO.showStatus) &&
                Objects.equals(userDTO, reviewDTO.userDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, review, date, showStatus, userDTO);
    }
}
