package com.epam.hotelreservation.authenticationservice.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.hotelreservation.authenticationservice.request.LoginRequest;
import com.epam.hotelreservation.authenticationservice.request.RegisterRequest;
import com.epam.hotelreservation.authenticationservice.utility.TestUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
    ids = "com.epam.hotelreservation:guest-service:+:stubs:8081")
class AuthenticationControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldReturnToken_WhenRegister() throws Exception {
    var mockRegisterRequest = initMockRegisterRequest();
    mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON).content(TestUtility.asJsonString(mockRegisterRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken", notNullValue()))
        .andExpect(jsonPath("$.refreshToken", notNullValue()));
  }

  @Test
  void shouldReturnToken_WhenLogin() throws Exception {
    var mockLoginRequest = initMockLoginRequest();
    mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON).content(TestUtility.asJsonString(mockLoginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken", notNullValue()))
        .andExpect(jsonPath("$.refreshToken", notNullValue()));
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
        .username("dat1234")
        .password("123456789")
        .build();
  }

}
