package com.epam.hotelreservation.authenticationservice.exception;

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

private final AuthenticationExceptionConverter authenticationExceptionConverter = new AuthenticationExceptionConverter();

@ExceptionHandler(AuthenticationServiceException.class)
public ResponseEntity<Object> handleGuestNotFoundException(AuthenticationServiceException ex) {
  log.error("Authentication Exception {}", ex.getAuthenticationErrorResponse());

  return new ResponseEntity<>(
      authenticationExceptionConverter.toJsonNode(ex.getAuthenticationErrorResponse(), StringUtils.EMPTY),
      new HttpHeaders(), ex.getAuthenticationErrorResponse().getHttpStatus());
}

}
