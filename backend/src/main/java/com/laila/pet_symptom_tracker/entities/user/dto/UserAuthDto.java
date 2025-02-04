package com.laila.pet_symptom_tracker.entities.user.dto;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UserAuthDto(@NotBlank UUID id,@NotBlank String username,@NotBlank String email,@NotBlank String role,@NotBlank Boolean enabled) {
  public static UserAuthDto from(@NotNull User user) {
    return new UserAuthDto(
        user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getEnabled());
  }
}
