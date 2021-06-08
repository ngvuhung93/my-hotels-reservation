package com.epam.hotelreservation.hotelservice.dto;

import com.epam.hotelreservation.hotelservice.constant.HotelServiceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReservationDto {

  private Integer id;
  private Integer hotelId;
  private Integer roomId;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = HotelServiceConstant.DATE_FORMAT_PATTERN)
  private String bookedTo;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = HotelServiceConstant.DATE_FORMAT_PATTERN)
  private String bookedFrom;
  private String reservationStatus;
}
