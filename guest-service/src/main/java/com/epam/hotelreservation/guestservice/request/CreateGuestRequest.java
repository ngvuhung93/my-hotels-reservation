package com.epam.hotelreservation.guestservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGuestRequest {

  private String username;
  private String password;
  private String salt;

  private String guestName;
  private String address;
  private String phoneNumber;

}
