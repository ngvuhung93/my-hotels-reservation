package com.epam.hotelreservation.guestservice.controller;

import com.epam.hotelreservation.guestservice.dto.GuestAccountDto;
import com.epam.hotelreservation.guestservice.dto.GuestDto;
import com.epam.hotelreservation.guestservice.request.CreateGuestRequest;
import com.epam.hotelreservation.guestservice.request.UpdateGuestRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface HotelGuestController {

  @Operation(summary = "Get a guest by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the guest",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = GuestDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Guest not found",
          content = @Content)})
  @GetMapping("/{id}")
  ResponseEntity<GuestDto> getGuestById(@PathVariable Integer id);


  @Operation(summary = "Get by username")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the guest",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = GuestAccountDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid username supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Guest not found",
          content = @Content)})
  @GetMapping
  ResponseEntity<GuestAccountDto> getByUsername(@RequestParam String username);


  @Operation(summary = "Create new guest")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Created new guest",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = GuestAccountDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid form input",
          content = @Content)})
  @PostMapping()
  ResponseEntity<GuestAccountDto> create(@RequestBody CreateGuestRequest createGuestRequest);

  @Operation(summary = "Update information of a created guest")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Updated the guest",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = GuestDto.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Guest not found",
          content = @Content)})
  @PutMapping("/{id}")
  ResponseEntity<GuestDto> updateById(@PathVariable Integer id,
      @RequestBody UpdateGuestRequest updateGuestRequest);
}
