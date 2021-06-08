package com.epam.hotelreservation.hotelservice.client;

import com.epam.hotelreservation.hotelservice.constant.HotelServiceConstant;
import com.epam.hotelreservation.hotelservice.dto.ReservationDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${api.service.reservation.name}", path = "${api.service.reservation.path}")
public interface ReservationService {

  @GetMapping("/booked")
  ResponseEntity<List<ReservationDto>> findReservationConflictWithGivenDates(
      @RequestParam @DateTimeFormat(pattern = HotelServiceConstant.DATE_FORMAT_PATTERN) LocalDate arrivalDate,
      @RequestParam @DateTimeFormat(pattern = HotelServiceConstant.DATE_FORMAT_PATTERN) LocalDate departureDate,
      @RequestParam String city);
}
