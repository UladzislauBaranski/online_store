package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.servicemodule.model.ArticleDTO;

import java.util.List;

public interface ArticleService {

    List<ArticleDTO> getArticlesByPage(int page);

    List<ArticleDTO> getAllArticles();

    ArticleDTO getArticleById(Long id);

    ArticleDTO addArticle(ArticleDTO article);

    Integer deleteArticleById(Long id);

    List<Integer> deleteArticlesByIdList(List<Long> ids);

    ArticleDTO updateArticle(ArticleDTO article);
}
