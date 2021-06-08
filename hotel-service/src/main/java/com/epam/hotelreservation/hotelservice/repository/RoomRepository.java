package com.epam.hotelreservation.hotelservice.repository;

import com.epam.hotelreservation.hotelservice.model.Room;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, BigInteger> {

}
