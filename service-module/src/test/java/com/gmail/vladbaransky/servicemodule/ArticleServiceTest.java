
package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.ArticleRepository;
import com.gmail.vladbaransky.repositorymodule.CommentRepository;
import com.gmail.vladbaransky.repositorymodule.model.Article;
import com.gmail.vladbaransky.servicemodule.impl.ArticleServiceImpl;
import com.gmail.vladbaransky.servicemodule.model.ArticleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.vladbaransky.servicemodule.util.TestValue.getIdList;
import static com.gmail.vladbaransky.servicemodule.util.TestValue.getStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        this.articleService = new ArticleServiceImpl(articleRepository, commentRepository);
    }

    @Test
    public void getArticlesByPage_returnArticles() {
        List<Article> articles = getAddedArticleList();

        when(articleRepository.getObjectByPage(0, 10)).thenReturn(articles);
        List<ArticleDTO> returnedArticles = articleService.getArticlesByPage(1);
        verify(articleRepository, times(1)).getObjectByPage(0, 10);
        for (int i = 0; i < returnedArticles.size(); i++) {
            getAssertion(returnedArticles.get(i), articles.get(i));
        }
    }

    @Test
    public void getAllArticles_returnArticles() {
        List<Article> articles = getAddedArticleList();

        when(articleRepository.getAllObject()).thenReturn(articles);
        List<ArticleDTO> returnedArticles = articleService.getAllArticles();
        verify(articleRepository, times(1)).getAllObject();
        assertThat(returnedArticles).isNotNull();
        for (int i = 0; i < returnedArticles.size(); i++) {
            getAssertion(returnedArticles.get(i), articles.get(i));
        }
    }

    @Test
    public void getArticleById_returnArticle() {
        Long id = 1L;
        Article article = getAddedArticle();
        when(articleRepository.getObjectById(id)).thenReturn(article);
        ArticleDTO returnedArticle = articleService.getArticleById(id);
        verify(articleRepository, times(1)).getObjectById(id);

        getAssertion(returnedArticle, article);
    }

    @Test
    public void addArticle_returnArticle() {
        Article article = getArticle();
        Article addedArticle = getAddedArticle();
        ArticleDTO articleDTO = getArticleDTO();

        when(articleRepository.addObject(article)).thenReturn(addedArticle);
        ArticleDTO returnedArticle = articleService.addArticle(articleDTO);
        verify(articleRepository, times(1)).addObject(article);

        getAssertion(returnedArticle, addedArticle);
    }

    @Test
    public void deleteArticleById_returnStatus() {
        Long id = 1L;
        Integer status = 1;
        when(articleRepository.deleteObjectById(id)).thenReturn(status);
        Integer returnedStatus = articleService.deleteArticleById(id);
        verify(articleRepository, times(1)).deleteObjectById(id);
        assertThat(returnedStatus).isNotNull();
        assertThat(returnedStatus).isEqualTo(status);
    }

    @Test
    public void deleteArticleByIdList_returnStatus() {
        List<Long> idList = getIdList();
        List<Integer> status = getStatus();

        when(articleRepository.deleteObjectByIdList(idList)).thenReturn(status);
        List<Integer> returnedStatus = articleService.deleteArticlesByIdList(idList);
        verify(articleRepository, times(1)).deleteObjectByIdList(idList);
        assertThat(returnedStatus).isNotNull();
        assertThat(returnedStatus).isEqualTo(status);
    }

    @Test
    public void updateArticle_returnArticle() {
        Article article = getArticle();
        Article addedArticle = getAddedArticle();
        ArticleDTO articleDTO = getArticleDTO();

        when(articleRepository.updateObject(article)).thenReturn(addedArticle);
        ArticleDTO returnedArticleDTO = articleService.updateArticle(articleDTO);
        verify(articleRepository, times(1)).updateObject(article);

        getAssertion(returnedArticleDTO, addedArticle);
    }

    private void getAssertion(ArticleDTO returnedArticle, Article article) {
        assertThat(returnedArticle).isNotNull();
        assertThat(returnedArticle.getId()).isEqualTo(article.getId());
        assertThat(returnedArticle.getDate()).isEqualTo(article.getDate());
        assertThat(returnedArticle.getTitle()).isEqualTo(article.getTitle());
        assertThat(returnedArticle.getSummary()).isEqualTo(article.getSummary());
        assertThat(returnedArticle.getContent()).isEqualTo(article.getContent());
    }

    private List<Article> getAddedArticleList() {
        List<Article> articles = new ArrayList<>();
        for (Long i = 1L; i <= 10L; i++) {
            Article article = getArticle();
            article.setId(i);
            articles.add(article);
        }
        return articles;
    }

    private Article getArticle() {
        Article article = new Article();
        article.setDate(LocalDate.now());
        article.setTitle("title");
        article.setSummary("summary");
        article.setContent("content");
        return article;
    }

    private Article getAddedArticle() {
        Article article = new Article();
        article.setId(1L);
        article.setDate(LocalDate.now());
        article.setTitle("title");
        article.setSummary("summary");
        article.setContent("content");
        return article;
    }

    private ArticleDTO getArticleDTO() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setDate(LocalDate.now());
        articleDTO.setTitle("title");
        articleDTO.setSummary("summary");
        articleDTO.setContent("content");
        return articleDTO;
    }
}

