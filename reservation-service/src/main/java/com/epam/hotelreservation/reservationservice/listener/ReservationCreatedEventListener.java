package com.epam.hotelreservation.reservationservice.listener;

import static lombok.AccessLevel.PRIVATE;

import com.epam.hotelreservation.reservationservice.event.ReservationCreatedEvent;
import com.epam.hotelreservation.reservationservice.model.Reservation;
import com.epam.hotelreservation.reservationservice.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReservationCreatedEventListener {

  ReservationRepository reservationRepository;

  @EventHandler
  public void handle(ReservationCreatedEvent reservationCreatedEvent) {
    log.info("Handle create event: {}", reservationCreatedEvent);
    reservationRepository.save(Reservation.builder()
        .uuid(reservationCreatedEvent.getUuid())
        .bookedFrom(reservationCreatedEvent.getBookedFrom())
        .bookedTo(reservationCreatedEvent.getBookedTo())
        .city(reservationCreatedEvent.getCity())
        .roomId(reservationCreatedEvent.getRoomId())
        .hotelId(reservationCreatedEvent.getHotelId())
        .reservationStatus(reservationCreatedEvent.getReservationStatus())
        .build());
  }

}
