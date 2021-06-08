package com.epam.hotelreservation.guestservice.exception;

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

  private final GuestExceptionConverter guestExceptionConverter = new GuestExceptionConverter();

  @ExceptionHandler(GuestServiceException.class)
  public ResponseEntity<Object> handleGuestNotFoundException(GuestServiceException ex) {
    log.error("Guest Exception {}", ex.getGuestErrorResponse());

    return new ResponseEntity<>(
        guestExceptionConverter.toJsonNode(ex.getGuestErrorResponse(), StringUtils.EMPTY),
        new HttpHeaders(), ex.getGuestErrorResponse().getHttpStatus());
  }

}
