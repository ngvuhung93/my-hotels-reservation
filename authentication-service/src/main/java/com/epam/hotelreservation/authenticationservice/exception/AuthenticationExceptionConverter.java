package com.epam.hotelreservation.authenticationservice.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationExceptionConverter {

  private final ObjectMapper mapper = new ObjectMapper();

  public JsonNode toJsonNode(AuthenticationErrorResponse errorDetail, String extraError) {
    var rootNode = this.mapper.createObjectNode();
    rootNode.put("errorCode", errorDetail.getErrorCode());
    rootNode.put("errorMessage", errorDetail.getMessage().concat(extraError));
    return rootNode;
  }


}
