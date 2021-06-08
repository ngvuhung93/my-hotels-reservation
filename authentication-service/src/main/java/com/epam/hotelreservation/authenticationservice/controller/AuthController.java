package com.epam.hotelreservation.authenticationservice.controller;


import com.epam.hotelreservation.authenticationservice.request.RegisterRequest;
import com.epam.hotelreservation.authenticationservice.request.LoginRequest;
import com.epam.hotelreservation.authenticationservice.response.AuthResponse;
import com.epam.hotelreservation.authenticationservice.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(
        AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        log.info("User register request: {}", registerRequest);
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("User login request: {}", loginRequest);
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

}
