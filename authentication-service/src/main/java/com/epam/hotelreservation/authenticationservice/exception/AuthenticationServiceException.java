package com.epam.hotelreservation.authenticationservice.exception;

import lombok.Getter;

@Getter
public class AuthenticationServiceException extends RuntimeException {

  private final AuthenticationErrorResponse authenticationErrorResponse;

  public AuthenticationServiceException(AuthenticationErrorResponse authenticationErrorResponse) {
    this.authenticationErrorResponse = authenticationErrorResponse;
  }
}
