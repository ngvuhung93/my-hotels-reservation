package com.epam.hotelreservation.hotelservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.epam.hotelreservation.hotelservice.client.ReservationService;
import com.epam.hotelreservation.hotelservice.constant.HotelServiceConstant;
import com.epam.hotelreservation.hotelservice.dto.ReservationDto;
import com.epam.hotelreservation.hotelservice.exception.HotelServiceException;
import com.epam.hotelreservation.hotelservice.mapper.MapStructMapperImpl;
import com.epam.hotelreservation.hotelservice.model.Hotel;
import com.epam.hotelreservation.hotelservice.model.Room;
import com.epam.hotelreservation.hotelservice.repository.HotelRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelServiceTest {

  private HotelRepository hotelRepository;
  private HotelService hotelService;
  private ReservationService reservationService;
  private final LocalDate today = LocalDate.now();
  private final String CITY = "New York";
  private final DateTimeFormatter formatter = DateTimeFormatter
      .ofPattern(HotelServiceConstant.DATE_FORMAT_PATTERN);

  @BeforeAll
  public void setup() {
    hotelRepository = Mockito.mock(HotelRepository.class);
    reservationService = Mockito.mock(ReservationService.class);
    hotelService = new HotelServiceImpl(hotelRepository, new MapStructMapperImpl(),
        reservationService);
  }

  @Test
  void shouldReturnHotel_WhenGetHotelByCorrectId() {
    var mockHotel = initMockHotel();
    when(hotelRepository.findById(1)).thenReturn(Optional.of(mockHotel));
    var savedHotel = hotelService.getHotelById(1);
    assertEquals(1, savedHotel.getId());
    assertEquals("Hotel California", savedHotel.getHotelName());
    assertEquals("53 Wall Str., LA", savedHotel.getHotelAddress());
    assertEquals("19006067", savedHotel.getHotelPhone());
  }

  @Test
  void shouldThrowException_WhenGetNonExistId() {
    assertThrows(HotelServiceException.class, () -> hotelService.getHotelById(0));
  }

  @Test
  void shouldReturnHotelsAndRooms_WhenGuestSearchForRooms() {
    /* Test Description:
     * Booked all rooms of Hotel 1, include room 1 and room 2
     * Success case: the only room available is room 3 of Hotel 2
     */

    var reservation1 = ReservationDto.builder()
        .hotelId(1)
        .roomId(1)
        .bookedFrom(today.minusDays(3).format(formatter))
        .bookedTo(today.plusDays(1).format(formatter))
        .build();
    var reservation2 = ReservationDto.builder()
        .hotelId(1)
        .roomId(2)
        .bookedFrom(today.minusDays(2).format(formatter))
        .bookedTo(today.plusDays(4).format(formatter))
        .build();

    var mockHotel1 = Hotel.builder()
        .id(1)
        .hotelAddress("54 Wall Str., New York")
        .build();

    var mockHotel2 = Hotel.builder()
        .id(2)
        .hotelAddress("55 Wall Str., New York")
        .build();

    var mockRoom1 = Room.builder()
        .id(1)
        .hotel(mockHotel1)
        .build();

    var mockRoom2 = Room.builder()
        .id(2)
        .hotel(mockHotel1)
        .build();

    var mockRoom3 = Room.builder()
        .id(3)
        .hotel(mockHotel2)
        .build();

    var roomOfHotel1 = new ArrayList<Room>();
    roomOfHotel1.add(mockRoom1);
    roomOfHotel1.add(mockRoom2);

    var roomOfHotel2 = new ArrayList<Room>();
    roomOfHotel2.add(mockRoom3);

    mockHotel1.setRooms(roomOfHotel1);
    mockHotel2.setRooms(roomOfHotel2);

    var listOfHotel = new ArrayList<Hotel>();
    listOfHotel.add(mockHotel1);
    listOfHotel.add(mockHotel2);

    var response = ResponseEntity.ok(List.of(reservation1,reservation2));

    when(reservationService.findReservationConflictWithGivenDates(today, today.plusDays(3), CITY))
        .thenReturn(response);

    when(hotelRepository.getHotelByLocation(CITY)).thenReturn(listOfHotel);
    var availableHotels = hotelService.searchHotelsByDestinationAndDate(today, today.plusDays(3), CITY);

    assertEquals(1, availableHotels.size());
    // Check Hotel 2
    assertEquals(2, availableHotels.get(0).getId());
    // Check Hotel 2 has only one room available
    assertEquals(1, availableHotels.get(0).getRooms().size());
    // Check Hotel 2, Room 3
    assertEquals(3, availableHotels.get(0).getRooms().get(0).getId());
  }


  private Hotel initMockHotel() {
    var mockRoom = Room.builder()
        .id(1)
        .build();

    return Hotel.builder()
        .id(1)
        .hotelName("Hotel California")
        .hotelAddress("53 Wall Str., LA")
        .hotelPhone("19006067")
        .rooms(Arrays.asList(mockRoom))
        .build();
  }
}
