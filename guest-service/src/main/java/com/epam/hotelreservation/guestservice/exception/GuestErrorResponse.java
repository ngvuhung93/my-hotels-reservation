package com.epam.hotelreservation.guestservice.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum GuestErrorResponse implements Serializable {

  GUEST_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "1000", "Guest not found."),
  EXISTED_USERNAME_EXCEPTION(HttpStatus.BAD_REQUEST, "1001", "Existed username.");

  private final HttpStatus httpStatus;
  private final String errorCode;
  private final String message;
}
