package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.Comment;
import com.pavcore.task.management.system.dao.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public void save(Comment comment) {
        commentRepo.save(comment);
    }
}
