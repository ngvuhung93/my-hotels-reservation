package com.epam.hotelreservation.guestservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


import com.epam.hotelreservation.guestservice.exception.GuestServiceException;

import com.epam.hotelreservation.guestservice.mapper.MapStructMapperImpl;
import com.epam.hotelreservation.guestservice.model.Guest;
import com.epam.hotelreservation.guestservice.repository.GuestRepository;
import com.epam.hotelreservation.guestservice.request.CreateGuestRequest;
import com.epam.hotelreservation.guestservice.request.UpdateGuestRequest;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GuestServiceTest {

  private GuestRepository guestRepository;
  private GuestService guestService;

  @BeforeAll
  public void setup() {
    guestRepository = Mockito.mock(GuestRepository.class);
    guestService = new GuestServiceImpl(guestRepository, new MapStructMapperImpl());
  }

  @Test
  void shouldCreateNewGuest_WhenReceiveCreateRequest() {
    var mockGuestCreateRequest = initMockCreateRequest();
    var mockCreatedGuest = initMockGuest();

    when(guestRepository.save(any(Guest.class))).thenReturn(mockCreatedGuest);

    var savedGuest = guestService.createAccount(mockGuestCreateRequest);
    assertEquals("dat1234", savedGuest.getUsername());
    assertEquals("Dat", savedGuest.getGuestName());
    assertEquals("68, Jump Str., New York", savedGuest.getAddress());
    assertEquals("092131233", savedGuest.getPhoneNumber());
  }

  @Test
  void shouldThrowException_WhenUsernameAlreadyExisted() {
    var mockGuestCreateRequest = initMockCreateRequest();
    var mockExistedGuest = initMockGuest();

    when(guestRepository.findByUsername(anyString())).thenReturn(mockExistedGuest);
    assertThrows(GuestServiceException.class, () -> guestService.createAccount(mockGuestCreateRequest));
  }

  @Test
  void shouldReturnGuest_WhenGetGuestByCorrectId() {
    var mockGuest = initMockGuest();
    when(guestRepository.findById(1)).thenReturn(Optional.of(mockGuest));
    var savedGuest = guestService.findGuestById(1);
    assertEquals(1, savedGuest.getId());
    assertEquals("Dat", savedGuest.getGuestName());
    assertEquals("dat1234", savedGuest.getUsername());
    assertEquals("092131233", savedGuest.getPhoneNumber());
  }

  @Test
  void shouldThrowException_WhenGetNonExistId() {
    assertThrows(GuestServiceException.class, () -> guestService.findGuestById(0));
  }

  @Test
  void shouldReturnGuest_WhenGetGuestByCorrectUsername() {
    var mockGuest = initMockGuest();
    when(guestRepository.findByUsername(mockGuest.getUsername())).thenReturn(mockGuest);
    var savedGuest = guestService.findGuestByUsername(mockGuest.getUsername());
    assertEquals("Dat", savedGuest.getGuestName());
    assertEquals("dat1234", savedGuest.getUsername());
    assertEquals("092131233", savedGuest.getPhoneNumber());
  }

  @Test
  void shouldThrowException_WhenGetNonExistUsername() {
    when(guestRepository.findByUsername(anyString())).thenReturn(null);
    assertThrows(GuestServiceException.class, () -> guestService.findGuestByUsername(StringUtils.EMPTY));
  }

  @Test
  void shouldReturnUpdatedGuest_WhenUpdateGuestById() {
    var mockUpdateRequest = initMockUpdateRequest();
    var mockGuest = initMockGuest();
    when(guestRepository.findById(1)).thenReturn(Optional.of(mockGuest));
    var mockUpdatedGuest = Guest.builder()
        .guestName(mockUpdateRequest.getName())
        .address(mockUpdateRequest.getAddress())
        .phoneNumber(mockUpdateRequest.getPhone())
        .username(mockGuest.getUsername())
        .id(mockGuest.getId()).build();

    when(guestRepository.save(any(Guest.class))).thenReturn(mockUpdatedGuest);

    var updatedGuest= guestService.updateGuest(1, mockUpdateRequest);
    assertEquals(mockUpdatedGuest.getId(), updatedGuest.getId());
    assertEquals(mockUpdatedGuest.getGuestName(), updatedGuest.getGuestName());
    assertEquals(mockUpdatedGuest.getPhoneNumber(), updatedGuest.getPhoneNumber());
    assertEquals(mockUpdatedGuest.getAddress(), updatedGuest.getAddress());
  }

  @Test
  void shouldThrowException_WhenUpdateNonExistId() {
    var mockUpdateRequest = initMockUpdateRequest();
    assertThrows(GuestServiceException.class, () -> guestService.updateGuest(0, mockUpdateRequest));
  }

  private Guest initMockGuest() {
    return Guest.builder()
        .id(1)
        .address("68, Jump Str., New York")
        .guestName("Dat")
        .username("dat1234")
        .password("1234567")
        .phoneNumber("092131233")
        .build();
  }

  private UpdateGuestRequest initMockUpdateRequest() {
    return UpdateGuestRequest.builder()
        .name("Dat Nguyen")
        .phone("0121749223")
        .address("67 Jump Str., New York")
        .build();
  }

  private CreateGuestRequest initMockCreateRequest() {
    return CreateGuestRequest.builder()
        .username("dat1234")
        .password("092131233")
        .salt("!@#$%^&*()")
        .address("68, Jump Str., New York")
        .phoneNumber("092131233")
        .guestName("Dat")
        .build();
  }

}
