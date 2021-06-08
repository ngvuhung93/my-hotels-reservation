package com.epam.hotelreservation.reservationservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.hotelreservation.reservationservice.constant.ReservationServiceConstant;
import com.epam.hotelreservation.reservationservice.dto.ReservationDto;
import com.epam.hotelreservation.reservationservice.enums.ReservationStatus;
import com.epam.hotelreservation.reservationservice.exception.ReservationErrorResponse;
import com.epam.hotelreservation.reservationservice.exception.ReservationExceptionConverter;
import com.epam.hotelreservation.reservationservice.exception.ReservationServiceException;
import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import com.epam.hotelreservation.reservationservice.service.ReservationService;
import com.epam.hotelreservation.reservationservice.utility.TestUtility;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
@WebMvcTest(ReservationControllerImpl.class)
class ReservationControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ReservationService reservationService;

  private final int RESERVATION_ID = 1;
  private final int NON_EXISTED_ID = -1;
  private final String CITY = "New York";
  private final LocalDate today = LocalDate.now();

  @Test
  void testFindReservationById_SuccessCase() throws Exception {
    var foundReservation = initMockReservation();
    when(reservationService.findReservationById(RESERVATION_ID)).thenReturn(foundReservation);

    mvc.perform(get(ReservationServiceConstant.RESERVATION_SERVICE_URL + "/" + RESERVATION_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(TestUtility.asJsonString(foundReservation)));
    verify(reservationService, times(1)).findReservationById(RESERVATION_ID);
  }

  @Test
  void testGetReservationById_FailedCase() throws Exception {

    when(reservationService.findReservationById(NON_EXISTED_ID))
        .thenThrow(new ReservationServiceException(
            ReservationErrorResponse.RESERVATION_NOT_FOUND_EXCEPTION));
    var converter = new ReservationExceptionConverter();
    var jsonStringResponse = converter
        .toJsonNode(ReservationErrorResponse.RESERVATION_NOT_FOUND_EXCEPTION, StringUtils.EMPTY)
        .toString();

    mvc.perform(get(ReservationServiceConstant.RESERVATION_SERVICE_URL + "/" + NON_EXISTED_ID)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string(jsonStringResponse));
    verify(reservationService, times(1)).findReservationById(NON_EXISTED_ID);
  }

  @Test
  void testCreateReservation_SuccessCase() throws Exception {
    var mockReservation = initMockReservation();
    var mockCreateRequest = initMockCreateRequest();

    when(reservationService.createReservation(mockCreateRequest)).thenReturn(mockReservation);
    mvc.perform(post(ReservationServiceConstant.RESERVATION_SERVICE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(TestUtility.asJsonString(mockCreateRequest)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(TestUtility.asJsonString(mockReservation)));
    verify(reservationService, times(1)).createReservation(mockCreateRequest);
  }

  @Test
  void testReservationConflictWithGivenDates_SuccessCase() throws Exception{
    var reservations = new ArrayList<ReservationDto>();
    var mockReservation1 = initMockReservation();
    var mockReservation2 = initMockReservation();
    reservations.add(mockReservation1);
    reservations.add(mockReservation2);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ReservationServiceConstant.DATE_FORMAT_PATTERN);
    var arrivalDate = today;
    var departureDate = today.plusDays(3);

    when(reservationService.findReservationConflictWithGivenDates(arrivalDate, departureDate,CITY)).thenReturn(reservations);
    mvc.perform(get(ReservationServiceConstant.RESERVATION_SERVICE_URL + "/booked")
        .param("arrivalDate", arrivalDate.format(formatter))
        .param("departureDate", departureDate.format(formatter))
        .param("city", CITY)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(TestUtility.asJsonString(reservations)));
    verify(reservationService, times(1)).findReservationConflictWithGivenDates(arrivalDate,departureDate,CITY);
  }

  @Test
  void testReservationConflictWithGivenDates_FailedCase() throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ReservationServiceConstant.DATE_FORMAT_PATTERN);

    var arrivalDate = today;
    var departureDate = today.plusDays(3);

    when(reservationService.findReservationConflictWithGivenDates(arrivalDate, departureDate,CITY))
        .thenThrow(new ReservationServiceException(
            ReservationErrorResponse.INVALID_TIME_FRAME_EXCEPTION));
    var converter = new ReservationExceptionConverter();
    var jsonStringResponse = converter
        .toJsonNode(ReservationErrorResponse.INVALID_TIME_FRAME_EXCEPTION, StringUtils.EMPTY)
        .toString();

    mvc.perform(get(ReservationServiceConstant.RESERVATION_SERVICE_URL + "/booked")
        .param("arrivalDate", arrivalDate.format(formatter))
        .param("departureDate", departureDate.format(formatter))
        .param("city", CITY)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string(jsonStringResponse));

    verify(reservationService, times(1)).findReservationConflictWithGivenDates(arrivalDate, departureDate, CITY);
  }

  private ReservationDto initMockReservation() {
    return ReservationDto.builder()
        .id(RESERVATION_ID)
        .hotelId(1)
        .roomId(1)
        .reservationStatus(ReservationStatus.CREATED)
        .bookedFrom(today)
        .bookedTo(today.plusDays(3))
        .build();
  }

  private CreateReservationRequest initMockCreateRequest() {
    return CreateReservationRequest.builder()
        .hotelId(1)
        .roomId(1)
        .bookedFrom(today)
        .bookedTo(today.plusDays(3))
        .build();
  }

}
