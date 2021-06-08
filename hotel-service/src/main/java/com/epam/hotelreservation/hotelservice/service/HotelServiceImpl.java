package com.epam.hotelreservation.hotelservice.service;

import com.epam.hotelreservation.hotelservice.client.ReservationService;
import com.epam.hotelreservation.hotelservice.dto.HotelDto;
import com.epam.hotelreservation.hotelservice.dto.ReservationDto;
import com.epam.hotelreservation.hotelservice.exception.HotelErrorResponse;
import com.epam.hotelreservation.hotelservice.exception.HotelServiceException;
import com.epam.hotelreservation.hotelservice.mapper.MapStructMapper;
import com.epam.hotelreservation.hotelservice.model.Hotel;
import com.epam.hotelreservation.hotelservice.repository.HotelRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final MapStructMapper mapper;
  private final ReservationService reservationService;

  @Autowired
  public HotelServiceImpl(HotelRepository hotelRepository,
      MapStructMapper mapper,
      ReservationService reservationService) {
    this.hotelRepository = hotelRepository;
    this.mapper = mapper;
    this.reservationService = reservationService;
  }

  @Override
  public HotelDto getHotelById(Integer id) {
    return mapper.mapHotelToHotelDto(
        hotelRepository.findById(id).orElseThrow(() -> new HotelServiceException(
            HotelErrorResponse.HOTEL_NOT_FOUND_EXCEPTION)));
  }

  @Override
  @CircuitBreaker(name = "searchHotelBreak", fallbackMethod = "searchHotelFallBack")
  public List<HotelDto> searchHotelsByDestinationAndDate(LocalDate arrivalDate,
      LocalDate departDate, String destination) {

    var reservations = reservationService
        .findReservationConflictWithGivenDates(arrivalDate, departDate, destination)
        .getBody();
    var hotels = hotelRepository.getHotelByLocation(destination);
    var bookedHotelRoom = new HashMap<Integer, List<Integer>>();

    validateReservationsResponse(reservations);

    for (ReservationDto reservation : reservations) {
      if (!bookedHotelRoom.containsKey(reservation.getHotelId())) {
        var initRoomIdList = new ArrayList<Integer>();
        initRoomIdList.add(reservation.getRoomId());
        bookedHotelRoom.put(reservation.getHotelId(), initRoomIdList);
      } else {
        var roomIdList = bookedHotelRoom.get(reservation.getHotelId());
        roomIdList.add(reservation.getRoomId());
        bookedHotelRoom.put(reservation.getHotelId(), roomIdList);
      }
    }

    for (Hotel hotel : hotels) {
      if (bookedHotelRoom.containsKey(hotel.getId())) {
        for (Integer roomId : bookedHotelRoom.get(hotel.getId())) {
          hotel.getRooms().removeIf(room -> room.getId().equals(roomId));
        }
      }
    }

    return hotels.stream()
        .filter(hotel -> !hotel.getRooms().isEmpty())
        .map(mapper::mapHotelToHotelDto)
        .collect(Collectors.toList());
  }

  List<HotelDto> searchHotelFallBack(LocalDate arrivalDate, LocalDate departDate, String destination, Exception t) {
    log.error("Can't get hotel: {}", t.getMessage());
    return null;
  }

  private void validateReservationsResponse(List<ReservationDto> reservations) {
    if (reservations == null) {
      throw new HotelServiceException(HotelErrorResponse.UNHANDLED_EXCEPTION);
    }
  }

}
