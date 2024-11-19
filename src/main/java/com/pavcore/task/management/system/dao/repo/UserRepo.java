package com.pavcore.task.management.system.dao.repo;

import com.pavcore.task.management.system.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
