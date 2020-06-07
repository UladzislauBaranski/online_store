package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.GenericDaoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class GenericDaoRepositoryImpl<I, T> implements GenericDaoRepository<I, T> {

    protected Class<T> entityClass;
    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDaoRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];
    }

    @Override
    public List<T> getAllObject() {
        String query = "from " + entityClass.getName();
        Query q = entityManager.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<T> getObjectByPage(int startPosition, int objectByPage) {
        String hql = "FROM " + entityClass.getSimpleName();
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(objectByPage);
        return query.getResultList();
    }

    @Override
    public T getObjectById(I id) {
        String hql = "FROM " + entityClass.getSimpleName() + " i WHERE i.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return (T) query.getSingleResult();
    }

    @Override
    public T addObject(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T updateObject(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public T delete(T entity) {
        entityManager.remove(entity);
        return entity;
    }

    @Override
    public List<Integer> deleteObjectByIdList(List<I> ids) {
        List<Integer> list = new ArrayList<>();
        for (I id : ids) {
            String hql = "DELETE FROM " + entityClass.getSimpleName() + " u WHERE u.id=:id";
            Query query = entityManager.createQuery(hql);
            query.setParameter("id", id);
            Integer result = query.executeUpdate();
            list.add(result);
        }
        return list;
    }

    @Override
    public Integer deleteObjectById(I id) {
        String hql = "DELETE FROM " + entityClass.getSimpleName() + " i WHERE i.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}