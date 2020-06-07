package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.servicemodule.ArticleService;
import com.gmail.vladbaransky.servicemodule.CommentService;
import com.gmail.vladbaransky.servicemodule.UserService;
import com.gmail.vladbaransky.servicemodule.model.ArticleDTO;
import com.gmail.vladbaransky.servicemodule.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.DEFAULT_PAGE;
import static com.gmail.vladbaransky.webmodule.constant.DefaulValue.VALUE_IF_DO_NOT_SELECTED;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, CommentService commentService, UserService userService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping
    public String getArticlesByPage(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page, Model model) {
        List<ArticleDTO> articles = articleService.getArticlesByPage(page);
        model.addAttribute("articles", articles);
        return "articles_page";
    }

    @GetMapping("/add")
    public String getNewArticlePage(Model model) {
        model.addAttribute("article", new ArticleDTO());
        return "new_article_page";
    }

    @PostMapping
    public String addNewArticle(
            @ModelAttribute(name = "article") @Valid ArticleDTO article,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("article", article);
            return "new_article_page";
        } else {
            UserDTO user = userService.getProfileCurrentUser();
            article.setUserDTO(user);
            article.setDate(LocalDate.now());
            articleService.updateArticle(article);
        }
        return "redirect:/articles";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        ArticleDTO article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article_page";
    }

    @PostMapping("/delete")
    public String deleteArticle(@RequestParam(defaultValue = VALUE_IF_DO_NOT_SELECTED) List<Long> id) {
        articleService.deleteArticlesByIdList(id);
        return "redirect:/articles";
    }

    @PostMapping("/delete/comments")
    public String deleteComments(@RequestParam List<Long> id) {
        commentService.deleteCommentsByIdList(id);
        return "redirect:/articles";
    }
}
