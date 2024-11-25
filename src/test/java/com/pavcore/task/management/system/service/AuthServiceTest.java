package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.config.JwtUtil;
import com.pavcore.task.management.system.dto.request.UserRequestTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    // Тест 1: Успешная аутентификация
    @Test
    public void testAuthenticate_Successful() {
        String email = "user@example.com";
        String password = "securePassword";
        String expectedToken = "jwtToken";

        UserRequestTO userRequestTO = new UserRequestTO();
        userRequestTO.setEmail(email);
        userRequestTO.setPassword(password);

        when(jwtUtil.generateToken(email)).thenReturn(expectedToken);

        String actualToken = authService.authenticate(userRequestTO);

        assertEquals(expectedToken, actualToken);
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        verify(jwtUtil, times(1)).generateToken(email);
    }

    // Тест 2: Неуспешная аутентификация с неверными учетными данными
    @Test
    public void testAuthenticate_InvalidCredentials() {
        String email = "user@example.com";
        String password = "wrongPassword";

        UserRequestTO userRequestTO = new UserRequestTO();
        userRequestTO.setEmail(email);
        userRequestTO.setPassword(password);

        doThrow(new BadCredentialsException("Invalid credentials"))
                .when(authenticationManager)
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> authService.authenticate(userRequestTO));

        assertEquals("Invalid credentials", exception.getMessage());
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        verify(jwtUtil, times(0)).generateToken(anyString());
    }
}
