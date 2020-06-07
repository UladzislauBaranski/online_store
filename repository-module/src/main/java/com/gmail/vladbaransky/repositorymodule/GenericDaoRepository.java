package com.gmail.vladbaransky.repositorymodule;

import java.util.List;

public interface GenericDaoRepository<I, T> {

    List<T> getAllObject();

    T addObject(T entity);

    T delete(T entity);

    List<T> getObjectByPage(int startPosition, int objectByPage);

    List<Integer> deleteObjectByIdList(List<I> ids);

    T getObjectById(I id);

    Integer deleteObjectById(I id);

    T updateObject(T entity);


}
