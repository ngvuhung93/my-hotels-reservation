package com.epam.hotelreservation.guestservice.service;

import com.epam.hotelreservation.guestservice.dto.GuestAccountDto;
import com.epam.hotelreservation.guestservice.dto.GuestDto;
import com.epam.hotelreservation.guestservice.request.CreateGuestRequest;
import com.epam.hotelreservation.guestservice.request.UpdateGuestRequest;

public interface GuestService {

  GuestDto findGuestById(Integer id);
  GuestAccountDto createAccount(CreateGuestRequest createGuestRequest);
  GuestAccountDto findGuestByUsername(String username);
  GuestDto updateGuest(Integer id, UpdateGuestRequest updateGuestRequest);
}
