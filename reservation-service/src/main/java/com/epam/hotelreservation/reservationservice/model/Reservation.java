package com.epam.hotelreservation.reservationservice.model;

import enums.ReservationStatus;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String uuid;
  private Integer hotelId;
  private Integer roomId;
  private String city;
  private LocalDate bookedFrom;
  private LocalDate bookedTo;
  @Enumerated(EnumType.STRING)
  private ReservationStatus reservationStatus;

}
