package com.epam.hotelreservation.guestservice.mapper;

import com.epam.hotelreservation.guestservice.dto.GuestAccountDto;
import com.epam.hotelreservation.guestservice.dto.GuestDto;
import com.epam.hotelreservation.guestservice.model.Guest;
import org.mapstruct.Mapper;

@Mapper
public interface MapStructMapper {

  GuestDto mapGuestToGuestDto(Guest guest);
  GuestAccountDto mapGuestToGuestAccountDto(Guest guest);
}
