package com.laila.pet_symptom_tracker.entities.user.dto;

import com.laila.pet_symptom_tracker.entities.user.User;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.NonNull;

public record GetUser(
    @NonNull UUID id,
    @NotBlank String username,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String email,
    @NonNull Boolean enabled) {
  public static GetUser to(User dto) {
    return new GetUser(
        dto.getId(),
        dto.getUsername(),
        dto.getFirstName(),
        dto.getLastName(),
        dto.getEmail(),
        dto.isEnabled());
  }
}
