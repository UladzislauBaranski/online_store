package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.OrderRepository;
import com.gmail.vladbaransky.repositorymodule.model.Order;
import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl extends GenericDaoRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public List<Integer> updateStatusById(List<Long> ids, StatusEnum status) {
        List<Integer> list = new ArrayList<>();
        for (Long id : ids) {
            String hql = "UPDATE " + entityClass.getSimpleName() + " o SET o.status=:status WHERE o.id=:id";
            Query query = entityManager.createQuery(hql);
            query.setParameter("id", id);
            query.setParameter("status", status);
            list.add(query.executeUpdate());
        }
        return list;
    }
}
