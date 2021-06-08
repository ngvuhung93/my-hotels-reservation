package com.epam.hotelreservation.guestservice.service;

import com.epam.hotelreservation.guestservice.dto.GuestAccountDto;
import com.epam.hotelreservation.guestservice.dto.GuestDto;
import com.epam.hotelreservation.guestservice.exception.GuestErrorResponse;
import com.epam.hotelreservation.guestservice.exception.GuestServiceException;
import com.epam.hotelreservation.guestservice.mapper.MapStructMapper;
import com.epam.hotelreservation.guestservice.model.Guest;
import com.epam.hotelreservation.guestservice.repository.GuestRepository;
import com.epam.hotelreservation.guestservice.request.CreateGuestRequest;
import com.epam.hotelreservation.guestservice.request.UpdateGuestRequest;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements GuestService {

  private final GuestRepository guestRepository;
  private final MapStructMapper mapper;

  @Autowired
  public GuestServiceImpl(
      GuestRepository guestRepository,
      MapStructMapper mapper) {
    this.guestRepository = guestRepository;
    this.mapper = mapper;
  }

  @Override
  public GuestDto findGuestById(Integer id) {
    return mapper.mapGuestToGuestDto(
        guestRepository.findById(id).orElseThrow(() -> new GuestServiceException(
            GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION)));
  }

  @Override
  public GuestAccountDto createAccount(CreateGuestRequest createGuestRequest) {
    validateExistUsername(createGuestRequest.getUsername());
    return mapper.mapGuestToGuestAccountDto(guestRepository.save(Guest.builder()
        .username(createGuestRequest.getUsername())
        .password(createGuestRequest.getPassword())
        .salt(createGuestRequest.getSalt())
        .guestName(createGuestRequest.getGuestName())
        .phoneNumber(createGuestRequest.getPhoneNumber())
        .address(createGuestRequest.getAddress())
        .build()));
  }

  @Override
  public GuestAccountDto findGuestByUsername(String username) {
    return mapper.mapGuestToGuestAccountDto(
        Optional.ofNullable(guestRepository.findByUsername(username))
            .orElseThrow(() -> new GuestServiceException(GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION)));
  }

  @Override
  public GuestDto updateGuest(Integer id, UpdateGuestRequest updateGuestRequest) {
    return guestRepository.findById(id).map(guest -> {
      guest.setGuestName(updateGuestRequest.getName());
      guest.setAddress(updateGuestRequest.getAddress());
      guest.setPhoneNumber(updateGuestRequest.getPhone());
      return mapper.mapGuestToGuestDto(guestRepository.save(guest));
    }).orElseThrow(() -> new GuestServiceException(GuestErrorResponse.GUEST_NOT_FOUND_EXCEPTION));
  }

  private void validateExistUsername(String username) {
    Optional.ofNullable(guestRepository.findByUsername(username))
        .ifPresent(guest -> {
          throw new GuestServiceException(GuestErrorResponse.EXISTED_USERNAME_EXCEPTION);
        });
  }

}
