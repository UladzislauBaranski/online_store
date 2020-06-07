package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.ArticleRepository;
import com.gmail.vladbaransky.repositorymodule.model.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class  ArticleRepositoryImpl extends GenericDaoRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    public List<Article> getObjectByPage(int startPosition, int objectByPage) {
        String hql = "FROM " + entityClass.getSimpleName() + " a ORDER BY a.date DESC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(objectByPage);
        return query.getResultList();
    }
}
