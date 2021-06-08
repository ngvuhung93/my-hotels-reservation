package com.epam.hotelreservation.reservationservice.mapper;

import com.epam.hotelreservation.reservationservice.dto.ReservationDto;
import com.epam.hotelreservation.reservationservice.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class MapStructMapperImpl implements MapStructMapper {

  @Override
  public ReservationDto mapReservationToReservationDto(Reservation reservation) {
    return ReservationDto.builder()
        .hotelId(reservation.getHotelId())
        .roomId(reservation.getRoomId())
        .id(reservation.getId())
        .bookedFrom(reservation.getBookedFrom())
        .bookedTo(reservation.getBookedTo())
        .city(reservation.getCity())
        .reservationStatus(reservation.getReservationStatus())
        .build();
  }
}
