package com.epam.hotelreservation.reservationservice.request;

import com.epam.hotelreservation.reservationservice.constant.ReservationServiceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReservationRequest {

  private Integer hotelId;
  private Integer roomId;
  @JsonFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN)
  private LocalDate bookedFrom;
  @JsonFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN)
  private LocalDate bookedTo;
  private String city;

}
