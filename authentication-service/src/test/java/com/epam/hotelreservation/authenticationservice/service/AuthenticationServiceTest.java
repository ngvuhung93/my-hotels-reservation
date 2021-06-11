package com.epam.hotelreservation.authenticationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.epam.hotelreservation.authenticationservice.entity.GuestEntity;
import com.epam.hotelreservation.authenticationservice.exception.AuthenticationServiceException;
import com.epam.hotelreservation.authenticationservice.request.LoginRequest;
import com.epam.hotelreservation.authenticationservice.request.RegisterRequest;
import com.epam.hotelreservation.authenticationservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

  private AuthenticationService authenticationService;
  private RestTemplate restTemplate;
  private JwtUtil jwtUtil;

  @Value("${api.guest-service.url}")
  private String url;

  @BeforeAll
  public void setup() {
    restTemplate = mock(RestTemplate.class);
    jwtUtil = mock(JwtUtil.class);
    authenticationService = new AuthenticationServiceImpl(restTemplate, jwtUtil);
  }

  @Test
  void shouldReturnToken_WhenRegister() {
    var mockRegisterRequest = initMockRegisterRequest();
    var mockRegisteredGuest = initMockGuestEntity();
    var mockAccessToken = "ey1234567890123456789";
    var mockRefreshToken = "ey1234567899999999999";

    var response = ResponseEntity.ok(mockRegisteredGuest);

    when(restTemplate.postForEntity("http://guest-service/guests", mockRegisterRequest, GuestEntity.class))
        .thenReturn(response);
    when(jwtUtil.generate(mockRegisteredGuest,"ACCESS")).thenReturn(mockAccessToken);
    when(jwtUtil.generate(mockRegisteredGuest,"REFRESH")).thenReturn(mockRefreshToken);

    var tokenResponse = authenticationService.register(mockRegisterRequest);
    assertEquals(mockAccessToken, tokenResponse.getAccessToken());
    assertEquals(mockRefreshToken, tokenResponse.getRefreshToken());
  }

  @Test
  void shouldReturnToken_WhenLogin() {
    var mockLoginRequest = initMockLoginRequest();
    var mockRegisteredGuest = initMockGuestEntity();
    var mockAccessToken = "ey1234567890123456789";
    var mockRefreshToken = "ey1234567899999999999";

    var response = ResponseEntity.ok(mockRegisteredGuest);

    var headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    var entity = new HttpEntity<>(headers);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://guest-service/guests")
        .queryParam("username", mockLoginRequest.getUsername());

    when(restTemplate.exchange(
        builder.toUriString(),
        HttpMethod.GET,
        entity,
        GuestEntity.class)).thenReturn(response);

    mockRegisteredGuest.setPassword(BCrypt.hashpw(mockRegisteredGuest.getPassword(), mockRegisteredGuest.getSalt()));

    when(jwtUtil.generate(mockRegisteredGuest,"ACCESS")).thenReturn(mockAccessToken);
    when(jwtUtil.generate(mockRegisteredGuest,"REFRESH")).thenReturn(mockRefreshToken);

    var tokenResponse = authenticationService.login(mockLoginRequest);
    assertEquals(mockAccessToken, tokenResponse.getAccessToken());
    assertEquals(mockRefreshToken, tokenResponse.getRefreshToken());
  }

  @Test
  void shouldReturnException_WhenReceivedIncorrectPassword() {
    var mockLoginRequest = initMockLoginRequest();
    var mockRegisteredGuest = initMockGuestEntity();
    var response = ResponseEntity.ok(mockRegisteredGuest);

    var headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    var entity = new HttpEntity<>(headers);

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://guest-service/guests")
        .queryParam("username", mockLoginRequest.getUsername());

    when(restTemplate.exchange(
        builder.toUriString(),
        HttpMethod.GET,
        entity,
        GuestEntity.class)).thenReturn(response);

    assertThrows(AuthenticationServiceException.class, () -> authenticationService.login(mockLoginRequest));
  }

  private GuestEntity initMockGuestEntity() {
    return GuestEntity.builder()
        .guestName("Dat")
        .address("53 Wall Str.")
        .phoneNumber("0123456789")
        .password("123456789")
        .salt("$2a$10$llw0G6IyibUob8h5XRt9xuRczaGdCm/AiV6SSjf5v78XS824EGbh.")
        .build();
  }

  private LoginRequest initMockLoginRequest() {
    return LoginRequest.builder()
        .username("dat1234")
        .password("123456789")
        .build();
  }

  private RegisterRequest initMockRegisterRequest() {
    return RegisterRequest.builder()
        .guestName("Dat")
        .username("dat1234")
        .password("123456789")
        .phoneNumber("0123456789")
        .address("53 Wall Str.")
        .build();
  }
}
