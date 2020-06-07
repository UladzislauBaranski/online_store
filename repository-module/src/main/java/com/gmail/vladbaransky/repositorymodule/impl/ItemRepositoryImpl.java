package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.ItemRepository;
import com.gmail.vladbaransky.repositorymodule.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericDaoRepositoryImpl<Long, Item> implements ItemRepository {
    @Override
    public List<Item> getObjectByPage(int startPosition, int objectByPage) {
        String hql = "FROM " + entityClass.getSimpleName() + " i ORDER BY i.title ASC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(objectByPage);
        return query.getResultList();
    }
}
