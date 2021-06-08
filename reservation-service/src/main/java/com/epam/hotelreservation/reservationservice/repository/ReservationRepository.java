package com.epam.hotelreservation.reservationservice.repository;

import com.epam.hotelreservation.reservationservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

  Reservation findReservationByUuid(String uuid);
}
