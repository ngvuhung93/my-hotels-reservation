package com.epam.hotelreservation.hotelservice.exception;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
public enum HotelErrorResponse implements Serializable {

  HOTEL_NOT_FOUND_EXCEPTION(HttpStatus.BAD_REQUEST, "2000", "Hotel not found."),
  RESERVATION_SERVICE_NOT_RESPONSE_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "2001",
      "Reservation service not available"),
  UNHANDLED_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "2002", "Internal service error");

  private final HttpStatus httpStatus;
  private final String errorCode;
  private final String message;
}
