package com.gmail.vladbaransky.repositorymodule;

import com.gmail.vladbaransky.repositorymodule.model.Item;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ItemRepository extends GenericDaoRepository<Long, Item> {
}
