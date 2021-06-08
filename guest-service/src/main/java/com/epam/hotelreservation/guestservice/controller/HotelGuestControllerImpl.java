package com.epam.hotelreservation.guestservice.controller;

import com.epam.hotelreservation.guestservice.dto.GuestAccountDto;
import com.epam.hotelreservation.guestservice.dto.GuestDto;
import com.epam.hotelreservation.guestservice.request.CreateGuestRequest;
import com.epam.hotelreservation.guestservice.request.UpdateGuestRequest;
import com.epam.hotelreservation.guestservice.service.GuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/guests")
public class HotelGuestControllerImpl implements HotelGuestController {

  private final GuestService guestService;

  @Autowired
  public HotelGuestControllerImpl(
      GuestService guestService) {
    this.guestService = guestService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<GuestDto> getGuestById(@PathVariable Integer id) {
    log.info("Get guest with id: {}", id);
    return ResponseEntity.ok(guestService.findGuestById(id));
  }

  @PostMapping()
  public ResponseEntity<GuestAccountDto> create(@RequestBody CreateGuestRequest createGuestRequest) {
    log.info("Create new guest: {}", createGuestRequest);
    return ResponseEntity.ok(guestService.createAccount(createGuestRequest));
  }

  @GetMapping
  public ResponseEntity<GuestAccountDto> getByUsername(@RequestParam String username) {
    log.info("Get guest with username: {}", username);
    return ResponseEntity.ok(guestService.findGuestByUsername(username));
  }

  @PutMapping("/{id}")
  public ResponseEntity<GuestDto> updateById(@PathVariable Integer id, @RequestBody
      UpdateGuestRequest updateGuestRequest) {
    log.info("Update guest: {}", updateGuestRequest);
    return ResponseEntity.ok(guestService.updateGuest(id, updateGuestRequest));
  }

}
