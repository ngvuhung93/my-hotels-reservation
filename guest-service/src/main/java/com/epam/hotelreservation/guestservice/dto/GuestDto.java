package com.epam.hotelreservation.guestservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDto {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("username")
  private String username;
  @JsonProperty("guestName")
  private String guestName;
  @JsonProperty("address")
  private String address;
  @JsonProperty("phoneNumber")
  private String phoneNumber;
}
