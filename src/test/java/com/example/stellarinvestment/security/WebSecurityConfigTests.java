package com.example.stellarinvestment.security;

import com.example.stellarinvestment.repository.UserRepository;
import com.example.stellarinvestment.service.AuthUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebSecurityConfigTests {

    @Test
    void passwordEncoder() {
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        PasswordEncoder passwordEncoder = webSecurityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

    @Test
    void configure() throws Exception {
        // Arrange
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        HttpSecurity http = mock(HttpSecurity.class);

        // Act
        webSecurityConfig.configure(http);

        // Assert
        verify(http).authorizeRequests();
        verify(http).authorizeRequests();
        verify(http).authorizeRequests().antMatchers("/customer").authenticated();
        verify(http).authorizeRequests().anyRequest().permitAll();
    }

    @Test
    void testConfigure() throws Exception {
        // Arrange
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        WebSecurity web = mock(WebSecurity.class);

        // Act
        webSecurityConfig.configure(web);

        // Assert
        verify(web).ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

    @Test
    void authenticationProvider() {
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        DaoAuthenticationProvider result = webSecurityConfig.authenticationProvider();
        assertNotNull(result);
        assertTrue(result instanceof DaoAuthenticationProvider);
    }
}
