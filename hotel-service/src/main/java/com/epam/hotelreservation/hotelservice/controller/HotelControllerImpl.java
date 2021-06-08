package com.epam.hotelreservation.hotelservice.controller;

import com.epam.hotelreservation.hotelservice.constant.HotelServiceConstant;
import com.epam.hotelreservation.hotelservice.dto.HotelDto;
import com.epam.hotelreservation.hotelservice.service.HotelService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(HotelServiceConstant.HOTEL_SERVICE_URL)
public class HotelControllerImpl implements HotelController {

  private final HotelService hotelService;

  @Autowired
  public HotelControllerImpl(
      HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<HotelDto> getById(@PathVariable Integer id) {
    log.info("Get hotel with id: {}", id);
    return ResponseEntity.ok(hotelService.getHotelById(id));
  }

  @GetMapping("/search")
  public ResponseEntity<List<HotelDto>> getHotelByLocationAndArrivalDateAndDepartDate(
      @RequestParam("arrivalDate")
      @DateTimeFormat(pattern = HotelServiceConstant.DATE_FORMAT_PATTERN) LocalDate arrivalDate,
      @RequestParam("departureDate")
      @DateTimeFormat(pattern = HotelServiceConstant.DATE_FORMAT_PATTERN) LocalDate departureDate,
      @RequestParam("destination") String destination) {
    log.info("Find hotel in {}, from: {}, to: {}", destination, arrivalDate, departureDate);
    return ResponseEntity
        .ok(hotelService.searchHotelsByDestinationAndDate(arrivalDate, departureDate, destination));
  }
}
