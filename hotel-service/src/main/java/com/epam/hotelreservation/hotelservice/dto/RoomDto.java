package com.epam.hotelreservation.hotelservice.dto;

import com.epam.hotelreservation.hotelservice.enums.RoomStatus;
import com.epam.hotelreservation.hotelservice.enums.RoomType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

  private Integer id;
  private String roomName;
  private RoomType roomType;
  private RoomStatus roomStatus;
  private int capacity;
  private BigDecimal pricePerNight;

}
