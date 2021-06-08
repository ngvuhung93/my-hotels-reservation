package com.epam.hotelreservation.guestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestAccountDto {
  private String username;
  private String password;
  private String salt;

  private String guestName;
  private String address;
  private String phoneNumber;
}
