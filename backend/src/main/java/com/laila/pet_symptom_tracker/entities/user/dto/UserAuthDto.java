package com.laila.pet_symptom_tracker.entities.user.dto;

import com.laila.pet_symptom_tracker.entities.user.User;
import java.util.UUID;

public record UserAuthDto(UUID id, String username, String email, String role, Boolean enabled) {
  public static UserAuthDto from(User user) {
    return new UserAuthDto(
        user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getEnabled());
  }
}
