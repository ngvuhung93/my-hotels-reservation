package com.epam.hotelreservation.reservationservice.mapper;

import com.epam.hotelreservation.reservationservice.dto.ReservationDto;
import com.epam.hotelreservation.reservationservice.model.Reservation;
import org.mapstruct.Mapper;

@Mapper
public interface MapStructMapper {

  ReservationDto mapReservationToReservationDto(Reservation reservation);
}
