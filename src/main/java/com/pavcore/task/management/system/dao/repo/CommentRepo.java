package com.pavcore.task.management.system.dao.repo;

import com.pavcore.task.management.system.dao.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
