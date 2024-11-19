package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dto.request.UserRequestTO;
import com.pavcore.task.management.system.dto.response.UserResponseTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping
    public ResponseEntity<UserResponseTO> authenticate(@RequestBody UserRequestTO userRequestTO) {
        return ResponseEntity.ok(new UserResponseTO());
    }
}
