package com.epam.hotelreservation.hotelservice.repository;

import com.epam.hotelreservation.hotelservice.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>, CustomHotelRepository {

}
