package com.epam.hotelreservation.authenticationservice.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum AuthenticationErrorResponse implements Serializable {

  INCORRECT_PASSWORD_EXCEPTION (HttpStatus.BAD_REQUEST, "1000", "Incorrect password provided");

  private final HttpStatus httpStatus;
  private final String errorCode;
  private final String message;
}
