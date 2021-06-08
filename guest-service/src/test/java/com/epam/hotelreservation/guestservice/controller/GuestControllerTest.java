package com.epam.hotelreservation.guestservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.hotelreservation.guestservice.dto.GuestAccountDto;
import com.epam.hotelreservation.guestservice.dto.GuestDto;
import com.epam.hotelreservation.guestservice.exception.GuestErrorResponse;
import com.epam.hotelreservation.guestservice.exception.GuestExceptionConverter;
import com.epam.hotelreservation.guestservice.exception.GuestServiceException;
import com.epam.hotelreservation.guestservice.request.CreateGuestRequest;
import com.epam.hotelreservation.guestservice.request.UpdateGuestRequest;
import com.epam.hotelreservation.guestservice.service.GuestService;
import com.epam.hotelreservation.guestservice.utility.TestUtility;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HotelGuestControllerImpl.class)
class GuestControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private GuestService guestService;

  private final int GUEST_ID = 1;
  private final int NOT_EXISTED_ID = -1;

  @Test
  void testGetGuestById_SuccessCase() throws Exception {
    var foundGuest = initMockGuest();
    when(guestService.findGuestById(GUEST_ID)).thenReturn(foundGuest);

    mvc.perform(get("/guests/" + GUEST_ID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(TestUtility.asJsonString(foundGuest)));
    verify(guestService, times(1)).findGuestById(GUEST_ID);
  }

  @Test
  void testGetGuestById_FailedCase() throws Exception {

    when(guestService.findGuestById(NOT_EXISTED_ID)).thenThrow(new GuestServiceException(
        GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION));
    var converter = new GuestExceptionConverter();
    var jsonStringResponse = converter
        .toJsonNode(GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION, StringUtils.EMPTY).toString();

    mvc.perform(get("/guests/" + NOT_EXISTED_ID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(jsonStringResponse));
    verify(guestService, times(1)).findGuestById(NOT_EXISTED_ID);

  }

  @Test
  void testGetGuestByUsername_SuccessCase() throws Exception {
    var mockAccount = initMockAccount();
    when(guestService.findGuestByUsername(mockAccount.getUsername())).thenReturn(mockAccount);

    mvc.perform(get("/guests/").contentType(MediaType.APPLICATION_JSON)
        .param("username", mockAccount.getUsername()))
        .andExpect(status().isOk())
        .andExpect(content().string(TestUtility.asJsonString(mockAccount)));
    verify(guestService, times(1)).findGuestByUsername(mockAccount.getUsername());
  }

  @Test
  void testGetGuestByUsername_FailedCase() throws Exception {

    String nonExistedUsername = "Non Existed Username";
    when(guestService.findGuestByUsername(nonExistedUsername)).thenThrow(new GuestServiceException(
        GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION));
    var converter = new GuestExceptionConverter();
    var jsonStringResponse = converter
        .toJsonNode(GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION, StringUtils.EMPTY).toString();

    mvc.perform(get("/guests/").contentType(MediaType.APPLICATION_JSON)
        .param("username", nonExistedUsername))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(jsonStringResponse));
    verify(guestService, times(1)).findGuestByUsername(nonExistedUsername);
  }

  @Test
  void testCreateNewGuest_SuccessCase() throws Exception {
    var mockAccount = initMockAccount();
    var mockCreateRequest = initMockCreateRequest();


    when(guestService.createAccount(mockCreateRequest)).thenReturn(mockAccount);

    mvc.perform(post("/guests/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtility.asJsonString(mockCreateRequest)))
        .andExpect(content().string(TestUtility.asJsonString(mockAccount)))
        .andExpect(status().isOk());
    verify(guestService, times(1)).createAccount(mockCreateRequest);
  }

  @Test
  void testUpdateGuestById_SuccessCase() throws Exception {
    var savedGuest = initMockGuest();
    var updateRequest = initMockUpdateRequest();

    savedGuest.setGuestName(updateRequest.getName());
    savedGuest.setAddress(updateRequest.getAddress());
    savedGuest.setPhoneNumber(updateRequest.getPhone());

    when(guestService.updateGuest(GUEST_ID, updateRequest)).thenReturn(savedGuest);

    mvc.perform(put("/guests/" + GUEST_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtility.asJsonString(updateRequest)))
        .andExpect(content().string(TestUtility.asJsonString(savedGuest)))
        .andExpect(status().isOk());
    verify(guestService, times(1)).updateGuest(GUEST_ID, updateRequest);
  }

  @Test
  void testUpdateGuestById_FailedCase() throws Exception {
    var updateRequest = initMockUpdateRequest();
    var guestServiceException = new GuestServiceException(
        GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION);
    var converter = new GuestExceptionConverter();
    var jsonStringResponse = converter
        .toJsonNode(guestServiceException.getGuestErrorResponse(), StringUtils.EMPTY).toString();

    when(guestService.updateGuest(NOT_EXISTED_ID, updateRequest)).thenThrow(guestServiceException);

    mvc.perform(put("/guests/" + NOT_EXISTED_ID)
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtility.asJsonString(updateRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(jsonStringResponse));
    verify(guestService, times(1)).updateGuest(NOT_EXISTED_ID, updateRequest);
  }

  private GuestDto initMockGuest() {
    return GuestDto.builder()
        .guestName("Dat")
        .address("53 Pasteur, Ho Chi Minh City")
        .phoneNumber("012345678")
        .username("steven328")
        .id(GUEST_ID)
        .build();
  }

  private UpdateGuestRequest initMockUpdateRequest() {
    return UpdateGuestRequest.builder()
        .name("Dat Nguyen")
        .phone("0121749223")
        .address("67 Jump Str., New York")
        .build();
  }

  private GuestAccountDto initMockAccount() {
    return GuestAccountDto.builder()
        .username("dat1234")
        .password("hashedPassword")
        .salt("Need some salt")
        .guestName("Dat")
        .address("53 Pasteur, Ho Chi Minh City")
        .phoneNumber("012345678")
        .build();
  }

  private CreateGuestRequest initMockCreateRequest() {
    return CreateGuestRequest.builder()
        .username("dat1234")
        .password("092131233")
        .salt("!@#$%^&*()")
        .address("53 Pasteur, Ho Chi Minh City")
        .phoneNumber("012345678")
        .guestName("Dat")
        .build();
  }


}
