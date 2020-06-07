package com.gmail.vladbaransky.servicemodule.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.gmail.vladbaransky.servicemodule.constant.validation.ArticleValidationMessages.*;
import static com.gmail.vladbaransky.servicemodule.constant.validation.ArticleValidationRules.*;

public class ArticleDTO {

    private Long id;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate date;

    @NotEmpty(message = NOT_EMPTY_TITLE_MESSAGE)
    @Pattern(regexp = TITLE_PATTERN, message = TITLE_PATTERN_MESSAGE)
    @Size(max = MAX_TITLE_SIZE)
    private String title;

    @Pattern(regexp = SUMMARY_PATTERN, message = SUMMARY_PATTERN_MESSAGE)
    @Size(max = MAX_SUMMARY_SIZE)
    private String summary;

    @NotEmpty(message = NOT_EMPTY_CONTENT_MESSAGE)
    @Size(max = MAX_CONTENT_SIZE, message = CONTENT_SIZE_MESSAGE)
    private String content;

    private List<CommentDTO> commentDTOList = new ArrayList<>();
    private UserDTO userDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<CommentDTO> getCommentDTOList() {
        return commentDTOList;
    }

    public void setCommentDTOList(List<CommentDTO> commentDTOList) {
        this.commentDTOList = commentDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDTO that = (ArticleDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(title, that.title) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(content, that.content) &&
                Objects.equals(commentDTOList, that.commentDTOList) &&
                Objects.equals(userDTO, that.userDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, title, summary, content, commentDTOList, userDTO);
    }
}
