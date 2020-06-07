package com.gmail.vladbaransky.repositorymodule.impl;

import com.gmail.vladbaransky.repositorymodule.UserRepository;
import com.gmail.vladbaransky.repositorymodule.model.RoleEnum;
import com.gmail.vladbaransky.repositorymodule.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl extends GenericDaoRepositoryImpl<Long, User> implements UserRepository {
    @Override
    public List<User> getObjectByPage(int startPosition, int objectByPage) {
        String hql = "FROM " + entityClass.getSimpleName() + " u ORDER BY u.username ASC";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(startPosition);
        query.setMaxResults(objectByPage);
        return query.getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        String queryString = "FROM " + entityClass.getSimpleName() +
                " u WHERE u.username =:name";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", username);
        return (User) query.getSingleResult();
    }

    @Override
    public Integer updatePasswordById(Long id, String password) {
        String hql = "UPDATE " + entityClass.getSimpleName() + " i SET i.password=:password WHERE i.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("password", password);
        return query.executeUpdate();
    }

    @Override
    public Integer updateRoleById(Long id, RoleEnum role) {
        String hql = "UPDATE " + entityClass.getSimpleName() + " i SET i.role=:role WHERE i.id=:id";
        Query query = entityManager.createQuery(hql);
        query.setParameter("id", id);
        query.setParameter("role", role);
        return query.executeUpdate();
    }


}
