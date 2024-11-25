package com.pavcore.task.management.system.dao.repo;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> getTasksByAuthor(User author, Pageable pageable);

    List<Task> getTasksByPerformer(User user, Pageable pageable);
}
