package com.epam.hotelreservation.guestservice.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Guest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  private String salt;

  private String guestName;
  private String address;
  private String phoneNumber;

  @CreationTimestamp
  @Column(name = "created_time")
  private Instant createdDate;

  @UpdateTimestamp
  @Column(name = "updated_time")
  private Instant updatedDate;

}
