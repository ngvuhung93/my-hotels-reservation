package com.epam.hotelreservation.reservationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ReservationErrorResponse {

  RESERVATION_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "3000", "Reservation not found."),
  INVALID_TIME_FRAME_EXCEPTION(HttpStatus.BAD_REQUEST, "3001", "Invalid time frame.");

  private final HttpStatus httpStatus;
  private final String errorCode;
  private final String message;
}
