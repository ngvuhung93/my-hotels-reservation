package com.epam.hotelreservation.hotelservice.mapper;

import com.epam.hotelreservation.hotelservice.dto.HotelDto;
import com.epam.hotelreservation.hotelservice.dto.RoomDto;
import com.epam.hotelreservation.hotelservice.model.Hotel;
import com.epam.hotelreservation.hotelservice.model.Room;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MapStructMapperImpl implements MapStructMapper {

  @Override
  public HotelDto mapHotelToHotelDto(Hotel hotel) {
    return HotelDto.builder()
        .hotelName(hotel.getHotelName())
        .hotelAddress(hotel.getHotelAddress())
        .hotelPhone(hotel.getHotelPhone())
        .rooms(hotel.getRooms().stream().map(this::mapRoomToRoomDto).collect(Collectors.toList()))
        .id(hotel.getId())
        .build();
  }

  @Override
  public RoomDto mapRoomToRoomDto(Room room) {
    return RoomDto.builder()
        .id(room.getId())
        .roomName(room.getRoomName())
        .roomType(room.getRoomType())
        .capacity(room.getCapacity())
        .roomStatus(room.getRoomStatus())
        .pricePerNight(room.getPricePerNight())
        .build();
  }
}
