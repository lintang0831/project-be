package com.projetct_be.project_be.controller;


import com.projetct_be.project_be.model.LoginRequest;
import com.projetct_be.project_be.securityNew.JwtTokenUtil;
import com.projetct_be.project_be.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")  // Sesuaikan endpoint untuk admin
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {  // Ganti LoginRequest menjadi AdminLoginRequest
        try {
            Map<String, Object> response = authService.authenticate(loginRequest);  // Ganti authenticate menjadi authenticateAdmin
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error during login: ", e);
            return new ResponseEntity<>("Server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
