package com.epam.hotelreservation.hotelservice.controller;

import com.epam.hotelreservation.hotelservice.dto.HotelDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface HotelController {

  @Operation(summary = "Get a hotel by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the hotel",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = HotelDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id provided",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Hotel not found",
          content = @Content)})
  @GetMapping("/{id}")
  ResponseEntity<HotelDto> getById(@PathVariable Integer id);

  @Operation(summary = "Get a guest by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found hotels",
          content = {@Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = HotelDto.class)))
          }),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content)})
  @GetMapping("/search")
  ResponseEntity<List<HotelDto>> getHotelByLocationAndArrivalDateAndDepartDate(
      @RequestParam("arrivalDate")
      @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate arrivalDate, @RequestParam("departDate")
  @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate departDate,
      @RequestParam("destination") String destination);

}
