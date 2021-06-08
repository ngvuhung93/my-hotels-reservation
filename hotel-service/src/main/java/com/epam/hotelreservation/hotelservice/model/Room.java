package com.epam.hotelreservation.hotelservice.model;

import com.epam.hotelreservation.hotelservice.enums.RoomStatus;
import com.epam.hotelreservation.hotelservice.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String roomName;
  @Enumerated(EnumType.STRING)
  private RoomType roomType;
  @Enumerated(EnumType.STRING)
  private RoomStatus roomStatus;
  private int capacity;
  private BigDecimal pricePerNight;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hotel_id")
  @JsonIgnore
  private Hotel hotel;

}
