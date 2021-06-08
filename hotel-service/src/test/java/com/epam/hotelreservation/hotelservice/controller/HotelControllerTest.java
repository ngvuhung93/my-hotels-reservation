package com.epam.hotelreservation.hotelservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.hotelreservation.hotelservice.constant.HotelServiceConstant;
import com.epam.hotelreservation.hotelservice.dto.HotelDto;
import com.epam.hotelreservation.hotelservice.exception.HotelErrorResponse;
import com.epam.hotelreservation.hotelservice.exception.HotelExceptionConverter;
import com.epam.hotelreservation.hotelservice.exception.HotelServiceException;
import com.epam.hotelreservation.hotelservice.mapper.MapStructMapper;
import com.epam.hotelreservation.hotelservice.mapper.MapStructMapperImpl;
import com.epam.hotelreservation.hotelservice.model.Hotel;
import com.epam.hotelreservation.hotelservice.model.Room;
import com.epam.hotelreservation.hotelservice.service.HotelService;
import com.epam.hotelreservation.hotelservice.utility.TestUtility;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HotelControllerImpl.class)
class HotelControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private HotelService hotelService;

  private final int HOTEL_ID = 1;
  private final int NOT_EXISTED_ID = -1;
  private final LocalDate today = LocalDate.now();
  private final String CITY = "New York";
  private final MapStructMapper mapper = new MapStructMapperImpl();

  @Test
  void testGetGuestById_SuccessCase() throws Exception {
    var foundHotel = initMockHotel();
    when(hotelService.getHotelById(HOTEL_ID)).thenReturn(foundHotel);

    mvc.perform(get(HotelServiceConstant.HOTEL_SERVICE_URL + "/" + HOTEL_ID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(TestUtility.asJsonString(foundHotel)));
    verify(hotelService, times(1)).getHotelById(HOTEL_ID);
  }

  @Test
  void testGetGuestById_FailedCase() throws Exception {

    when(hotelService.getHotelById(NOT_EXISTED_ID)).thenThrow(new HotelServiceException(
        HotelErrorResponse.HOTEL_NOT_FOUND_EXCEPTION));
    var converter = new HotelExceptionConverter();
    var jsonStringResponse = converter
        .toJsonNode(HotelErrorResponse.HOTEL_NOT_FOUND_EXCEPTION, StringUtils.EMPTY).toString();

    mvc.perform(get(HotelServiceConstant.HOTEL_SERVICE_URL + "/" + NOT_EXISTED_ID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(jsonStringResponse));
    verify(hotelService, times(1)).getHotelById(NOT_EXISTED_ID);
  }

  @Test
  void testHotelByLocationAndArrivalDateAndDepartDate_SuccessCase() throws Exception {
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

    mockHotel1.setRooms(Arrays.asList(mockRoom1, mockRoom2));
    mockHotel2.setRooms(Arrays.asList(mockRoom3));

    var listOfHotel = Stream.of(mockHotel1, mockHotel2)
        .map(mapper::mapHotelToHotelDto).collect(Collectors.toList());

    DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(HotelServiceConstant.DATE_FORMAT_PATTERN);
    var arrivalDate = today;
    var departureDate = today.plusDays(3);

    when(hotelService.searchHotelsByDestinationAndDate(arrivalDate, departureDate, CITY))
        .thenReturn(listOfHotel);

    mvc.perform(get(HotelServiceConstant.HOTEL_SERVICE_URL + "/search")
        .contentType(MediaType.APPLICATION_JSON)
        .param("arrivalDate", arrivalDate.format(formatter))
        .param("departureDate", departureDate.format(formatter))
        .param("destination", CITY))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(TestUtility.asJsonString(listOfHotel)));

    verify(hotelService, times(1))
        .searchHotelsByDestinationAndDate(arrivalDate, departureDate, CITY);
  }

  private HotelDto initMockHotel() {
    return HotelDto.builder()
        .id(HOTEL_ID)
        .hotelPhone("19006067")
        .hotelName("Hotel California")
        .hotelAddress("53 Wall Str., LA")
        .build();
  }
}
