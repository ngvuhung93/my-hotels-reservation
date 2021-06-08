package com.epam.hotelreservation.reservationservice.controller;

import com.epam.hotelreservation.reservationservice.constant.ReservationServiceConstant;
import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import com.epam.hotelreservation.reservationservice.request.UpdateReservationTimeFrameRequest;
import com.epam.hotelreservation.reservationservice.service.ReservationService;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ReservationServiceConstant.RESERVATION_SERVICE_URL)
public class ReservationCommandControllerImpl implements ReservationCommandController {

  private final ReservationService reservationService;

  public ReservationCommandControllerImpl(
      ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping("/command/create")
  public CompletableFuture<String> create(
      @RequestBody CreateReservationRequest createReservationRequest) {
    return reservationService.create(createReservationRequest);
  }

  @PutMapping("/command/update")
  public CompletableFuture<String> updateTimeFrame(
      @RequestBody UpdateReservationTimeFrameRequest updateReservationTimeFrameRequest) {
    return reservationService.updateTimeFrame(updateReservationTimeFrameRequest);
  }

}
