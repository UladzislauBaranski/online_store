package com.gmail.vladbaransky.servicemodule.impl;

import com.gmail.vladbaransky.repositorymodule.CommentRepository;
import com.gmail.vladbaransky.servicemodule.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    @Override
    public List<Integer> deleteCommentsByIdList(List<Long> id) {
        return commentRepository.deleteObjectByIdList(id);
    }
}
