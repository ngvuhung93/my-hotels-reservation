package com.epam.hotelreservation.reservationservice.exception;

import lombok.Getter;

@Getter
public class ReservationServiceException extends RuntimeException {

  private final ReservationErrorResponse reservationErrorResponse;

  public ReservationServiceException(
      ReservationErrorResponse reservationErrorResponse) {
    this.reservationErrorResponse = reservationErrorResponse;
  }

}
