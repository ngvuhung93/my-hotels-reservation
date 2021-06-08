package com.epam.hotelreservation.guestservice.mapper;

import com.epam.hotelreservation.guestservice.dto.GuestAccountDto;
import com.epam.hotelreservation.guestservice.dto.GuestDto;
import com.epam.hotelreservation.guestservice.model.Guest;
import org.springframework.stereotype.Component;

@Component
public class MapStructMapperImpl implements MapStructMapper {

  @Override
  public GuestDto mapGuestToGuestDto(Guest guest) {
    return GuestDto.builder()
        .guestName(guest.getGuestName())
        .address(guest.getAddress())
        .id(guest.getId())
        .phoneNumber(guest.getPhoneNumber())
        .username(guest.getUsername())
        .build();
  }

  @Override
  public GuestAccountDto mapGuestToGuestAccountDto(Guest guest) {
    return GuestAccountDto.builder()
        .username(guest.getUsername())
        .password(guest.getPassword())
        .address(guest.getAddress())
        .phoneNumber(guest.getPhoneNumber())
        .guestName(guest.getGuestName())
        .salt(guest.getSalt())
        .build();
  }
}
