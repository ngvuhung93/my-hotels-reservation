package com.epam.hotelreservation.hotelservice.repository;

import com.epam.hotelreservation.hotelservice.model.Hotel;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomHotelRepository {

  @Query("SELECT h FROM Hotel h WHERE h.hotelAddress LIKE %:location%")
  List<Hotel> getHotelByLocation(String location);

}
