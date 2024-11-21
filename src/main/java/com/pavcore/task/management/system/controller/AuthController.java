package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dto.request.UserRequestTO;
import com.pavcore.task.management.system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody UserRequestTO userRequestTO) {
        String token = authService.authenticate(userRequestTO);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
