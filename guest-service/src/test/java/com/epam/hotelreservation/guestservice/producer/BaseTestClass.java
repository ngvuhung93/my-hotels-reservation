package com.epam.hotelreservation.guestservice.producer;

import com.epam.hotelreservation.guestservice.controller.HotelGuestController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMessageVerifier
public class BaseTestClass {

  @Autowired
  private HotelGuestController hotelGuestController;

  @BeforeAll
  public void setup() {
    StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(hotelGuestController);
    RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
  }
}
