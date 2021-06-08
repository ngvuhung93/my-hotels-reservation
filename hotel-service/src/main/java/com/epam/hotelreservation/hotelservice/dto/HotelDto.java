package com.epam.hotelreservation.hotelservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {

  private Integer id;
  private String hotelName;
  private String hotelAddress;
  private String hotelPhone;
  private List<RoomDto> rooms;
}
