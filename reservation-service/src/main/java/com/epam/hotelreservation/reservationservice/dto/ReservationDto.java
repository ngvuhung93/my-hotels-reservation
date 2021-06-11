package com.epam.hotelreservation.reservationservice.dto;

import com.epam.hotelreservation.reservationservice.constant.ReservationServiceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import enums.ReservationStatus;
import java.time.LocalDate;
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
  @JsonFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN)
  private LocalDate bookedFrom;
  @JsonFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN)
  private LocalDate bookedTo;
  private String city;
  private ReservationStatus reservationStatus;
}
