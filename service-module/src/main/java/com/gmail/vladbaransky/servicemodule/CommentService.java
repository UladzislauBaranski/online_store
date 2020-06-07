package com.gmail.vladbaransky.servicemodule;

import java.util.List;

public interface CommentService {

    List<Integer> deleteCommentsByIdList(List<Long> id);
}

