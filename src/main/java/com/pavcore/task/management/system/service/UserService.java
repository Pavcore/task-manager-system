package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dao.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUserById(long id) {
        return userRepo.getReferenceById(id);
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
