package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.config.JwtUtil;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dao.repo.UserRepo;
import com.pavcore.task.management.system.exception.NotFoundPerformerException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepo userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    public User getUserById(long id) {
        try {
            return userRepo.getReferenceById(id);
        } catch (NotFoundPerformerException e) {
            throw new NotFoundPerformerException(e.getMessage());
        }
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserByToken(HttpServletRequest request) {
        String token = jwtUtil.getJWTFromRequest(request);
        String email = jwtUtil.getEmailFromToken(token);
        return getUserByEmail(email);
    }
}
