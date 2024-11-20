package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dto.request.UserRequestTO;
import com.pavcore.task.management.system.dto.response.UserResponseTO;
import com.pavcore.task.management.system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<UserResponseTO> authenticate(@RequestBody UserRequestTO userRequestTO) {
        UserResponseTO userResponseTO = authService.authenticate(userRequestTO);
        if (userResponseTO == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userResponseTO);
    }
}
