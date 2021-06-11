package com.epam.hotelreservation.authenticationservice.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestEntity {

  String username;
  String password;
  String salt;
  String guestName;
  String address;
  String phoneNumber;

}

