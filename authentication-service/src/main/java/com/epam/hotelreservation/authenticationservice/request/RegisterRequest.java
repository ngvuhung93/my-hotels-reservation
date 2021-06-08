package com.epam.hotelreservation.authenticationservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

  private String username;
  private String password;
  private String salt;
  private String guestName;
  private String address;
  private String phoneNumber;
}
