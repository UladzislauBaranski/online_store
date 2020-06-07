package com.gmail.vladbaransky.repositorymodule.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String review;
    private LocalDate date;
    @Column(name = "show_status")
    private Boolean showStatus;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return Objects.equals(id, review1.id) &&
                Objects.equals(review, review1.review) &&
                Objects.equals(date, review1.date) &&
                Objects.equals(showStatus, review1.showStatus) &&
                Objects.equals(user, review1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, review, date, showStatus, user);
    }
}
