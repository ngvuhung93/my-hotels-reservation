package com.epam.hotelreservation.reservationservice.controller;

import com.epam.hotelreservation.reservationservice.constant.ReservationServiceConstant;
import com.epam.hotelreservation.reservationservice.dto.ReservationDto;
import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import com.epam.hotelreservation.reservationservice.service.ReservationService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(ReservationServiceConstant.RESERVATION_SERVICE_URL)
public class ReservationControllerImpl {

  private final ReservationService reservationService;

  @Autowired
  public ReservationControllerImpl(
      ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  public ResponseEntity<ReservationDto> create(
      @RequestBody CreateReservationRequest createReservationRequest) {
    log.info("Create reservation request: {}", createReservationRequest);
    return ResponseEntity.ok(reservationService.createReservation(createReservationRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationDto> getById(@PathVariable Integer id) {
    log.info("Get reservation with id: {}", id);
    return ResponseEntity.ok(reservationService.findReservationById(id));
  }

  @GetMapping("/booked")
  public ResponseEntity<List<ReservationDto>> findReservationConflictWithGivenDates(
      @RequestParam @DateTimeFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN) LocalDate arrivalDate,
      @RequestParam @DateTimeFormat(pattern = ReservationServiceConstant.DATE_FORMAT_PATTERN) LocalDate departureDate,
      @RequestParam String city
  ) {
    log.info("Find reservation conflict with given dates: {} and {}", arrivalDate, departureDate);
    return ResponseEntity.ok(reservationService
        .findReservationConflictWithGivenDates(arrivalDate, departureDate, city));
  }

}
