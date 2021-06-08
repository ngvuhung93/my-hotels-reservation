package com.epam.hotelreservation.authenticationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GuestEntity {

  private String username;
  private String password;
  private String salt;
  private String guestName;
  private String address;
  private String phoneNumber;

}

