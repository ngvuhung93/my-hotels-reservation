package com.epam.hotelreservation.reservationservice.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import com.epam.hotelreservation.reservationservice.command.ReservationCreateCommand;
import com.epam.hotelreservation.reservationservice.command.ReservationUpdateTimeFrameCommand;
import com.epam.hotelreservation.reservationservice.enums.ReservationStatus;
import com.epam.hotelreservation.reservationservice.event.ReservationCreatedEvent;
import com.epam.hotelreservation.reservationservice.event.ReservationUpdatedTimeFrameEvent;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationAggregate {

  @AggregateIdentifier
  String uuid;
  Integer hotelId;
  Integer roomId;
  String city;
  LocalDate bookedFrom;
  LocalDate bookedTo;
  ReservationStatus reservationStatus;

  private ReservationAggregate() {
  }

  @CommandHandler
  public ReservationAggregate(ReservationCreateCommand reservationCreateCommand) {
    apply(ReservationCreatedEvent.builder()
        .uuid(reservationCreateCommand.getUuid())
        .hotelId(reservationCreateCommand.getHotelId())
        .roomId(reservationCreateCommand.getRoomId())
        .bookedFrom(reservationCreateCommand.getBookedFrom())
        .bookedTo(reservationCreateCommand.getBookedTo())
        .city(reservationCreateCommand.getCity())
        .reservationStatus(ReservationStatus.CREATED)
        .build()
    );
  }

  @CommandHandler
  public void updateTimeFrameHandler(
      ReservationUpdateTimeFrameCommand reservationUpdateTimeFrameCommand) {
    apply(ReservationUpdatedTimeFrameEvent.builder()
        .uuid(reservationUpdateTimeFrameCommand.getUuid())
        .bookedFrom(reservationUpdateTimeFrameCommand.getBookedFrom())
        .bookedTo(reservationUpdateTimeFrameCommand.getBookedTo())
        .build()
    );
  }

  @EventSourcingHandler
  public void on(ReservationCreatedEvent event) {
    this.uuid = event.getUuid();
    this.hotelId = event.getHotelId();
    this.roomId = event.getRoomId();
    this.bookedFrom = event.getBookedFrom();
    this.bookedTo = event.getBookedTo();
    this.city = event.getCity();
    this.reservationStatus = event.getReservationStatus();
  }

  @EventSourcingHandler
  public void on(ReservationUpdatedTimeFrameEvent event) {
    this.uuid = event.getUuid();
    this.bookedFrom = event.getBookedFrom();
    this.bookedTo = event.getBookedTo();
    this.reservationStatus = ReservationStatus.MODIFIED;
  }

}
