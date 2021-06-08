package com.epam.hotelreservation.reservationservice.controller;

import com.epam.hotelreservation.reservationservice.dto.ReservationDto;
import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ReservationController {

  @Operation(summary = "Create a new reservation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Reservation created",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid form data",
          content = @Content)})
  @PostMapping()
  ResponseEntity<ReservationDto> create(
      @RequestBody CreateReservationRequest createReservationRequest);

  @Operation(summary = "Get a reservation by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the reservation",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ReservationDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id provided",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Reservation not found",
          content = @Content)})
  @GetMapping("/{id}")
  ResponseEntity<ReservationDto> getById(@PathVariable Integer id);

  @Operation(summary = "Find reservation conflict with given dates")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the conflict reservations",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ReservationDto.class)))}),
      @ApiResponse(responseCode = "400", description = "Invalid time frame provided",
          content = @Content)})
  @GetMapping("/booked")
  ResponseEntity<List<ReservationDto>> findReservationConflictWithGivenDates();
}
