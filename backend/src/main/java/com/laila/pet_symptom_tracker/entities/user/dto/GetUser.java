package com.laila.pet_symptom_tracker.entities.user.dto;

import com.laila.pet_symptom_tracker.entities.user.User;
import java.util.UUID;

public record GetUser(UUID id, String username, String email) {
  public static GetUser from(User user) {
    return new GetUser(user.getId(), user.getUsername(), user.getEmail());
  }
}
