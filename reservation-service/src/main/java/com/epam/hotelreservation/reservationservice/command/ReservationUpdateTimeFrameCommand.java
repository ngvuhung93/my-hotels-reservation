package com.epam.hotelreservation.reservationservice.command;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationUpdateTimeFrameCommand {

  @TargetAggregateIdentifier
  String uuid;
  LocalDate bookedFrom;
  LocalDate bookedTo;

}
