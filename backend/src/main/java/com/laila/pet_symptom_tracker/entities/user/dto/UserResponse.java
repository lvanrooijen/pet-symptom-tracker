package com.laila.pet_symptom_tracker.entities.user.dto;

import com.laila.pet_symptom_tracker.entities.user.User;
import java.util.UUID;

public record UserResponse(UUID id, String username, String email) {
  public static UserResponse from(User user) {
    return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
  }
}
