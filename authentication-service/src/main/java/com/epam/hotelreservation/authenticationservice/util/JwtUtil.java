package com.epam.hotelreservation.authenticationservice.util;


import com.epam.hotelreservation.authenticationservice.entity.GuestEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private String expirationTime;

  private Key key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public String generate(GuestEntity guestEntity, String type) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("address", guestEntity.getAddress());
    claims.put("guestName", guestEntity.getGuestName());
    claims.put("phoneNumber", guestEntity.getPhoneNumber());
    return doGenerateToken(claims, guestEntity.getUsername(), type);
  }

  private String doGenerateToken(Map<String, Object> claims, String username, String type) {
    long expirationTimeLong;
    if ("ACCESS".equals(type)) {
      expirationTimeLong = Long.parseLong(expirationTime) * 1000;
    } else {
      expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
    }
    final var createdDate = new Date();
    final var expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(createdDate)
        .setExpiration(expirationDate)
        .signWith(key)
        .compact();
  }

}
