package com.epam.hotelreservation.reservationservice.event;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class ReservationUpdatedTimeFrameEvent {

  String uuid;
  LocalDate bookedFrom;
  LocalDate bookedTo;
}
