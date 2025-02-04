package com.laila.pet_symptom_tracker.securityconfig.dto;

import java.util.Date;

public record TokenData(String username, String[] roles, Date issueDate, Date expirationDate) {
  public boolean isExpired() {
    return expirationDate.before(new Date());
  }
}
