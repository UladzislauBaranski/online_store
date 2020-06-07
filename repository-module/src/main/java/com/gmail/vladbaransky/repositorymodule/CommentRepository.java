package com.gmail.vladbaransky.repositorymodule;

import com.gmail.vladbaransky.repositorymodule.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends GenericDaoRepository<Long, Comment> {

    List<Integer> deleteChildObjectByParentId(List<Long> ids);
}
