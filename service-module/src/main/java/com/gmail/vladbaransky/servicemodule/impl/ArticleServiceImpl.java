package com.gmail.vladbaransky.servicemodule.impl;

import com.gmail.vladbaransky.repositorymodule.ArticleRepository;
import com.gmail.vladbaransky.repositorymodule.CommentRepository;
import com.gmail.vladbaransky.repositorymodule.model.Article;
import com.gmail.vladbaransky.servicemodule.ArticleService;
import com.gmail.vladbaransky.servicemodule.model.ArticleDTO;
import com.gmail.vladbaransky.servicemodule.util.converter.ArticleConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final int OBJECT_BY_PAGE = 10;

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public List<ArticleDTO> getArticlesByPage(int page) {
        int startPosition = (page - 1) * OBJECT_BY_PAGE;
        List<Article> articles = articleRepository.getObjectByPage(startPosition, OBJECT_BY_PAGE);
        return articles.stream()
                .map(ArticleConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.getAllObject();
        return articles.stream()
                .map(ArticleConverter::getDTOFromObject)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.getObjectById(id);
        return ArticleConverter.getDTOFromObject(article);
    }

    @Override
    @Transactional
    public ArticleDTO addArticle(ArticleDTO articleDTO) {
        Article article = ArticleConverter.getObjectFromDTO(articleDTO);
        Article returnedArticle = articleRepository.addObject(article);
        return ArticleConverter.getDTOFromObject(returnedArticle);
    }

    @Override
    @Transactional
    public Integer deleteArticleById(Long id) {
        return articleRepository.deleteObjectById(id);
    }

    @Override
    @Transactional
    public List<Integer> deleteArticlesByIdList(List<Long> ids) {

        commentRepository.deleteChildObjectByParentId(ids);
        return articleRepository.deleteObjectByIdList(ids);
    }

    @Transactional
    @Override
    public ArticleDTO updateArticle(ArticleDTO articleDTO) {
        Article article = ArticleConverter.getObjectFromDTO(articleDTO);
        Article returnedArticle = articleRepository.updateObject(article);
        return ArticleConverter.getDTOFromObject(returnedArticle);
    }
}
