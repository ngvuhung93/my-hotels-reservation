package com.epam.hotelreservation.authenticationservice.controller;

import com.epam.hotelreservation.authenticationservice.request.LoginRequest;
import com.epam.hotelreservation.authenticationservice.request.RegisterRequest;
import com.epam.hotelreservation.authenticationservice.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {

  @PostMapping(value = "/register")
  ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest);

  @PostMapping(value = "/login")
  ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest);
}
