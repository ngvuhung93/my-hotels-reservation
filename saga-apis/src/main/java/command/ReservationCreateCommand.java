package command;

import enums.ReservationStatus;
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
public class ReservationCreateCommand {

  @TargetAggregateIdentifier
  String uuid;
  Integer hotelId;
  Integer roomId;
  String city;
  LocalDate bookedFrom;
  LocalDate bookedTo;
  ReservationStatus reservationStatus;

}
