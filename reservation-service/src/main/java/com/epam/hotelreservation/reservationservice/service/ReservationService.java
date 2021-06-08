package com.epam.hotelreservation.reservationservice.service;

import com.epam.hotelreservation.reservationservice.dto.ReservationDto;
import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import com.epam.hotelreservation.reservationservice.request.UpdateReservationTimeFrameRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ReservationService {

  ReservationDto createReservation(CreateReservationRequest createReservationRequest);

  ReservationDto findReservationById(Integer id);

  List<ReservationDto> findReservationConflictWithGivenDates(LocalDate arrivalDate,
      LocalDate departureDate, String city);

  CompletableFuture<String> create(CreateReservationRequest createReservationRequest);

  CompletableFuture<String> updateTimeFrame(
      UpdateReservationTimeFrameRequest updateReservationTimeFrameRequest);
}
