package com.epam.hotelreservation.reservationservice.listener;

import static lombok.AccessLevel.PRIVATE;

import com.epam.hotelreservation.reservationservice.event.ReservationUpdatedTimeFrameEvent;
import com.epam.hotelreservation.reservationservice.repository.ReservationRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReservationUpdatedTimeFrameEventListener {

  ReservationRepository reservationRepository;

  @EventHandler
  public void handle(ReservationUpdatedTimeFrameEvent reservationUpdatedTimeFrameEvent) {
    log.info("Handle update event: {}", reservationUpdatedTimeFrameEvent);
    Optional.ofNullable(
        reservationRepository.findReservationByUuid(reservationUpdatedTimeFrameEvent.getUuid()))
        .ifPresent(reservation -> {
          reservation.setBookedFrom(reservationUpdatedTimeFrameEvent.getBookedFrom());
          reservation.setBookedTo(reservationUpdatedTimeFrameEvent.getBookedTo());
          reservationRepository.save(reservation);
        });
  }
}
