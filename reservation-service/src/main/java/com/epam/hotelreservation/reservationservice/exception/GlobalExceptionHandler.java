package com.epam.hotelreservation.reservationservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final ReservationExceptionConverter reservationExceptionConverter = new ReservationExceptionConverter();

  @ExceptionHandler(ReservationServiceException.class)
  public ResponseEntity<Object> handleReservationNotFoundException(
      ReservationServiceException exception) {
    log.error("Reservation Exception", exception);

    return new ResponseEntity<>(
        reservationExceptionConverter.toJsonNode(exception.getReservationErrorResponse(),
            StringUtils.EMPTY), new HttpHeaders(),
        exception.getReservationErrorResponse().getHttpStatus());
  }

}
