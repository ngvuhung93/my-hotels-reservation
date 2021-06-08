package com.epam.hotelreservation.hotelservice.exception;

import lombok.Getter;

@Getter
public class HotelServiceException extends RuntimeException {

  private final HotelErrorResponse hotelErrorResponse;

  public HotelServiceException(HotelErrorResponse hotelErrorResponse) {
    this.hotelErrorResponse = hotelErrorResponse;
  }
}
