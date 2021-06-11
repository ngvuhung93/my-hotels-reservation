package com.epam.hotelreservation.reservationservice.listener;

import static lombok.AccessLevel.PRIVATE;

import com.epam.hotelreservation.reservationservice.model.Reservation;
import com.epam.hotelreservation.reservationservice.producer.KafkaProducer;
import com.epam.hotelreservation.reservationservice.repository.ReservationRepository;
import com.epam.hotelreservation.reservationservice.util.ReservationUtility;
import event.ReservationCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReservationCreatedEventListener {

  ReservationRepository reservationRepository;
  @Autowired
  KafkaProducer kafkaProducer;

  @EventHandler
  public void handle(ReservationCreatedEvent reservationCreatedEvent) {
    log.info("Handle create event: {}", reservationCreatedEvent);
    kafkaProducer
        .send("reservationTopic", ReservationUtility.asJsonString(reservationCreatedEvent));
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
