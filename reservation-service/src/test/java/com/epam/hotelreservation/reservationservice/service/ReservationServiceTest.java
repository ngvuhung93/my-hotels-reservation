package com.epam.hotelreservation.reservationservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.epam.hotelreservation.reservationservice.enums.ReservationStatus;
import com.epam.hotelreservation.reservationservice.exception.ReservationServiceException;
import com.epam.hotelreservation.reservationservice.mapper.MapStructMapperImpl;
import com.epam.hotelreservation.reservationservice.model.Reservation;
import com.epam.hotelreservation.reservationservice.repository.ReservationRepository;
import com.epam.hotelreservation.reservationservice.request.CreateReservationRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationServiceTest {

  private ReservationRepository reservationRepository;
  private ReservationService reservationService;
  private CommandGateway commandGateway;
  private final LocalDate today = LocalDate.now();
  private final String CITY = "New York";
  private final MapStructMapperImpl mapper = new MapStructMapperImpl();

  @BeforeAll
  public void setup() {
    commandGateway = Mockito.mock(CommandGateway.class);
    reservationRepository = Mockito.mock(ReservationRepository.class);
    reservationService = new ReservationServiceImpl(reservationRepository,
        new MapStructMapperImpl(), commandGateway);
  }

  @Test
  void shouldReturnReservation_WhenProvideCorrectId() {
    var mockReservation = initMockReservation();
    when(reservationRepository.findById(1)).thenReturn(Optional.of(mockReservation));
    var savedReservation = reservationService.findReservationById(1);
    assertEquals(1, savedReservation.getId());
    assertEquals(1, savedReservation.getHotelId());
    assertEquals(1, savedReservation.getRoomId());
    assertEquals(today, savedReservation.getBookedFrom());
    assertEquals(today.plusDays(3), savedReservation.getBookedTo());
    assertEquals(CITY, savedReservation.getCity());
    assertEquals(ReservationStatus.CREATED, savedReservation.getReservationStatus());
  }

  @Test
  void shouldThrowException_WhenGetNonExistId() {
    assertThrows(ReservationServiceException.class,
        () -> reservationService.findReservationById(0));
  }

  @Test
  void shouldReturnReservation_WhenCreateNewReservation() {
    var mockCreateReservationRequest = initMockCreateRequest();
    var mockCreatedReservation = initMockReservation();

    when(reservationRepository.save(any(Reservation.class))).thenReturn(mockCreatedReservation);
    var savedReservation = reservationService.createReservation(mockCreateReservationRequest);
    assertEquals(1, savedReservation.getId());
    assertEquals(1, savedReservation.getHotelId());
    assertEquals(1, savedReservation.getRoomId());
    assertEquals(today, savedReservation.getBookedFrom());
    assertEquals(today.plusDays(3), savedReservation.getBookedTo());
    assertEquals("New York", savedReservation.getCity());
    assertEquals(ReservationStatus.CREATED, savedReservation.getReservationStatus());
  }

  @Test
  void shouldReturnReservationConflictWithGivenDates() {
    
    var mockReturnReservationHalfBefore = Reservation.builder()
        .bookedFrom(today.minusDays(3))
        .bookedTo(today.plusDays(1))
        .city(CITY)
        .build();
    var mockReturnReservationHalfAfter = Reservation.builder()
        .bookedFrom(today.minusDays(2))
        .bookedTo(today.plusDays(4))
        .city(CITY)
        .build();
    var mockReturnReservationOutBound = Reservation.builder()
        .bookedFrom(today.minusDays(1))
        .bookedTo(today.plusDays(4))
        .city(CITY)
        .build();
    var mockReturnReservationInBound = Reservation.builder()
        .bookedFrom(today.plusDays(1))
        .bookedTo(today.plusDays(2))
        .city(CITY)
        .build();
    var mockNotReturnCase = Reservation.builder()
        .bookedFrom(today.plusDays(5))
        .bookedTo(today.plusDays(6))
        .city(CITY)
        .build();
    var mockNotReturnCaseWithInvalidCity = Reservation.builder()
        .bookedFrom(today.plusDays(1))
        .bookedTo(today.plusDays(2))
        .city("Los Angeles")
        .build();

    var reservations = new ArrayList<Reservation>();
    reservations.add(mockReturnReservationHalfBefore);
    reservations.add(mockReturnReservationHalfAfter);
    reservations.add(mockReturnReservationOutBound);
    reservations.add(mockReturnReservationInBound);
    reservations.add(mockNotReturnCase);
    reservations.add(mockNotReturnCaseWithInvalidCity);

    when(reservationRepository.findAll()).thenReturn(reservations);
    var returnReservations = reservationService.findReservationConflictWithGivenDates(today, today.plusDays(3),CITY);
    assertEquals(4, returnReservations.size());
    assertEquals(returnReservations.get(0), mapper.mapReservationToReservationDto(mockReturnReservationHalfBefore));
    assertEquals(returnReservations.get(1), mapper.mapReservationToReservationDto(mockReturnReservationHalfAfter));
    assertEquals(returnReservations.get(2), mapper.mapReservationToReservationDto(mockReturnReservationOutBound));
    assertEquals(returnReservations.get(3), mapper.mapReservationToReservationDto(mockReturnReservationInBound));
  }

  @Test
  void shouldThrowException_WhenReceiveInvalidTimeFrame() {
    assertThrows(ReservationServiceException.class,
        () -> reservationService.findReservationConflictWithGivenDates(today,today,CITY));
  }

  private Reservation initMockReservation() {
    return Reservation.builder()
        .id(1)
        .roomId(1)
        .hotelId(1)
        .bookedFrom(today)
        .bookedTo(today.plusDays(3))
        .reservationStatus(ReservationStatus.CREATED)
        .city(CITY)
        .build();
  }

  private CreateReservationRequest initMockCreateRequest() {
    return CreateReservationRequest.builder()
        .hotelId(1)
        .roomId(1)
        .bookedFrom(today)
        .bookedTo(today.plusDays(3))
        .city(CITY)
        .build();
  }

}
