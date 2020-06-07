package com.gmail.vladbaransky.repositorymodule;

import com.gmail.vladbaransky.repositorymodule.model.Order;
import com.gmail.vladbaransky.repositorymodule.model.StatusEnum;

import java.util.List;

public interface OrderRepository extends GenericDaoRepository<Long, Order> {

    List<Integer> updateStatusById(List<Long> ids, StatusEnum status);
}
