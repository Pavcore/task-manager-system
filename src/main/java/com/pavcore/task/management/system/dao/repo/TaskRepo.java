package com.pavcore.task.management.system.dao.repo;

import com.pavcore.task.management.system.dao.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
