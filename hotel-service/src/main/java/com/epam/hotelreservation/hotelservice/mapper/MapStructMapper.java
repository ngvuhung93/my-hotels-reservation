package com.epam.hotelreservation.hotelservice.mapper;

import com.epam.hotelreservation.hotelservice.dto.HotelDto;
import com.epam.hotelreservation.hotelservice.dto.RoomDto;
import com.epam.hotelreservation.hotelservice.model.Hotel;
import com.epam.hotelreservation.hotelservice.model.Room;
import org.mapstruct.Mapper;

@Mapper
public interface MapStructMapper {

  HotelDto mapHotelToHotelDto(Hotel hotel);

  RoomDto mapRoomToRoomDto(Room room);

}
