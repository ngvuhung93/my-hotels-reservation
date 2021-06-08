package com.epam.hotelreservation.hotelservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeignErrorDecoder extends ErrorDecoder.Default {

  @Override
  public Exception decode(String methodKey, Response response) {

    switch (response.status()) {
      case 503:
        log.error("Status code: {}, Error Response: {}", response.status(), response.body());
        return new HotelServiceException(
            HotelErrorResponse.RESERVATION_SERVICE_NOT_RESPONSE_EXCEPTION);
      default:
        log.error("Status code: {}, Error Response: {}", response.status(), response.body());
        return new HotelServiceException(HotelErrorResponse.UNHANDLED_EXCEPTION);
    }

  }
}
