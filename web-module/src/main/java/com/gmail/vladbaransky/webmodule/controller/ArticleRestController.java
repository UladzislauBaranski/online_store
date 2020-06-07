package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.servicemodule.ArticleService;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.ArticleDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {

    private final ArticleService articleService;
    private final UserService userService;

    public ArticleRestController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping
    public List<ArticleDTO> getArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/{id}")
    public ArticleDTO getArticles(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @PostMapping
    public ArticleDTO addArticle(@RequestBody ArticleDTO article) {
        UserDTO user = userService.getProfileCurrentUser();
        article.setUserDTO(user);
        return articleService.updateArticle(article);
    }

    @DeleteMapping("/{id}")
    public Integer deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticleById(id);
    }
}
