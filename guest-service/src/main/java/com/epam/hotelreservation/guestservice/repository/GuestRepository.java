package com.epam.hotelreservation.guestservice.repository;

import com.epam.hotelreservation.guestservice.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

  Guest findByUsername(String username);
}
