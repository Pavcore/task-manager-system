package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.Comment;
import com.pavcore.task.management.system.dao.repo.CommentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepo commentRepo;

    @InjectMocks
    private CommentService commentService;

    // Тест 1: Проверка успешного сохранения комментария
    @Test
    public void testSave_ValidComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test comment");

        commentService.save(comment);

        verify(commentRepo, times(1)).save(comment);
    }

    // Тест 2: Проверка поведения при передаче null
    @Test
    public void testSave_NullComment() {
        Comment comment = null;

        commentService.save(comment);

        verify(commentRepo, times(0)).save(any(Comment.class));
    }
}
