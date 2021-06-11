package com.epam.hotelreservation.authenticationservice.service;

import com.epam.hotelreservation.authenticationservice.entity.GuestEntity;
import com.epam.hotelreservation.authenticationservice.exception.AuthenticationErrorResponse;
import com.epam.hotelreservation.authenticationservice.exception.AuthenticationServiceException;
import com.epam.hotelreservation.authenticationservice.request.RegisterRequest;
import com.epam.hotelreservation.authenticationservice.request.LoginRequest;
import com.epam.hotelreservation.authenticationservice.response.AuthResponse;
import com.epam.hotelreservation.authenticationservice.util.JwtUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService{

  private final RestTemplate restTemplate;
  private final JwtUtil jwt;

  private final String url = "http://guest-service/guests";

  @Autowired
  public AuthenticationServiceImpl(RestTemplate restTemplate,
      final JwtUtil jwt) {
    this.restTemplate = restTemplate;
    this.jwt = jwt;
  }

  @Override
  @CircuitBreaker(name = "registerBreak", fallbackMethod = "registerFallBack")
  public AuthResponse register(RegisterRequest registerRequest) {
    var salt = BCrypt.gensalt();

    registerRequest.setPassword(BCrypt.hashpw(registerRequest.getPassword(),salt));
    registerRequest.setSalt(salt);

    var response = restTemplate.postForEntity(url, registerRequest, GuestEntity.class);
    var guestEntity = response.getBody();

    Assert.notNull(guestEntity, "Failed to register user. Please try again later");

    return AuthResponse.builder()
        .accessToken(jwt.generate(guestEntity, "ACCESS"))
        .refreshToken(jwt.generate(guestEntity, "REFRESH"))
        .build();
  }

  @Override
  @CircuitBreaker(name = "loginFallBack", fallbackMethod = "loginFallBack")
  public AuthResponse login(LoginRequest loginRequest) {

    var headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    var entity = new HttpEntity<>(headers);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("username", loginRequest.getUsername());

    HttpEntity<GuestEntity> response = restTemplate.exchange(
        builder.toUriString(),
        HttpMethod.GET,
        entity,
        GuestEntity.class);

    var guestEntity = response.getBody();

    Assert.notNull(guestEntity, "Failed to login user. Please try again later");
    validatePassword(loginRequest.getPassword(), guestEntity.getPassword(), guestEntity.getSalt());

    return AuthResponse.builder()
        .accessToken(jwt.generate(guestEntity, "ACCESS"))
        .refreshToken(jwt.generate(guestEntity, "REFRESH"))
        .build();
  }

  private void validatePassword(String receivedPassword, String hashedPassword, String salt) {
    var hashedReceivedPassword = BCrypt.hashpw(receivedPassword, salt);
    if(!hashedReceivedPassword.equals(hashedPassword)){
      throw new AuthenticationServiceException(
          AuthenticationErrorResponse.INCORRECT_PASSWORD_EXCEPTION);
    }
  }

  public AuthResponse loginFallBack(LoginRequest loginRequest, Exception t) {
    log.error("Can't login with {}, {}", loginRequest, t.getMessage());
    return null;
  }

  public AuthResponse registerFallBack(RegisterRequest registerRequest, Exception t) {
    log.error("Can't register with {}, {}", registerRequest.getUsername(), t.getMessage());
    return null;
  }

}
