package event;

import enums.ReservationStatus;
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
public class ReservationCreatedEvent {

  String uuid;
  Integer hotelId;
  Integer roomId;
  String city;
  LocalDate bookedFrom;
  LocalDate bookedTo;
  ReservationStatus reservationStatus;
}
