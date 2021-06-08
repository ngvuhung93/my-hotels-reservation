package com.epam.hotelreservation.authenticationservice.service;

import com.epam.hotelreservation.authenticationservice.request.LoginRequest;
import com.epam.hotelreservation.authenticationservice.request.RegisterRequest;
import com.epam.hotelreservation.authenticationservice.response.AuthResponse;

public interface AuthenticationService {
  AuthResponse register(RegisterRequest registerRequest);
  AuthResponse login(LoginRequest loginRequest);
}
