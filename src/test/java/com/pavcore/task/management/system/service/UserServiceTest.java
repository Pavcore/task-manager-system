package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.config.JwtUtil;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dao.repo.UserRepo;
import com.pavcore.task.management.system.exception.NotFoundUserException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById_UserExists() {
        long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        long userId = 1L;
        when(userRepo.findById(userId)).thenThrow(new NotFoundUserException("User not found"));

        NotFoundUserException exception = assertThrows(NotFoundUserException.class, () -> userService.getUserById(userId));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testGetUserByEmail_UserExists() {
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserByEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testGetUserByEmail_UserNotFound() {
        String email = "test@example.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByEmail(email));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testGetUserByToken_ValidToken_UserExists() {
        String token = "validToken";
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        when(jwtUtil.getJWTFromRequest(mockRequest)).thenReturn(token);
        when(jwtUtil.getEmailFromToken(token)).thenReturn(email);
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserByToken(mockRequest);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void testGetUserByToken_UserNotFound() {
        String token = "validToken";
        String email = "test@example.com";

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);

        when(jwtUtil.getJWTFromRequest(mockRequest)).thenReturn(token);
        when(jwtUtil.getEmailFromToken(token)).thenReturn(email);
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByToken(mockRequest));

        assertEquals("User not found", exception.getMessage());
    }
}
