package com.gmail.vladbaransky.servicemodule;

import com.gmail.vladbaransky.repositorymodule.CommentRepository;
import com.gmail.vladbaransky.servicemodule.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.gmail.vladbaransky.servicemodule.util.TestValue.getIdList;
import static com.gmail.vladbaransky.servicemodule.util.TestValue.getStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {
        this.commentService = new CommentServiceImpl(commentRepository);
    }

    @Test
    public void deleteCommentsByIdList_returnStatus() {
        List<Long> idList = getIdList();
        List<Integer> status =getStatus();

        when(commentRepository.deleteObjectByIdList(idList)).thenReturn(status);
        List<Integer> returnedStatus = commentService.deleteCommentsByIdList(idList);
        verify(commentRepository, times(1)).deleteObjectByIdList(idList);

        assertThat(returnedStatus).isNotNull();
        assertThat(returnedStatus).isEqualTo(status);
    }
}
