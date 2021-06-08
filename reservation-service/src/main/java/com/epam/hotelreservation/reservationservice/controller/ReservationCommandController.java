package com.epam.hotelreservation.reservationservice.controller;

import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import com.epam.hotelreservation.reservationservice.request.UpdateReservationTimeFrameRequest;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReservationCommandController {

  CompletableFuture<String> create(@RequestBody CreateReservationRequest createReservationRequest);

  CompletableFuture<String> updateTimeFrame(
      @RequestBody UpdateReservationTimeFrameRequest updateReservationTimeFrameRequest);
}
