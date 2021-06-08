package com.epam.hotelreservation.reservationservice.command;

import com.epam.hotelreservation.reservationservice.enums.ReservationStatus;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationCreateCommand {

  String uuid;
  Integer hotelId;
  Integer roomId;
  String city;
  LocalDate bookedFrom;
  LocalDate bookedTo;
  ReservationStatus reservationStatus;
}
