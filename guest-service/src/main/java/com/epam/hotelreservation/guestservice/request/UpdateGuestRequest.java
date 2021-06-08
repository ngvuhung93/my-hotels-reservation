package com.epam.hotelreservation.guestservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateGuestRequest {

  private String name;
  private String phone;
  private String address;
}
