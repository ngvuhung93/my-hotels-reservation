package com.epam.hotelreservation.reservationservice.request;

import com.epam.hotelreservation.reservationservice.constant.ReservationServiceConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateReservationTimeFrameRequest {

  String uuid;
  @JsonFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN)
  private LocalDate bookedFrom;
  @JsonFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN)
  private LocalDate bookedTo;
}
