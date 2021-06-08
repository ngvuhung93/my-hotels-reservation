package com.epam.hotelreservation.authenticationservice.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.hotelreservation.authenticationservice.request.LoginRequest;
import com.epam.hotelreservation.authenticationservice.request.RegisterRequest;
import com.epam.hotelreservation.authenticationservice.response.AuthResponse;
import com.epam.hotelreservation.authenticationservice.service.AuthenticationService;
import com.epam.hotelreservation.authenticationservice.utility.TestUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
class AuthenticationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthenticationService authenticationService;

  @Test
  void testRegisterNewGuest_SuccessCase() throws Exception {
    var mockResponse = initMockResponse();
    var mockRegisterRequest = initMockRegisterRequest();
    when(authenticationService.register(mockRegisterRequest)).thenReturn(mockResponse);
    mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON).content(TestUtility.asJsonString(mockRegisterRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken", notNullValue()))
        .andExpect(jsonPath("$.refreshToken", notNullValue()));
    verify(authenticationService, times(1)).register(mockRegisterRequest);
  }

  @Test
  void testLoginGuest_SuccessCase() throws Exception {
    var mockResponse = initMockResponse();
    var mockLoginRequest = initMockLoginRequest();
    when(authenticationService.login(mockLoginRequest)).thenReturn(mockResponse);
    mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON).content(TestUtility.asJsonString(mockLoginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken", notNullValue()))
        .andExpect(jsonPath("$.refreshToken", notNullValue()));
    verify(authenticationService, times(1)).login(mockLoginRequest);
  }

  private AuthResponse initMockResponse() {
    return AuthResponse.builder()
        .accessToken("This is the access token")
        .refreshToken("This is the refresh token")
        .build();
  }

  RegisterRequest initMockRegisterRequest() {
    return RegisterRequest.builder()
        .username("dat1234")
        .password("123456789")
        .guestName("Dat Nguyen")
        .address("53 Vietnam")
        .phoneNumber("0123456789")
        .build();
  }

  LoginRequest initMockLoginRequest() {
    return LoginRequest.builder()
        .password("dat1234")
        .password("123456789")
        .build();
  }

}
