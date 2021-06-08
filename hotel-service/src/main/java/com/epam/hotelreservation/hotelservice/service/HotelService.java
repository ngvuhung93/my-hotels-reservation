package com.epam.hotelreservation.hotelservice.service;

import com.epam.hotelreservation.hotelservice.dto.HotelDto;
import java.time.LocalDate;
import java.util.List;

public interface HotelService {

  HotelDto getHotelById(Integer id);

  List<HotelDto> searchHotelsByDestinationAndDate(LocalDate arrivalDate, LocalDate departDate,
      String destination);

}
