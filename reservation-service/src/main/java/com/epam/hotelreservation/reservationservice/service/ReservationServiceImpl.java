package com.epam.hotelreservation.reservationservice.service;

import com.epam.hotelreservation.reservationservice.command.ReservationCreateCommand;
import com.epam.hotelreservation.reservationservice.command.ReservationUpdateTimeFrameCommand;
import com.epam.hotelreservation.reservationservice.dto.ReservationDto;
import com.epam.hotelreservation.reservationservice.enums.ReservationStatus;
import com.epam.hotelreservation.reservationservice.exception.ReservationErrorResponse;
import com.epam.hotelreservation.reservationservice.exception.ReservationServiceException;
import com.epam.hotelreservation.reservationservice.mapper.MapStructMapper;
import com.epam.hotelreservation.reservationservice.model.Reservation;
import com.epam.hotelreservation.reservationservice.repository.ReservationRepository;
import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import com.epam.hotelreservation.reservationservice.request.UpdateReservationTimeFrameRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final MapStructMapper mapper;
  private final CommandGateway commandGateway;

  @Autowired
  public ReservationServiceImpl(
      ReservationRepository reservationRepository,
      MapStructMapper mapper,
      CommandGateway commandGateway) {
    this.reservationRepository = reservationRepository;
    this.mapper = mapper;
    this.commandGateway = commandGateway;
  }

  @Override
  public ReservationDto createReservation(CreateReservationRequest createReservationRequest) {
    return mapper.mapReservationToReservationDto(reservationRepository.save(Reservation.builder()
        .hotelId(createReservationRequest.getHotelId())
        .roomId(createReservationRequest.getRoomId())
        .bookedFrom(createReservationRequest.getBookedFrom())
        .bookedTo(createReservationRequest.getBookedTo())
        .city(createReservationRequest.getCity())
        .reservationStatus(ReservationStatus.CREATED)
        .build()));
  }

  public CompletableFuture<String> create(CreateReservationRequest createReservationRequest) {
    return commandGateway.send(ReservationCreateCommand.builder()
        .uuid(UUID.randomUUID().toString())
        .hotelId(createReservationRequest.getHotelId())
        .roomId(createReservationRequest.getRoomId())
        .bookedFrom(createReservationRequest.getBookedFrom())
        .bookedTo(createReservationRequest.getBookedTo())
        .city(createReservationRequest.getCity())
        .reservationStatus(ReservationStatus.CREATED)
        .build()
    );
  }

  @Override
  public CompletableFuture<String> updateTimeFrame(
      UpdateReservationTimeFrameRequest updateReservationTimeFrameRequest) {
    return commandGateway.send(ReservationUpdateTimeFrameCommand.builder()
        .uuid(updateReservationTimeFrameRequest.getUuid())
        .bookedFrom(updateReservationTimeFrameRequest.getBookedFrom())
        .bookedTo(updateReservationTimeFrameRequest.getBookedTo())
        .build()
    );
  }

  @Override
  public ReservationDto findReservationById(Integer id) {
    return mapper.mapReservationToReservationDto(
        reservationRepository.findById(id).orElseThrow(() -> new ReservationServiceException(
            ReservationErrorResponse.RESERVATION_NOT_FOUND_EXCEPTION)));
  }

  @Override
  public List<ReservationDto> findReservationConflictWithGivenDates(LocalDate arrivalDate,
      LocalDate departureDate, String city) {
    validateTimeFrame(arrivalDate, departureDate);
    return reservationRepository.findAll().stream().filter(
        reservation -> !(departureDate.compareTo(reservation.getBookedFrom()) < 0
            || arrivalDate.compareTo(reservation.getBookedTo()) > 0) && city
            .equals(reservation.getCity()))
        .map(mapper::mapReservationToReservationDto)
        .collect(Collectors.toList());
  }

  private void validateTimeFrame(LocalDate arrivalDate, LocalDate departureDate) {
    if (departureDate.compareTo(arrivalDate) <= 0) {
      throw new ReservationServiceException(ReservationErrorResponse.INVALID_TIME_FRAME_EXCEPTION);
    }
  }
}
