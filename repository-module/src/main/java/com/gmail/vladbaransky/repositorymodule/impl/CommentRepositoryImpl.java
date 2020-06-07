package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.CommentRepository;
import com.gmail.vladbaransky.repositorymodule.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepositoryImpl extends GenericDaoRepositoryImpl<Long, Comment> implements CommentRepository {

    @Override
    public List<Integer> deleteChildObjectByParentId(List<Long> ids) {
        List<Integer> list = new ArrayList<>();
        for (Long id : ids) {
            String hql = "DELETE FROM " + entityClass.getSimpleName() + " c WHERE c.article.id=:id";
            Query query = entityManager.createQuery(hql);
            query.setParameter("id", id);
            list.add(query.executeUpdate());
        }
        return list;
    }
}
