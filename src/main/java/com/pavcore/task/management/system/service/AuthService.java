package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dto.request.UserRequestTO;
import com.pavcore.task.management.system.dto.response.UserResponseTO;
import com.pavcore.task.management.system.mapper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ResponseMapper responseMapper;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder, ResponseMapper responseMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.responseMapper = responseMapper;
    }

    public UserResponseTO authenticate(UserRequestTO userRequestTO) {
        if (userService.getUserByEmail(userRequestTO.getEmail()) == null) {
            return null;
        }
        User user = userService.getUserByEmail(userRequestTO.getEmail());
        if (passwordEncoder.matches(userRequestTO.getPassword(), user.getPassword())) {
            return responseMapper.map(user);
        } else return null;
    }
}
