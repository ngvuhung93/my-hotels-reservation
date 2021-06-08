package com.epam.hotelreservation.guestservice.exception;

import lombok.Getter;

@Getter
public class GuestServiceException extends RuntimeException {

  private final GuestErrorResponse guestErrorResponse;

  public GuestServiceException(GuestErrorResponse guestErrorResponse) {
    this.guestErrorResponse = guestErrorResponse;
  }
}
